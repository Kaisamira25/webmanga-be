package com.alpha.lainovo.service;


import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.repository.TypeRepository;
import com.alpha.lainovo.service.ServiceInterface.TypeInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeService implements TypeInterface {

    private final TypeRepository typeRepo;

    private final PublicationsRepository publicationsRepository;

    @Override
    @Cacheable(cacheNames = "Type", key = "'#id'")
    public Type getByTypeId(Integer id) {
        return typeRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Type", key = "'#typeList'")
    public List<Type> getAllType() {
        return typeRepo.findAll();
    }


    @Override
    public Object create(Type typeDTO) {
        Type type = new Type();
        type.setTypeName(typeDTO.getTypeName());
        typeRepo.save(type);
        log.info(">>>>>> TypeServiceImp:save | Create a Type with name:{} ", type.getTypeName());
        return type;
    }

    @Override
    @Cacheable(cacheNames = "BookType", key = "'#id'")
    public Type update(Integer id, Type typeDTO) {
        Type type = getByTypeId(id);
        if (type != null) {
            type.setTypeName(typeDTO.getTypeName());
            typeRepo.save(type);
            log.info(">>>>>> TypeServiceImp:update | Update a Type with name:{} ", type.getTypeName());
            return type;
        }
        log.error(">>>>>>> TypeServiceImp:update | No Type found to update with id: {} ",id);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Type", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        Type bookType = getByTypeId(id);
        if (bookType != null) {
            typeRepo.delete(bookType);
            return true;
        }
        return false;
    }

    @Override
    public List<Type> getTypeListbyType(String type) {
        return typeRepo.findTypesByTypeNameContains(type);
    }

    public Set<Type> getTypesByPublicationId(Integer publicationId) {
        Set<Type> types = new HashSet<>();
        Optional<Publications> optionalPublication = publicationsRepository.findById(publicationId);
        if (optionalPublication.isPresent()) {
            Publications publication = optionalPublication.get();
            types = publication.getTypes();
        }
        return types;
    }
}
