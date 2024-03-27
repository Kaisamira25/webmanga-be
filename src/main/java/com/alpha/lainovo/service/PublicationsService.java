package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import com.alpha.lainovo.utilities.time.Time;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsService implements PublicationsInterface {

    private final PublicationsRepository publicationsRepo;
    private final SortService sortService;

    // Get ALL Publications with Image
    @Transactional
    public List<PublicationsImageDTO> getAllPublicationsWithImage() {
        return publicationsRepo.getAllPublicationsWithImage();
    }

    // Get ALL Publications BEST SELLER with Image
    @Override
    public List<PublicationsImageDTO> getBestSellerPublicationsWithImage() {
        return publicationsRepo.getBestSellerPublicationsWithImage(PageRequest.of(0, 4));
    }

    // Get ALL Publications NEW ARRIVAL with Image
    @Override
    public List<PublicationsImageDTO> getNewArrivalPublicationsWithImage() {
        return publicationsRepo.getNewArrivalPublicationsWithImage(PageRequest.of(0, 4));
    }

    // Get ALL Publications with Image and Paging
    @Override
    public Page<PublicationsImageDTO> getAllPagePublicationsWithImage(int page, int size, String sortField, String sortBy) {
        Sort sort = sortService.sortBy(sortBy, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return publicationsRepo.getPagePublicationsWithImage(pageable);
    }

    @Override
    @Cacheable(cacheNames = "Publications", key = "'#id'")
    public Publications getByPublicationsId(Integer id) {
        return publicationsRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Publications", key = "'#publicationsList'")
    public List<Publications> getAllPublications() {
        return publicationsRepo.findAll(Sort.by(Sort.Direction.DESC, "arrivalDay"));
    }

    @Cacheable(cacheNames = "Publications", key = "'#title'")
    @Override
    public Optional<Publications> findByName(String name) {
        return publicationsRepo.findByPublicationsName(name);
    }


    @Override
    public Object create(Publications publicationsDTO) {
        Publications publications = new Publications();
        publications.setPublicationsName(publicationsDTO.getPublicationsName());
        publications.setUnitPrice(publicationsDTO.getUnitPrice());
        publications.setStock(publicationsDTO.getStock());
        publications.setAuthor(publicationsDTO.getAuthor());
        publications.setPublisher(publicationsDTO.getPublisher());
        publications.setPublicationYear(publicationsDTO.getPublicationYear());
        publications.setSummary(publicationsDTO.getSummary());
        publications.setArrivalDay(Time.getTheTimeRightNow());

        publicationsRepo.save(publications);
        log.info(">>>>>> PublicationsServiceImp:save | Create a Publications with name:{} ", publications.getPublicationsName());
        return publications;
    }

    @Transactional
    @Override
    @Cacheable(cacheNames = "Publications", key = "'#id'")
    public Publications update(Integer id, Publications publicationsDTO) {
        Publications publications = getByPublicationsId(id);
        if (publications != null) {
            publications.setPublicationsName(publicationsDTO.getPublicationsName());
            publications.setUnitPrice(publicationsDTO.getUnitPrice());
            publications.setStock(publicationsDTO.getStock());
            publications.setAuthor(publicationsDTO.getAuthor());
            publications.setPublisher(publicationsDTO.getPublisher());
            publications.setPublicationYear(publicationsDTO.getPublicationYear());
            publications.setSummary(publicationsDTO.getSummary());
            publications.setArrivalDay(Time.getTheTimeRightNow());

            publicationsRepo.save(publications);
            log.info(">>>>>> PublicationsServiceImp:update | Update a Publications with name:{} ", publications.getPublicationsName());
            return publications;
        }
        log.error(">>>>>>> PublicationsServiceImp:update | No Publications found to update with id: {} ",id);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Publications", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        Publications publications = getByPublicationsId(id);
        if (publications != null) {
            publicationsRepo.delete(publications);
            return true;
        }
        return false;
    }


}
