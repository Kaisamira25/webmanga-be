package com.alpha.lainovo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UploadService {
    private Cloudinary cloudinary;

    public UploadService(String cloudName, String apiKey, String apiSecret) {
        cloudinary = new Cloudinary(
                ObjectUtils.asMap("cloud_name", cloudName, "api_key", apiKey, "api_secret", apiSecret));
    }

    public UploadService(String Cloudinary_url) {
        cloudinary = new Cloudinary(Cloudinary_url);
    }

    public UploadService() {
        cloudinary = new Cloudinary("cloudinary://675224277872548:QlscTL5MtNyRqTAJS5IKJtoaIrs@dnsfh307i");
    }

    public String uploadImage(String imagePath) throws Exception {
        try {
            Map uploadResult = cloudinary.uploader().upload(imagePath, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            throw new Exception("Lỗi khi tải lên hình ảnh: " + e.getMessage());
        }
    }

    public String uploadImage(String imagePath, Object... option) throws Exception {
        try {
            Map uploadResult = cloudinary.uploader().upload(imagePath, ObjectUtils.asMap(option));
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            throw new Exception("Lỗi khi tải lên hình ảnh: " + e.getMessage());
        }
    }

    public void deleteImage(String...publicId ) throws Exception {
        try {
            // Xóa hình ảnh khỏi Cloudinary dựa trên 'public_id'
            for (String x : publicId) {
                cloudinary.uploader().destroy(x, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            throw new Exception("Lỗi khi xóa hình ảnh: " + e.getMessage());
        }
    }
    public void deleteFolder(String folder) throws Exception{
        ApiResponse apiResponse= cloudinary.api().deleteFolder(folder,ObjectUtils.emptyMap());
    }

    public void setCloudinary(String cloudName, String apiKey, String apiSecret) {
        cloudinary = new Cloudinary(
                ObjectUtils.asMap("cloud_name", cloudName, "api_key", apiKey, "api_secret", apiSecret));
    }

    public void setCloudinary(String Cloudinary_url) {
        cloudinary = new Cloudinary(Cloudinary_url);
    }
}
