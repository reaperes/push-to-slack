package com.reaperes.pushtoslack.scheduler.tasks;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @see <a href="http://d2.naver.com/api/v1/contents?categoryId=2&page=0&size=20">Get API spec</a>
 */
@Getter
@Setter
public class NaverHelloWorldDto {

  private List<Content> content;

  @Getter
  @Setter
  public static class Content {
    private String postTitle;
    private String postImage;
    private String postPublishedAt;
    private String url;
  }
}
