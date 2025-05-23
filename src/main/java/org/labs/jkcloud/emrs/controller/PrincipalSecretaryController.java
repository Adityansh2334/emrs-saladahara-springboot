package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.PrincipalSecretary;
import org.labs.jkcloud.emrs.service.PrincipalSecretaryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/principal-secretary")
@RequiredArgsConstructor
public class PrincipalSecretaryController {

    private final PrincipalSecretaryService principalSecretaryService;

    @PostMapping
    public PrincipalSecretary createPrincipalSecretary(@RequestBody PrincipalSecretary principalSecretary) {
        return principalSecretaryService.create(principalSecretary);
    }

    @GetMapping("/all")
    public List<PrincipalSecretary> getAllPrincipalSecretary() {
        return principalSecretaryService.getAll();
    }

    @GetMapping("/{type}")
    public List<PrincipalSecretary> getPrincipalSecretaryByType(@PathVariable String type) {
        return principalSecretaryService.getByPerson(type);
    }

    @DeleteMapping("/{id}")
    public void deletePrincipalSecretary(@PathVariable Long id) {
        principalSecretaryService.delete(id);
    }

    @PutMapping()
    public PrincipalSecretary updatePrincipalSecretary(@RequestBody PrincipalSecretary principalSecretary) {
        return principalSecretaryService.update(principalSecretary);
    }

}
