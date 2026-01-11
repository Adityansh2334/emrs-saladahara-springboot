package org.labs.jkcloud.emrs.service;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.HallOfAdmin;
import org.labs.jkcloud.emrs.repository.HallOfAdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallOfAdminService {

    private final HallOfAdminRepository hallOfAdminRepository;

    public HallOfAdmin saveHallOfAdmin(HallOfAdmin hallOfAdmin) {
        return hallOfAdminRepository.save(hallOfAdmin);
    }

    public List<HallOfAdmin> getAllHallOfAdmin() {
        return hallOfAdminRepository.findAll();
    }

    public void deleteHallOfAdmin(Long id) {
        hallOfAdminRepository.deleteById(id);
    }

    public HallOfAdmin getHallOfAdminById(Long id) {
        return hallOfAdminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Hall of admin not found with id " + id));
    }

    public HallOfAdmin updateHallOfAdmin(HallOfAdmin updatedHallOfAdmin) {
        HallOfAdmin existingHallOfAdmin = getHallOfAdminById(updatedHallOfAdmin.getId());
        existingHallOfAdmin.setName(updatedHallOfAdmin.getName());
        existingHallOfAdmin.setDesignation(updatedHallOfAdmin.getDesignation());
        existingHallOfAdmin.setPhoto(updatedHallOfAdmin.getPhoto());
        return hallOfAdminRepository.save(existingHallOfAdmin);
    }
}
