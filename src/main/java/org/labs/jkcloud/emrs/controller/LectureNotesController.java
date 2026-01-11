package org.labs.jkcloud.emrs.controller;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.LectureNotes;
import org.labs.jkcloud.emrs.service.LectureNotesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture-notes")
@RequiredArgsConstructor
@CrossOrigin
public class LectureNotesController {

    private final LectureNotesService lectureNotesService;

    @GetMapping
    public List<LectureNotes> getAllLectureNotes() {
        return lectureNotesService.getAllLectureNotes();
    }

    @PostMapping
    public LectureNotes createLectureNotes(@RequestBody LectureNotes lectureNotes) {
        return lectureNotesService.saveLectureNotes(lectureNotes);
    }

    @DeleteMapping("/{id}")
    public void deleteLectureNotes(@PathVariable Long id, @RequestParam String fileUrl) {
        lectureNotesService.deleteLectureNotes(id, fileUrl);
    }

}
