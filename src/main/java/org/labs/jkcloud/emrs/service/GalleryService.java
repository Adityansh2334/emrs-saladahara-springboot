package org.labs.jkcloud.emrs.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.Gallery;
import org.labs.jkcloud.emrs.model.GalleryImages;
import org.labs.jkcloud.emrs.model.VO.GalleryResponse;
import org.labs.jkcloud.emrs.repository.GalleryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public List<GalleryResponse> getAllGalleries() {
        List<GalleryResponse> galleryResponseList = new ArrayList<>();
        for (Gallery gallery : galleryRepository.findAll()) {
            GalleryResponse galleryResponse = new GalleryResponse();
            galleryResponse.setId(gallery.getId());
            galleryResponse.setTitle(gallery.getTitle());
            galleryResponse.setCoverImage(gallery.getCoverImage());
            galleryResponse.setImages(gallery.getImages().stream().map(GalleryImages::getImageUrl).toArray(String[]::new));
            galleryResponseList.add(galleryResponse);
        }
        return galleryResponseList;
    }

    public Optional<Gallery> getGalleryById(Long id) {
        return galleryRepository.findById(id);
    }

    @Transactional
    public Gallery saveGallery(Gallery gallery) {
        if (gallery.getImages() != null) {
            for (GalleryImages image : gallery.getImages()) {
                image.setGallery(gallery); // link child to parent
            }
        }
        return galleryRepository.save(gallery);
    }

    @Transactional
    public Gallery updateGallery(Long id, Gallery updatedGallery) {
        return galleryRepository.findById(id).map(existing -> {
            existing.setTitle(updatedGallery.getTitle());
            existing.setCoverImage(updatedGallery.getCoverImage());
            existing.getImages().clear();
            if (updatedGallery.getImages() != null) {
                for (GalleryImages img : updatedGallery.getImages()) {
                    img.setGallery(existing);
                    existing.getImages().add(img);
                }
            }
            return galleryRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Gallery not found with id " + id));
    }

    public void deleteGallery(Long id) {
        galleryRepository.deleteById(id);
    }

    public List<Gallery> getAllGalleryImagesById(Long id) {
        return galleryRepository.getGalleriesById(id);
    }
}
