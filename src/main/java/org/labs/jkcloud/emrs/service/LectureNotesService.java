package org.labs.jkcloud.emrs.service;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.LectureNotes;
import org.labs.jkcloud.emrs.repository.LectureNotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureNotesService {

    private final FileUploadService fileUploadService;
    private final LectureNotesRepository repo;

    public void deleteLectureNotes(Long id, String fileUrl) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Lecture notes not found with id " + id);
        }
        fileUploadService.deleteFile("lecture_notes", extractFileNameFromUrl(fileUrl));
        repo.deleteById(id);
    }

    public List<LectureNotes> getAllLectureNotes() {
        return repo.findAll();
    }

    public LectureNotes saveLectureNotes(LectureNotes lectureNotes) {
        return repo.save(lectureNotes);
    }

    private String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) return "";
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
