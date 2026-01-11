package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.Faculty;
import org.labs.jkcloud.emrs.repository.FacultyRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyStaffService {

    private final FacultyRepository repo;

    public List<Faculty> getAllFaculties() {
        return repo.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + id));
    }

    public Faculty createFaculty(Faculty faculty) {
        return repo.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        Faculty existing = repo.findById(faculty.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + faculty.getId()));
        existing.setName(faculty.getName());
        existing.setImage(faculty.getImage());
        existing.setDesignation(faculty.getDesignation());
        existing.setCategory(faculty.getCategory());
        existing.setBio(faculty.getBio());
        return repo.save(existing);
    }

    public void deleteFaculty(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Faculty not found with id " + id);
        }
        repo.deleteById(id);
    }

}
