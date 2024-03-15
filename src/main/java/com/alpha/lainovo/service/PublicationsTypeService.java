package com.alpha.lainovo.service;

import com.alpha.lainovo.EntityUtils.EntityUtils;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.repository.TypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsTypeService {

    private final PublicationsRepository publicationsRepo;

    private final TypeRepository typeRepo;

    @Transactional
    public void addTypeToPublications(Integer publicationsId, Integer typeId) {
        Publications publications = publicationsRepo.findByPublicationsID(publicationsId).get();
        Type type = typeRepo.findByTypeID(typeId).get();
        publications.getTypes().add(type);
        publicationsRepo.save(publications);
        log.info(">>>>>> PublicationsTypeServiceImp:addTypeToPublications | Added Type with id:{} to Publications with id:{} ", typeId, publicationsId);
    }

    @Transactional
    public boolean removeTypeFromPublications(Integer publicationsId, Integer typeId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);
        Optional<Type> optionalType = typeRepo.findByTypeID(typeId);

        if (optionalPublications.isPresent() && optionalType.isPresent()) {
            Publications publications = optionalPublications.get();
            Type type = optionalType.get();
//            publications.getGenres().forEach(g -> {
//                System.out.println("Genre ID: " + g.getGenreID());
//            });
            if (publications.getTypes().stream().anyMatch(t -> EntityUtils.equals(t, type))) {
                publications.getTypes().removeIf(t -> EntityUtils.equals(t, type));
                publicationsRepo.save(publications);
                return true;
            } else {
                return false;
            }
        } else {
            log.error(">>>>>>> PublicationsTypeServiceImp:removeTypeFromPublications | No Publications found with id: {} or no Type found with id: {}", publicationsId, typeId);
        }
        return false;
    }




}
