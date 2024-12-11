package com.doziem.shopping_store.repository;

import com.doziem.shopping_store.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
