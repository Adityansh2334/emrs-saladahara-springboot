package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.HallOfAdmin;
import org.labs.jkcloud.emrs.model.HoaDocuments;
import org.labs.jkcloud.emrs.service.HallOfAdminService;
import org.labs.jkcloud.emrs.service.HoaDocumentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall-of-admin")
@RequiredArgsConstructor
public class HallOfAdminController {


    private final HallOfAdminService hallOfAdminService;
    private final HoaDocumentsService hoaDocumentsService;

    @PostMapping
    public HallOfAdmin saveHallOfAdmin(@RequestBody HallOfAdmin hallOfAdmin) {
        return hallOfAdminService.saveHallOfAdmin(hallOfAdmin);
    }

    @PostMapping("/hoa-documents")
    public void saveHoaDocuments(@RequestBody HoaDocuments hoaDocuments) {
        hoaDocumentsService.saveHoaDocuments(hoaDocuments);
    }

    @GetMapping({"/hoa-documents"})
    public List<HoaDocuments> getHoaDocuments() {
        return hoaDocumentsService.getAllHoaDocuments();
    }

    @DeleteMapping("/hoa-documents/{id}")
    public void deleteHoaDocuments(@PathVariable Long id) {
        hoaDocumentsService.deleteHoaDocuments(id);
    }

    @GetMapping("/all")
    public List<HallOfAdmin> getAllHallOfAdmin() {
        return hallOfAdminService.getAllHallOfAdmin();
    }

    @DeleteMapping("/{id}")
    public void deleteHallOfAdmin(@PathVariable Long id) {
        hallOfAdminService.deleteHallOfAdmin(id);
    }

    @GetMapping("/{id}")
    public HallOfAdmin getHallOfAdminById(@PathVariable Long id) {
        return hallOfAdminService.getHallOfAdminById(id);
    }

    @PutMapping
    public HallOfAdmin updateHallOfAdmin(@RequestBody HallOfAdmin updatedHallOfAdmin) {
        return hallOfAdminService.updateHallOfAdmin(updatedHallOfAdmin);
    }


}
