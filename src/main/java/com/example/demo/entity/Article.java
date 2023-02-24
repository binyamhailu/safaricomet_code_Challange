package com.example.demo.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String article_id;
    private String content ;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    private String user_id;

}
