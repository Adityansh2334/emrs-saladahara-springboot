package org.labs.jkcloud.emrs.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.VO.UploadResponse;
import org.labs.jkcloud.emrs.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/image/{section}")
    public ResponseEntity<?> uploadImage(@PathVariable String section,
                                         @RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {
        return fileUploadService.uploadFile(section, file, request, true);
    }

    @PostMapping("/images/{section}")
    public ResponseEntity<?> uploadImages(@PathVariable String section,
                                          @RequestParam("files") List<MultipartFile> files,
                                          HttpServletRequest request) {
        return fileUploadService.uploadMultipleFiles(section, files, request, true);
    }

    @GetMapping("/images/{section}")
    public ResponseEntity<?> listImages(@PathVariable String section, HttpServletRequest request) {
        return fileUploadService.listFiles(section, request);
    }

    @DeleteMapping("/media/{section}/{filename:.+}")
    public ResponseEntity<?> deleteImage(@PathVariable String section,
                                         @PathVariable String filename) {
        return fileUploadService.deleteFile(section, filename);
    }

    @PostMapping("/file/{section}")
    public ResponseEntity<UploadResponse> uploadFile(@PathVariable String section,
                                                     @RequestParam("file") MultipartFile file,
                                                     HttpServletRequest request) {
        return fileUploadService.uploadFile(section, file, request, false);
    }

    @PostMapping("/files/{section}")
    public ResponseEntity<?> uploadFiles(@PathVariable String section,
                                         @RequestParam("files") List<MultipartFile> files,
                                         HttpServletRequest request) {
        return fileUploadService.uploadMultipleFiles(section, files, request, false);
    }

    @DeleteMapping("/file/{section}/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String section,
                                        @PathVariable String filename) {
        return fileUploadService.deleteFile(section, filename);
    }

    @DeleteMapping("/files/{section}")
    public ResponseEntity<?> deleteMultipleFiles(@PathVariable String section,
                                                 @RequestParam("files") List<String> filenames) {
        return fileUploadService.deleteMultipleFiles(section, filenames);
    }
}