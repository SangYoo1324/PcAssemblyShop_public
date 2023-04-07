package com.example.pcAssemblyShop.api;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DashboardApiControllerTest {

    @Test
    void deleteNullImage() {

        //body에서 이미지 다 추출

        String body =

                "<figure class=\"image\"><img " +
                "src=\"https://res.cloudinary.com/hanbfrtij/image/" +
                "upload/v1680805198/euwma2ysohvrq2kcb5kk.jpg\"></figure>"
                +"<figure class=\"image\"><img " +
                "src=\"https://res.cloudinary.com/hanbfrtij/image/" +
                "upload/v1680805198/euwma2ysohvrq2kcb5kk.jpg\"></figure>";

        String wordToSearch =
                "src=\"";

        String wordToSearchforEnd = "</figure>";
        int indexForEnd = body.indexOf(wordToSearchforEnd);
        int index= body.indexOf(wordToSearch);

        Map<Integer,Integer> indexMap = new HashMap<>();

        while(true){
            if(index ==-1 ||indexForEnd ==-1)
                break;
            indexMap.put(index, indexForEnd);

            index= body.indexOf(wordToSearch,index+wordToSearch.length()); // index부터 끝까지 탐색(inclusive)

            indexForEnd= body.indexOf(wordToSearchforEnd,indexForEnd+wordToSearch.length());
        }

        log.info("occurance:::::::::::"+indexMap.entrySet());



        List<String> urlListFromBody = indexMap.entrySet().stream().map(e->{
           return body.substring(e.getKey()+5,e.getValue()-2);
       }).collect(Collectors.toList());

        //assuring url is successfully extracted
     for(String url : urlListFromBody){
         log.info("url fully captured?  "+ url);
     }




        // 추출한 이미지 url로 검색해서 이미즏ㄹ에 article 넘버 삽입



//cloudinary에서도 이미지 지우기 테스트
       String url= "https://res.cloudinary.com/hanbfrtij/image/upload/v1680828773/xxqfkszxeo1hzydthb8f.jpg";
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hanbfrtij",
                "api_key", "823367694169484",
                "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                "secure", true));

        String regexChangedUrl = url.replaceAll(".*\\/([^\\/]*)(\\.[^\\.]*|$)", "$1");
          String  changedUrl=     url.substring(url.lastIndexOf("/")+1,url.length()-4);

    log.info(changedUrl);
        log.info(regexChangedUrl);

//        assertEquals();
    }
}