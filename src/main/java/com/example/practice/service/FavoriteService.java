package com.example.practice.service;


import com.example.practice.entity.FavoriteUrl;
import com.example.practice.repository.FavoriteUrlRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.ManagedBean;
@Service
@ManagedBean
public class FavoriteService {
    @Autowired
    FavoriteUrlRepository favoriteUrlRepository;
    public boolean favoriteFind(String username, String url) {

        if(url==null)return false;
        List<FavoriteUrl> favoriteUrlList = favoriteUrlRepository.findByUsernameAndUrl(username,url);
        System.out.println("find: "+username+" "+url+" "+favoriteUrlList.size());
        if(favoriteUrlList.size()>0) return true;
        else return false;
    }

    public List<FavoriteUrl> favoriteRead(String username){
        List<FavoriteUrl> favoriteUrlList = favoriteUrlRepository.findByUsername(username);
        return favoriteUrlList;
    }
    public void favoriteCreate(String username, String url) {
        FavoriteUrl favoriteUrl = new FavoriteUrl();
        favoriteUrl.setUsername(username);
        favoriteUrl.setUrl(url);
        favoriteUrlRepository.save(favoriteUrl);
    }
    public void favoriteDelete(String username, String url) {
        List<FavoriteUrl> favoriteUrlList = favoriteUrlRepository.findByUsernameAndUrl(username,url);
        for(FavoriteUrl favoriteUrl:favoriteUrlList){
            System.out.println(favoriteUrl.getId());
            favoriteUrlRepository.deleteById(favoriteUrl.getId());
        }
    }
}

