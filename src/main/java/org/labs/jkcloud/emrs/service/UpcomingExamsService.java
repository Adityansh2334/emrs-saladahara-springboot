package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.UpcomingExams;
import org.labs.jkcloud.emrs.repository.UpcomingExamsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpcomingExamsService {

    private final UpcomingExamsRepository upcomingExamsRepository;

    public List<UpcomingExams> getAllUpcomingExams() {
        return upcomingExamsRepository.findAll();
    }

    public UpcomingExams getUpcomingExamsById(Long id) {
        return upcomingExamsRepository.findById(id).orElse(null);
    }

    public List<UpcomingExams> saveUpcomingExams(UpcomingExams[] assessments) {
        return upcomingExamsRepository.saveAll(Arrays.asList(assessments));
    }

    public void deleteUpcomingExams(Long id) {
        upcomingExamsRepository.deleteById(id);
    }

}
