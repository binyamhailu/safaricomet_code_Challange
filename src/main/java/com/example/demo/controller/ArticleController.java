package com.example.demo.controller;


import com.example.demo.entity.Article;
import com.example.demo.entity.Visibility;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CreateArticleDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class ArticleController {

    private final ArticleRepository articleRepository ;
    private final TokenRepository tokenRepository ;

    public ArticleController(ArticleRepository articleRepository, TokenRepository tokenRepository) {
        this.articleRepository = articleRepository;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/article")
    public ResponseEntity<?> createArticle(@RequestBody CreateArticleDto createArticleDto, @RequestHeader("authentication-header") String header){



       if(createArticleDto==null){
           return  new ResponseEntity<>(new ApiResponse("the body is empty",false),HttpStatus.BAD_REQUEST);
       }
       //Check The header for valid Token
        if(header==null){
            return new ResponseEntity<>(new ApiResponse("header is  not  available",false),HttpStatus.UNAUTHORIZED);
        }
        //header is available
        Article article = new Article();

        article.setArticle_id(createArticleDto.getArticle_id());
        article.setUser_id(createArticleDto.getUser_id());
        article.setContent(createArticleDto.getContent());
        article.setVisibility(createArticleDto.getVisibility());

        Article createdArticle = articleRepository.save(article);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticles(@RequestHeader("authentication-header") Optional <String> header){
        if(header.isEmpty()){
            //No header wewill bring all the articles with visibility true
            List<Article> articles = articleRepository.getArticleByVisibility(Visibility.PUBLIC);
            return new ResponseEntity<>(articles,HttpStatus.OK);
        }
        return new ResponseEntity<>(articleRepository.findAll(),HttpStatus.ACCEPTED);

    }
}
