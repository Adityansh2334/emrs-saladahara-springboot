package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.HoaDocuments;
import org.labs.jkcloud.emrs.repository.HoaDocumentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDocumentsService {

    private final HoaDocumentsRepository repo;

    public HoaDocuments saveHoaDocuments(HoaDocuments hoaDocuments) {
        return repo.save(hoaDocuments);
    }

    public HoaDocuments getHoaDocumentsById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<HoaDocuments> getAllHoaDocuments() { return repo.findAll(); }

    public void deleteHoaDocuments(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("HoaDocuments not found with id " + id);
        }
        repo.deleteById(id);
    }
}
