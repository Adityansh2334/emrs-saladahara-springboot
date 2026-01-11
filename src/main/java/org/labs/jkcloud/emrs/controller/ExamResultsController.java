package org.labs.jkcloud.emrs.controller;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.ExamResults;
import org.labs.jkcloud.emrs.service.ExamResultsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-results")
@RequiredArgsConstructor
public class ExamResultsController {

    private final ExamResultsService examResultsService;

    @GetMapping
    public List<ExamResults> getAllResults() {
        return examResultsService.getAllResults();
    }

    @PostMapping
    public List<ExamResults> saveResults(@RequestBody ExamResults[] results) {
        return examResultsService.saveResults(results);
    }

    @DeleteMapping("/{id}")
    public void deleteResults(@PathVariable Long id) {
        examResultsService.deleteResults(id);
    }

    @GetMapping("/{id}")
    public ExamResults getResultsById(@PathVariable Long id) {
        return examResultsService.getResultsById(id);
    }
}
