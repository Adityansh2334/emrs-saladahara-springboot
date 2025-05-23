package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.Tender;
import org.labs.jkcloud.emrs.repository.TenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TendersService {

    private final FileUploadService fileUploadService;
    private final TenderRepository repo;

    public void deleteTender(Long id, String fileUrl) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Notices not found with id " + id);
        }
        fileUploadService.deleteFile("tenders", extractFileNameFromUrl(fileUrl));
        repo.deleteById(id);
    }

    public List<Tender> getAllTenders() {
        return repo.findAll();
    }

    public Tender saveTender(Tender tender) {
        return repo.save(tender);
    }

    private String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) return "";
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
