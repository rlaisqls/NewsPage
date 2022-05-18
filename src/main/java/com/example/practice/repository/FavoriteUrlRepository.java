package com.example.practice.repository;

import com.example.practice.entity.FavoriteUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteUrlRepository extends JpaRepository<FavoriteUrl,Long> {
    List<FavoriteUrl> findByUsername(String username);
    List<FavoriteUrl> findByUrl(String username);
    List<FavoriteUrl> findByUsernameAndUrl(String username,String url);


}


