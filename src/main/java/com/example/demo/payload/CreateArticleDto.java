package com.example.demo.payload;

import com.example.demo.entity.Visibility;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class CreateArticleDto {
    private String article_id;
    private String content ;
    private Visibility visibility;
    private String user_id;
}
