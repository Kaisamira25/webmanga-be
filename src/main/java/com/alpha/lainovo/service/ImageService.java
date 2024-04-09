package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.RImageDTO;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.repository.ImageRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.service.ServiceInterface.ImageInterface;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import com.alpha.lainovo.utilities.FileUpload.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService implements ImageInterface {
    private final PublicationsRepository repoPublic;
    private final ImageRepository imageRepo;

    @Override
    public List<Image> findById(Integer id) {
        return imageRepo.findAllByPublications(repoPublic.findByPublicationsID(id).get());
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    public Object createImage(RImageDTO imageDTO) {
        Image image = new Image();
        image.setImageURL(imageDTO.getUrl());
        image.setPublications(repoPublic.findByPublicationsID(imageDTO.getId()).get());
        imageRepo.save(image);
        System.out.println(image);
        return image;
    }
    @Transactional
    public Boolean delImagebyPublication(Integer id){
        Optional<Publications> optionalPublications = repoPublic.findByPublicationsID(id);
        if (optionalPublications.isPresent()) {
            Publications publications = optionalPublications.get();
            List<Image> images = publications.getImages();
            System.out.println(images.toString());
            images.removeIf(image -> {
                imageRepo.deleteImageByImageID(image.getImageID());
                return true;
            });
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Object create(Image entity) {
        return null;
    }

    @Override
    public Image update(Integer key, Image entity) {
        return null;
    }
}
