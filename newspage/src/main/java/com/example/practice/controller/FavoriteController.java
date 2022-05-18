package com.example.practice.controller;

import com.example.practice.entity.FavoriteUrl;
import com.example.practice.service.AccountService;
import com.example.practice.service.FavoriteService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.List;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Controller
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    public static HashMap<String, String> map;

    @GetMapping("/favorite/create")
    public String favoriteCreate(String username,String favoriteUrl){
        favoriteService.favoriteCreate(username,favoriteUrl);
        return "";
    }

    @DeleteMapping("/favorite/delete")
    public String favoriteDelete(String username,String favoriteUrl){
        favoriteService.favoriteDelete(username,favoriteUrl);
        return "";
    }
    @GetMapping("/favorite")
    public String favoriteForm(@CookieValue(name = "accountId")String accountId, Model model) throws IOException {
        ArrayList<String> al1 = new ArrayList<>();
        ArrayList<String> al2 = new ArrayList<>();
        ArrayList<String> al3 = new ArrayList<>();
        ArrayList<String> al4 = new ArrayList<>();
        ArrayList<String> al5 = new ArrayList<>();
        ArrayList<String> al6 = new ArrayList<>();

        List<FavoriteUrl> favoriteUrlList = favoriteService.favoriteRead(accountId);
        for (FavoriteUrl favoriteElement : favoriteUrlList) {
            String articleUrl = favoriteElement.getUrl();
            Document doc = Jsoup.connect(articleUrl).get();
            String articleTitle = String.valueOf(doc.select(".media_end_head_headline"));

            Elements articleElements = doc.select("#dic_area");
            if(articleElements.get(0).text().length()<150||articleTitle.equals("동영상기사"))continue;

            Elements articleTimeElements = doc.select(".media_end_head_info_datestamp_time, ._ARTICLE_DATE_TIME");
            Elements articleMediaElements = doc.select(".media_end_head_top_logo_img, .light_type");

            String articleContext = articleElements.get(0).toString();
            String articleMedia = articleMediaElements.attr("src");
            String articleTime = articleTimeElements.attr("data-date-time");
            String articleid = articleUrl
                    .replace("?","")
                    .replace("/","")
                    .replace("=","")
                    .replace("&","")
                    .replace(".","")
                    .replace(":","");

            al1.add(articleUrl);
            al2.add(articleTitle);
            al3.add(articleContext);
            al4.add(articleMedia);
            al5.add(articleTime);
            al6.add(articleid);

        }

        model.addAttribute("urls", al1);
        model.addAttribute("titles", al2);
        model.addAttribute("content",al3);
        model.addAttribute("media",al4);
        model.addAttribute("time",al5);
        model.addAttribute("id",al6);

        return "news/favorite.jsp";
    }

}
