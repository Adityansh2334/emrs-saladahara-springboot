package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.UpcomingExams;
import org.labs.jkcloud.emrs.service.UpcomingExamsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upcoming-exams")
@RequiredArgsConstructor
public class UpcomingExamsController {

    private final UpcomingExamsService upcomingExamsService;

    @GetMapping
    public List<UpcomingExams> getAllUpcomingExams() {
        return upcomingExamsService.getAllUpcomingExams();
    }

    @GetMapping("/{id}")
    public UpcomingExams getUpcomingExamsById(@PathVariable Long id) {
        return upcomingExamsService.getUpcomingExamsById(id);
    }

    @PostMapping
    public List<UpcomingExams> saveUpcomingExams(@RequestBody UpcomingExams[] assessments) {
        return upcomingExamsService.saveUpcomingExams(assessments);
    }

    @DeleteMapping("/{id}")
    public void deleteUpcomingExam(@PathVariable Long id) {
        upcomingExamsService.deleteUpcomingExams(id);
    }

}
