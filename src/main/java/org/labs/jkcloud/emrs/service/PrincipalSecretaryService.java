package org.labs.jkcloud.emrs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.PrincipalSecretary;
import org.labs.jkcloud.emrs.repository.PrincipalSecretaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalSecretaryService {

    private final PrincipalSecretaryRepository repo;

    public PrincipalSecretary create(PrincipalSecretary principalSecretary) {
        try {
            System.out.println("Principal Secretary created"+ new ObjectMapper().writeValueAsString(principalSecretary));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return repo.save(principalSecretary);
    }
    public PrincipalSecretary update(PrincipalSecretary updated) {
        try {
            System.out.println("Principal Secretary created"+ new ObjectMapper().writeValueAsString(updated));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PrincipalSecretary existing = repo.findById(updated.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Principal Secretary not found with id " + updated.getId()));
        existing.setName(updated.getName());
        existing.setImageUrl(updated.getImageUrl());
        existing.setDesignation(updated.getDesignation());
        existing.setMessage(updated.getMessage());
        return repo.save(existing);
    }
    public List<PrincipalSecretary> getAll() {
        return repo.findAll();
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Principal Secretary not found with id " + id);
        }
        repo.deleteById(id);
    }
    public List<PrincipalSecretary> getByPerson(String type) {
        if(type == null) {
            throw new ResourceNotFoundException("Principal Secretary not found for null");
        }
        return repo.findByType(type);
    }
}
