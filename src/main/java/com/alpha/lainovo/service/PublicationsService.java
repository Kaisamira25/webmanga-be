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

//    @Transactional
//    public List<PublicationsImageDTO> getAllPublicationsWithImage() {
//        log.info(": {}", publicationsRepo.selectAllPublicationsWithImage());
//        return publicationsRepo.selectAllPublicationsWithImage();
//    }
@PersistenceContext
private EntityManager entityManager;

    public List<Publications> getAllPublicationsWithImage() {
        Query query = entityManager.createNativeQuery(
                "CALL selectPublicationsWithImage()", Publications.class);
        return query.getResultList();
    }

//        @PersistenceContext
//        private EntityManager entityManager;
//
//        public List<Object[]> getAllPublicationsWithImage() {
//            Query query = entityManager.createNativeQuery(
//                    "SELECT p.*, i.image_url " +
//                            "FROM Publications p " +
//                            "INNER JOIN Images i ON p.publications_id = i.publications_id"
//            );
//            return query.getResultList();
//        }


//    public Page<Object[]> getAllPagePublicationsWithImage(int page, int size, String sortField, String sortBy) {
//        Sort sort = sortService.sortBy(sortBy, sortField);
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<Publications> pagePublications = publicationsRepo.getPagePublications(pageable);
//        List<Object[]> publicationsWithImage = new ArrayList<>();
//
//        for (Publications publication : pagePublications) {
//            Query query = entityManager.createNativeQuery(
//                    "SELECT p.*, i.image_url " +
//                            "FROM Publications p " +
//                            "INNER JOIN Images i ON p.publications_id = i.publications_id " +
//                            "WHERE p.publications_id = :publicationsId"
//            );
//            query.setParameter("publicationsId", publication.getPublicationsID());
//            List<Object[]> result = query.getResultList();
//            publicationsWithImage.addAll(result);
//        }
//
//        return new PageImpl<>(publicationsWithImage, pageable, publicationsWithImage.size());
//    }


//    public List<Object[]> getAllPublicationsWithImage(Integer PublicationsId) {
//        Query query = entityManager.createNativeQuery(
//                "SELECT p.*, i.image_url " +
//                        "FROM Publications p " +
//                        "INNER JOIN Images i ON p.publications_id = i.publications_id " +
//                        "WHERE p.publications_id = :PublicationsId"
//        );
//        query.setParameter("PublicationsId", PublicationsId);
//        return query.getResultList();
//    }




    @Override
    public List<Publications> getBestSellerPublications() {
        return publicationsRepo.getBestSellerPublications(PageRequest.of(0, 9));
    }

    @Override
    public List<Publications> getNewArrivalPublications() {
        return publicationsRepo.getNewArrivalPublications(PageRequest.of(0, 6));
    }

    @Override
    public Page<Publications> getAllPagePublications(int page, int size, String sortField, String sortBy) {
        Sort sort = sortService.sortBy(sortBy, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return publicationsRepo.getPagePublications(pageable);
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
