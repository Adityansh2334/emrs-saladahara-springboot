package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.AssignmentExams;
import org.labs.jkcloud.emrs.repository.AssignmentExamsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentExamsService {

    private final AssignmentExamsRepository assignmentExamsRepository;

    public AssignmentExams getAssignmentExamsById(Long id) {
        return assignmentExamsRepository.findById(id).orElse(null);
    }

    public List<AssignmentExams> getAllAssignmentExams() {
        return assignmentExamsRepository.findAll();
    }

    public void deleteAssignmentExams(Long id) {
        assignmentExamsRepository.deleteById(id);
    }

    public List<AssignmentExams> saveAssignmentExams(AssignmentExams[] assignments) {
        return assignmentExamsRepository.saveAll(Arrays.asList(assignments));
    }

}
