package com.doziem.shopping_store.service.image;

import com.doziem.shopping_store.Dto.ImageDto;
import com.doziem.shopping_store.exceptions.ResourceNotFoundException;
import com.doziem.shopping_store.model.Image;
import com.doziem.shopping_store.model.Product;
import com.doziem.shopping_store.repository.ImageRepository;
import com.doziem.shopping_store.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Image found with " + id));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete, ()->{
            throw new ResourceNotFoundException("No Image found with " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file: files){
            try {
                Image image = new Image();

                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl =buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

              Image saveImage = imageRepository.save(image);

              image.setDownloadUrl(buildDownloadUrl+saveImage.getId());

              ImageDto imageDto = new ImageDto();

              imageDto.setImageId(saveImage.getId());
              imageDto.setImageName(saveImage.getFileName());
              imageDto.setDownloadUrl(saveImage.getDownloadUrl());

              savedImageDto.add(imageDto);

            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
