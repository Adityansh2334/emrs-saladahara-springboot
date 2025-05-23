package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Gallery;
import org.labs.jkcloud.emrs.model.VO.GalleryResponse;
import org.labs.jkcloud.emrs.service.GalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping
    public ResponseEntity<Gallery> createGallery(@RequestBody Gallery gallery) {
        return ResponseEntity.ok(galleryService.saveGallery(gallery));
    }

    @GetMapping
    public ResponseEntity<List<GalleryResponse>> getAllGalleries() {
        return ResponseEntity.ok(galleryService.getAllGalleries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Gallery>> getAllGalleriesById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getAllGalleryImagesById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable Long id, @RequestBody Gallery gallery) {
        return ResponseEntity.ok(galleryService.updateGallery(id, gallery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.ok().build();
    }
}
