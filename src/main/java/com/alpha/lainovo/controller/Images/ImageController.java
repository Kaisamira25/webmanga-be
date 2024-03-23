package com.alpha.lainovo.controller.Images;

import com.alpha.lainovo.dto.request.RImageDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.repository.ImageRepository;
import com.alpha.lainovo.service.ImageService;
import com.alpha.lainovo.service.ServiceInterface.ImageInterface;
import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/images")
@SecurityRequirement(name="bearerAuth")
@RequiredArgsConstructor
public class ImageController {

    private final ImageInterface imageInterface;
    private final ImageService imageService;

    @Operation(summary = "Create Images for Publications", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
//    @PostMapping("/{id}/publications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequestMapping(value = "/{id}/publications", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createImageForPublications(
            @PathVariable("id") Integer id,
            @RequestPart("file") MultipartFile[] files) {

        RImageDTO imageDTO = new RImageDTO(files);
        Image image = imageInterface.saveImage(id,imageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "successful",image));
    }


    /**
     * Đây là một phương thức trong một lớp Controller của Spring Boot, được sử dụng để xử lý yêu cầu POST tới đường dẫn `/{id}/publications`, với `id` là một biến động. Phương thức này nhận dữ liệu dưới dạng `MediaType.MULTIPART_FORM_DATA_VALUE`, tức là nó có thể nhận các tệp tin được tải lên.
     *
     * Cụ thể, các tham số của phương thức bao gồm:
     * - `@PathVariable("id") Integer id`: Đây là `id` được trích xuất từ URL. Nó được đánh dấu bởi `@PathVariable`, cho biết rằng giá trị của nó sẽ được lấy từ URL.
     * - `@RequestPart("file") MultipartFile[] files`: Đây là một mảng các tệp tin được tải lên. `@RequestPart` cho biết rằng đây là một phần của yêu cầu, và `"file"` là tên của phần đó trong yêu cầu.
     *
     * Trong phương thức, một đối tượng `RImageDTO` mới được tạo từ các tệp tin đã tải lên. Sau đó, hình ảnh được lưu bằng cách gọi phương thức `saveImage` trên `imageInterface`, với `id` và `imageDTO` là các tham số.
     *
     * Cuối cùng, phương thức trả về một `ResponseEntity` với trạng thái `HttpStatus.OK` và một đối tượng `Message` mới. Đối tượng `Message` này chứa một mã (1), một thông điệp ("successful"), và hình ảnh đã được lưu. Điều này cho phép người dùng biết rằng hình ảnh của họ đã được lưu thành công.
     *
     * Đây là một cách tiêu chuẩn để xử lý tải lên tệp tin trong Spring Boot.
     * @param id
     * @return
     */

    @Operation(summary = "delete Images for Publications", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication not found", responseCode = "400")})
    @DeleteMapping("/{id}/publications")
    public ResponseEntity<Message> deleteImageForPublications(@PathVariable("id") Integer id) {
        boolean status = imageInterface.deleteImage(id);
        if(status == true) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Delete successful"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Delete fail"));

    }

    @GetMapping("/all")
    @Operation(summary = "Find All Images", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllImages() {
        List<Image> list = imageService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "Find an Image with the ID", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Image not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getImageById(@PathVariable("imageId") Integer id) {
        Image image = imageService.findById(id);
        if (image != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", image));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Image does not exist"));
    }

}

