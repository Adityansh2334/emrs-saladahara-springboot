package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> getGalleriesById(Long id);
}
