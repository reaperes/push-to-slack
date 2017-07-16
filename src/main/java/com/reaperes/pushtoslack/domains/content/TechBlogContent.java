package com.reaperes.pushtoslack.domains.content;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Only 1 content store per tech blog.
 */
@Data
@Entity
public class TechBlogContent implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  @Enumerated(EnumType.STRING)
  private TechBlog techBlog;

  @Column
  private String latestContentKey;

  @Column
  private String contentUrl;

  public enum TechBlog {
    NAVER_HELLO_WORLD
  }
}