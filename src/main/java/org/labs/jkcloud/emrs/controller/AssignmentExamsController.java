package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.AssignmentExams;
import org.labs.jkcloud.emrs.service.AssignmentExamsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment-exams")
@RequiredArgsConstructor
public class AssignmentExamsController {

    private final AssignmentExamsService assignmentExamsService;

    @GetMapping
    public List<AssignmentExams> getAllAssignmentExams() {
        return assignmentExamsService.getAllAssignmentExams();
    }

    @GetMapping("/{id}")
    public AssignmentExams getAssignmentExamsById(@PathVariable Long id) {
        return assignmentExamsService.getAssignmentExamsById(id);
    }

    @DeleteMapping ("/{id}")
    public void deleteAssignmentExams(@PathVariable Long id) {
        assignmentExamsService.deleteAssignmentExams(id);
    }

    @PostMapping
    public List<AssignmentExams> saveAssignmentExams(@RequestBody AssignmentExams[] assignments) {
        return assignmentExamsService.saveAssignmentExams(assignments);
    }

}
