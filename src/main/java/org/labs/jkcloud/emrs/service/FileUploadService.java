package org.labs.jkcloud.emrs.service;

import jakarta.servlet.http.HttpServletRequest;
import org.labs.jkcloud.emrs.model.VO.UploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.*;


@Service
public class FileUploadService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.cloudfront-url}")
    private String cloudFrontUrl;

    private final S3Client s3Client;

    public FileUploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private static final Set<String> ALLOWED_SECTIONS = Set.of(
            "home_banner", "achievement", "principal_secretary", "office_of_principal",
            "faculty", "hall_of_admin", "sports", "science", "facilities", "gallery",
            "admission", "lecture_notes", "notices", "tenders", "academic-calendar", "exam-results", "assignment-exams"
    );

    private String generateUniqueFileName(MultipartFile file) {
        return UUID.randomUUID() + "-" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    }

    private String buildCloudFrontUrl(String section, String fileName) {
        return String.format("%s/%s/%s", cloudFrontUrl, section, fileName);
    }

    public ResponseEntity<UploadResponse> uploadFile(String section, MultipartFile file, HttpServletRequest request, boolean imageOnly) {
        if (!ALLOWED_SECTIONS.contains(section)) {
            return ResponseEntity.badRequest().body(new UploadResponse(false, "Invalid section", null, null, "Invalid section"));
        }

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResponse(false, "No file selected.", null, null, "No file selected."));
        }

        if (imageOnly && !Optional.ofNullable(file.getContentType()).orElse("").startsWith("image")) {
            return ResponseEntity.badRequest().body(new UploadResponse(false, "File is not an image.", null, null, "File is not an image."));
        }

        try {
            String fileName = generateUniqueFileName(file);
            String key = section + "/" + fileName;

            s3Client.putObject(builder -> builder.bucket(bucketName).key(key).build(),
                    RequestBody.fromBytes(file.getBytes()));

            String fileUrl = buildCloudFrontUrl(section, fileName);
            return ResponseEntity.ok(new UploadResponse(true, "File uploaded successfully.", fileName, fileUrl, null));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new UploadResponse(false, "Upload failed.", null, null, e.getMessage()));
        }
    }

    public ResponseEntity<List<UploadResponse>> uploadMultipleFiles(String section, List<MultipartFile> files, HttpServletRequest request, boolean imageOnly) {
        if (!ALLOWED_SECTIONS.contains(section)) {
            return ResponseEntity.badRequest().body(List.of(new UploadResponse(false, "Invalid section", null, null, "Invalid section")));
        }

        List<UploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            if (imageOnly && !Optional.ofNullable(file.getContentType()).orElse("").startsWith("image")) continue;

            try {
                String fileName = generateUniqueFileName(file);
                String key = section + "/" + fileName;

                s3Client.putObject(builder -> builder.bucket(bucketName).key(key).build(),
                        RequestBody.fromBytes(file.getBytes()));

                responses.add(new UploadResponse(true, "File uploaded successfully.", fileName, buildCloudFrontUrl(section, fileName), null));

            } catch (IOException e) {
                responses.add(new UploadResponse(false, "Failed to upload file.", null, null, e.getMessage()));
            }
        }

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<String>> listFiles(String section, HttpServletRequest request) {
        if (!ALLOWED_SECTIONS.contains(section)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        try {
            ListObjectsV2Response response = s3Client.listObjectsV2(builder -> builder.bucket(bucketName).prefix(section + "/"));

            List<String> fileUrls = response.contents().stream()
                    .map(S3Object::key)
                    .filter(key -> !key.endsWith("/"))
                    .map(key -> buildCloudFrontUrl(section, key.substring((section + "/").length())))
                    .toList();

            return ResponseEntity.ok(fileUrls);

        } catch (S3Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    public ResponseEntity<UploadResponse> deleteFile(String section, String filename) {
        if (!ALLOWED_SECTIONS.contains(section)) {
            return ResponseEntity.badRequest().body(new UploadResponse(false, "Invalid section", null, null, "Invalid section"));
        }

        String key = section + "/" + filename;
        try {
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key).build());
            return ResponseEntity.ok(new UploadResponse(true, "File deleted successfully.", filename, null, null));
        } catch (S3Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UploadResponse(false, "Failed to delete file.", null, null, e.awsErrorDetails().errorMessage()));
        }
    }

    public ResponseEntity<Map<String, Object>> deleteMultipleFiles(String section, List<String> filenames) {
        if (!ALLOWED_SECTIONS.contains(section)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid section: " + section));
        }

        List<ObjectIdentifier> toDelete = filenames.stream()
                .map(name -> ObjectIdentifier.builder().key(section + "/" + name).build())
                .toList();

        try {
            DeleteObjectsResponse result = s3Client.deleteObjects(DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(Delete.builder().objects(toDelete).build())
                    .build());

            List<String> deleted = result.deleted().stream().map(DeletedObject::key).toList();
            List<String> deletedFiles = deleted.stream().map(k -> k.substring((section + "/").length())).toList();

            Map<String, Object> response = new HashMap<>();
            response.put("deletedFiles", deletedFiles);
            response.put("failedFiles", filenames.stream().filter(f -> !deletedFiles.contains(f)).toList());
            response.put("success", true);
            response.put("message", "File deletion process completed.");
            return ResponseEntity.ok(response);

        } catch (S3Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete files", "details", e.awsErrorDetails().errorMessage()));
        }
    }
}

