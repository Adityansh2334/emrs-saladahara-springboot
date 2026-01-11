package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Tender;
import org.labs.jkcloud.emrs.service.TendersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenders")
@RequiredArgsConstructor
@CrossOrigin
public class TendersController {

    private final TendersService tendersService;

    @GetMapping
    public List<Tender> getAllTenders() {
        return tendersService.getAllTenders();
    }

    @PostMapping
    public Tender createTender(@RequestBody Tender tender) {
        return tendersService.saveTender(tender);
    }

    @DeleteMapping("/{id}")
    public void deleteTenderById(@PathVariable Long id, @RequestParam String fileUrl) {
        tendersService.deleteTender(id, fileUrl);
    }
}
