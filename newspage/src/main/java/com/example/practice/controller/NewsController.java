package com.example.practice.controller;

import com.example.practice.entity.FavoriteUrl;
import com.example.practice.service.FavoriteService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Controller
public class NewsController {
    public static HashMap<String, String> map;

    @Autowired FavoriteService favoriteService;
    @GetMapping(value = "/")
    public String startCrawl(@CookieValue(name = "accountId",required = false)String accountId, String sid1, String sid2, String page, Model model) throws IOException{
        System.out.println("news");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);
        String date = dTime.replace(".", "");

        if(sid1==null) sid1="001";
        if(sid2==null) sid2="";
        if(page==null) page="1";

        ArrayList<String> al1 = new ArrayList<>();
        ArrayList<String> al2 = new ArrayList<>();
        ArrayList<String> al3 = new ArrayList<>();
        ArrayList<String> al4 = new ArrayList<>();
        ArrayList<String> al5 = new ArrayList<>();
        ArrayList<String> al6 = new ArrayList<>();
        ArrayList<String> al7 = new ArrayList<>();

        String address = "https://news.naver.com/main/list.naver?mode=LS2D&mid=sec&sid1="+sid1+"&sid2="+sid2+"&date="+date+"&page="+page;
        System.out.println(address);
        Document rawData = Jsoup.connect(address).get();
        for (Element articleElement : rawData.select("li dl dt a")) {
            String articleUrl = articleElement.attr("href");
            Document doc = Jsoup.connect(articleUrl).get();
            String articleTitle = articleElement.text();
            Elements articleElements = doc.select("#dic_area");

            if(articleElements.get(0).text().length()<150||articleTitle.equals("")||articleTitle.equals("동영상기사"))continue;

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

            if(accountId!=null){
                if(favoriteService.favoriteFind(accountId,articleUrl)) al7.add("1");
                else al7.add("0");
            }else al7.add("0");
        }

        model.addAttribute("urls", al1);
        model.addAttribute("titles", al2);
        model.addAttribute("content",al3);
        model.addAttribute("media",al4);
        model.addAttribute("time",al5);
        model.addAttribute("id",al6);
        model.addAttribute("findDb",al7);
        model.addAttribute("page",page);
        return "news/news.jsp";
    }

}