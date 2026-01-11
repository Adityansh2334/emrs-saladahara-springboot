package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.Notice;
import org.labs.jkcloud.emrs.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final FileUploadService fileUploadService;
    private final NoticeRepository repo;

    public void deleteNotice(Long id, String fileUrl) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Notices not found with id " + id);
        }
        fileUploadService.deleteFile("notices", extractFileNameFromUrl(fileUrl));
        repo.deleteById(id);
    }

    public List<Notice> getAllNotices() {
        return repo.findAll();
    }

    public Notice saveNotice(Notice notice) {
        return repo.save(notice);
    }

    private String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) return "";
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
