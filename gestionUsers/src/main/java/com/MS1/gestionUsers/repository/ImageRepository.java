package com.bac.tn.gestionusers.repository;

import com.MS1.gestionUsers.models.Image;
import com.MS1.gestionUsers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    Optional<Image> findById(String id);

    Image findByUserId(long user);

    Optional<Image> findByName(String name);
}
