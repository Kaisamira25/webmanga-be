package com.alpha.lainovo.utilities.FileUpload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageFileService {

    public List<String> uploadfileImg(String folder, MultipartFile[] uploadFile) {

        String userDirectory = System.getProperty("user.dir");

        File uploadRootDir = new File(userDirectory + "/src/main/resources/static/" + folder);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        List<String> fileNames = new ArrayList<>();

        try {
            for (MultipartFile uploadFiles : uploadFile) {
                String fileName = UUID.randomUUID().toString() + "_" + uploadFiles.getOriginalFilename();
                File fileServer = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);

                Files.copy(uploadFiles.getInputStream(), Paths.get(fileServer.getAbsolutePath()));
                fileNames.add("/static" + "/" + folder + "/" + fileName);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileNames;
    }

    public void deletefileImg(String folder, String pathImage) {

        String userDirectory = System.getProperty("user.dir");

        String result = null;
        String reverse = new StringBuilder(pathImage).reverse().toString();

        int viTri = reverse.indexOf("/");

        result = new StringBuilder(reverse.substring(0, viTri)).reverse().toString();

        File uploadRootDir = new File(userDirectory + "/src/main/resources/static/" + folder, result);

        if (uploadRootDir.exists() && uploadRootDir.isFile()) {
            if (uploadRootDir.delete()) {

                log.info(">>>>>> ImageFileService:deletefileImg | delete image with path: {}", pathImage);
            } else {
                log.error(">>>>>> ImageFileService:deletefileImg | delete image error: {}", pathImage);
            }
        } else {
            log.error(">>>>>> ImageFileService:deletefileImg |  image dose not exist : {}", pathImage);
        }
    }
}

/**
 * Đây là một lớp `ImageFileService` trong Java, có hai phương thức chính: `uploadfileImg` và `deletefileImg`.
 *
 * 1. `uploadfileImg(String folder, MultipartFile[] uploadFile)`: Phương thức này được sử dụng để tải lên các tệp hình ảnh.
 *     - `folder`: Thư mục mà các tệp hình ảnh sẽ được lưu.
 *     - `uploadFile`: Mảng các tệp hình ảnh cần tải lên.
 *     - Phương thức này sẽ tạo một thư mục mới nếu nó chưa tồn tại, sau đó tải lên từng tệp hình ảnh và lưu chúng vào thư mục đã chỉ định.
 *     Tên tệp sẽ được tạo ra bằng cách sử dụng UUID ngẫu nhiên và tên tệp gốc. Cuối cùng, nó sẽ trả về danh sách các đường dẫn tệp hình ảnh đã tải lên.
 *
 *
 * 2. `deletefileImg(String folder, String pathImage)`: Phương thức này được sử dụng để xóa một tệp hình ảnh.
 *     - `folder`: Thư mục chứa tệp hình ảnh cần xóa.
 *     - `pathImage`: Đường dẫn của tệp hình ảnh cần xóa.
 *     - Phương thức này sẽ tìm tệp hình ảnh trong thư mục đã chỉ định và xóa nó nếu tệp tồn tại.
 *     Nếu tệp không tồn tại hoặc xóa không thành công, nó sẽ ghi log lỗi tương ứng.
 *
 * Lưu ý: Đoạn mã này sử dụng thư viện `slf4j` để ghi log, và `MultipartFile` để xử lý tệp tin được tải lên.
 * Ngoài ra, nó cũng sử dụng `RuntimeException` để xử lý các ngoại lệ có thể xảy ra khi tải lên hoặc xóa tệp.
 */

