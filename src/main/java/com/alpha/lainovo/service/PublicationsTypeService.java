package com.alpha.lainovo.service;

import com.alpha.lainovo.entityutils.EntityUtils;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.repository.TypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public boolean removeTypeFromPublications(Integer publicationsId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);
        if (optionalPublications.isPresent() ) {
            Publications publications = optionalPublications.get();
            publications.setTypes(null);
            publicationsRepo.save(publications);
            return true;
        } else {
            return false;
        }

    }




}
