package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Achievement;
import org.labs.jkcloud.emrs.service.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    /**
     * Create a new achievement.
     */
    @PostMapping
    public ResponseEntity<Achievement> createAchievement(@RequestBody Achievement achievement) {
        Achievement saved = achievementService.create(achievement);
        return ResponseEntity.ok(saved);
    }

    /**
     * Update an existing achievement by ID.
     */
    @PutMapping
    public ResponseEntity<Achievement> updateAchievement (@RequestBody Achievement updatedData) {
        Achievement updated = achievementService.update(updatedData);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get all achievements.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        return ResponseEntity.ok(achievementService.getAll());
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<Achievement>> getAchievementBySection(@PathVariable String section) {
        return ResponseEntity.ok(achievementService.getAchievementBySection(section));
    }

    @GetMapping("{id}")
    public ResponseEntity<Achievement> getAchievementById(@PathVariable Long id) {
        return ResponseEntity.ok(achievementService.getAchievementById(id));
    }

    /**
     * Delete an achievement by ID.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.delete(id);
        return ResponseEntity.ok().build();
    }
}
