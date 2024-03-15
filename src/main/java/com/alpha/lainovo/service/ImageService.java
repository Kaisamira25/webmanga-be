package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.RImageDTO;
import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.ImageRepository;
import com.alpha.lainovo.service.ServiceInterface.ImageInterface;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import com.alpha.lainovo.utilities.FileUpload.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService implements ImageInterface {

    private final ImageRepository imageRepo;

    private final PublicationsInterface iPublications;

    private final ImageFileService imagefileService;

    @Value("${nameFolder.save.images}")
    private String NAME_FOLDER_TO_SAVE_IMAGE;
    @Override
    @CachePut(cacheNames = "image", key = "'#id'")
    public Image saveImage(Integer id, RImageDTO imageDTO) {

        Publications publications = iPublications.getByPublicationsId(id);
        if (publications != null && imageDTO.file() != null) {
            List<String> file = imagefileService.uploadfileImg(NAME_FOLDER_TO_SAVE_IMAGE, imageDTO.file());

            for (int i = 0; i < imageDTO.file().length; i++) {
                Image image = new Image();
                image.setImageURL(file.get(i));
                image.setPublications(publications);
                imageRepo.save(image);
            }

        }
        return null;
    }


    /**
     * Đây là một phương thức trong một lớp Service của Spring Boot, được sử dụng để lưu hình ảnh.
     *
     * Cụ thể, các thành phần của phương thức bao gồm:
     *
     * - `@Value("${nameFolder.save.images}") private String NAME_FOLDER_TO_SAVE_IMAGE;`:
     *      Đây là một biến được khởi tạo từ giá trị của thuộc tính `nameFolder.save.images` trong tệp cấu hình của ứng dụng.
     *
     *
     * - `@Override @CachePut(cacheNames = "image", key = "'#id'") public Image saveImage(Integer id, RImageDTO imageDTO)`:
     *      Đây là phương thức `saveImage` được ghi đè từ interface. `@CachePut` là một annotation của Spring Cache, nó cho biết rằng kết quả của phương thức này sẽ được lưu vào cache với tên là "image" và key là giá trị của `id`.
     *
     *
     * - `Publications publications = iPublications.findById(id);`: Tìm kiếm một đối tượng `Publications` bằng cách sử dụng `id` được cung cấp.
     *
     *
     * - `if (publications != null && imageDTO.file() != null)`: Kiểm tra xem đối tượng `Publications` và tệp tin hình ảnh có tồn tại hay không.
     *
     *
     * - `List<String> file = imagefileService.uploadfileImg(NAME_FOLDER_TO_SAVE_IMAGE, imageDTO.file());`: Gọi phương thức `uploadfileImg` để tải lên tệp tin hình ảnh và lưu trữ đường dẫn của chúng.
     *
     *
     * - `for (int i = 0; i < imageDTO.file().length; i++)`: Duyệt qua tất cả các tệp tin hình ảnh đã tải lên.
     *
     *
     * - `Image image = new Image(); image.setImageURL(file.get(i)); image.setPublications(publications); imageRepo.save(image);`:
     *      Tạo một đối tượng `Image` mới, thiết lập URL của hình ảnh và đối tượng `Publications` liên quan, sau đó lưu đối tượng `Image` này vào cơ sở dữ liệu.
     * - `return null;`: Trả về `null` nếu không có hình ảnh nào được lưu.
     *
     * Đây là một cách tiêu chuẩn để xử lý lưu hình ảnh trong Spring Boot.
     * @param id
     * @return
     */




    @Override
    @CacheEvict(cacheNames = "image", key = "'#id'", allEntries = true)
    public boolean deleteImage(Integer id) {
        Image image = findById(id);
        if(image != null) {
            imagefileService.deletefileImg(NAME_FOLDER_TO_SAVE_IMAGE, image.getImageURL());
            imageRepo.delete(image);
            return true;
        }
        return false;
    }

    @Override
    @CacheEvict(cacheNames = "image", key = "'#id'", allEntries = true)
    public Image findById(Integer id) {
        return imageRepo.findById(id).orElse(null);
    }
}
