package com.mentor4you.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name="author_id", nullable = false)
    private Integer authorId;

    @Column(name="author_email", nullable = false)
    private String authorEmail;

    @Column(name="category_id", nullable = false)
    private Integer categoryId;

    @Column(name="category_name", nullable = false)
    private String categoryName;
}
