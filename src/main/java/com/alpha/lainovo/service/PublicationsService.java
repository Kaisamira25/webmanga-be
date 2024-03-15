package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import com.alpha.lainovo.utilities.time.Time;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsService implements PublicationsInterface {

    private final PublicationsRepository publicationsRepo;

    private final GenreRepository genreRepo;

    @Override
    @Cacheable(cacheNames = "Publications", key = "'#id'")
    public Publications getByPublicationsId(Integer id) {
        return publicationsRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Publications", key = "'#publicationsList'")
    public List<Publications> getAllPublications() {
        return publicationsRepo.findAll();
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
