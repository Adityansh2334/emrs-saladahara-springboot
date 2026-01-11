package org.labs.jkcloud.emrs.service;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.ExamResults;
import org.labs.jkcloud.emrs.repository.ExamResultsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamResultsService {

    private final ExamResultsRepository examResultsRepository;

    public List<ExamResults> saveResults(ExamResults[] results) {
        return examResultsRepository.saveAll(Arrays.asList(results));
    }

    public void deleteResults(Long id) {
        examResultsRepository.deleteById(id);
    }

    public ExamResults getResultsById(Long id) {
        return examResultsRepository.findById(id).orElse(null);
    }

    public List<ExamResults> getAllResults() {
        return examResultsRepository.findAll();
    }

}
