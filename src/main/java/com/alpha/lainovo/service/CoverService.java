package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.repository.CoverRepository;
import com.alpha.lainovo.service.ServiceInterface.CoverInterface;
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
public class CoverService implements CoverInterface {

    private final CoverRepository coverRepo;

    @Override
    @Cacheable(cacheNames = "Cover", key = "'#id'")
    public Cover getByCoverId(Integer id) {
        return coverRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Cover", key = "'#coverList'")
    public List<Cover> getAllCover() {
        return coverRepo.findAll();
    }


    @Override
    public Object create(Cover coverDTO) {
        Cover cover = new Cover();
        cover.setCoverType(coverDTO.getCoverType());
        coverRepo.save(cover);
        log.info(">>>>>> CoverServiceImp:save | Create a Cover with name:{} ", cover.getCoverType());
        return cover;
    }

    @Override
    @Cacheable(cacheNames = "Cover", key = "'#id'")
    public Cover update(Integer id, Cover coverDTO) {
        Cover cover = getByCoverId(id);
        if (cover != null) {
            cover.setCoverType(coverDTO.getCoverType());
            coverRepo.save(cover);
            log.info(">>>>>> CoverServiceImp:update | Update a Cover with name:{} ", cover.getCoverType());
            return cover;
        }
        log.error(">>>>>>> CoverServiceImp:update | No Cover found to update with id: {} ",id);
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "Cover", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        Cover cover = getByCoverId(id);
        if (cover != null) {
            coverRepo.delete(cover);
            return true;
        }
        return false;
    }
    @Override
    public List<Cover> getCoverListbyCover(String cover) {
        return coverRepo.findCoversByCoverTypeContains(cover);
    }


}
