package com.alpha.lainovo.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record RImageDTO(MultipartFile[] file) {
}
