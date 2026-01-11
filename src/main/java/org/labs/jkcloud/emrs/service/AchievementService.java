package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.Achievement;
import org.labs.jkcloud.emrs.repository.AchievementRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository repo;

    public Achievement create(Achievement achievement) {
        return repo.save(achievement);
    }

    public Achievement update(Achievement updated) {
        Achievement existing = repo.findById(updated.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id " + updated.getId()));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCoverImage(updated.getCoverImage());
        return repo.save(existing);
    }

    public List<Achievement> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Achievement not found with id " + id);
        }
        repo.deleteById(id);
    }

    public Achievement getAchievementById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id " + id));
    }

    public List<Achievement> getAchievementBySection(String section) {
        return repo.findBySection(section);
    }
}
