package com.doziem.shopping_store.service.image;

import com.doziem.shopping_store.Dto.ImageDto;
import com.doziem.shopping_store.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
