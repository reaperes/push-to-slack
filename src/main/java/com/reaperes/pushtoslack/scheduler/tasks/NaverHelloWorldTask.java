package com.reaperes.pushtoslack.scheduler.tasks;

import com.google.common.collect.Iterables;
import com.reaperes.pushtoslack.domains.content.TechBlogContent;
import com.reaperes.pushtoslack.domains.content.TechBlogContentService;
import com.reaperes.pushtoslack.notifier.Notifier;
import com.reaperes.pushtoslack.scheduler.ScheduledTask;
import com.reaperes.pushtoslack.scheduler.ScheduledTaskMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@ScheduledTask
public class NaverHelloWorldTask {

  private static final String HOST = "http://d2.naver.com";

  private static final String API_URL = "/api/v1/contents?categoryId=2&page=0&size=20";

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private TechBlogContentService techBlogContentService;

  @Autowired
  private Notifier notifier;

  @ScheduledTaskMethod
  public void task() {
    ResponseEntity<NaverHelloWorldDto> response = restTemplate.exchange(HOST + API_URL, HttpMethod.GET, null, NaverHelloWorldDto.class);
    NaverHelloWorldDto dto = response.getBody();
    NaverHelloWorldDto.Content remoteLatestContent = dto.getContent().get(0);
    String remoteLatestContentKey = Iterables.getLast(Arrays.asList(remoteLatestContent.getUrl().split("/")));

    TechBlogContent content = techBlogContentService.getLatestContent(TechBlogContent.TechBlog.NAVER_HELLO_WORLD);
    if (Objects.nonNull(content) && ObjectUtils.nullSafeEquals(content.getLatestContentKey(), remoteLatestContentKey)) {
      return;
    }

    TechBlogContent techBlogContent = new TechBlogContent();
    techBlogContent.setTechBlog(TechBlogContent.TechBlog.NAVER_HELLO_WORLD);
    techBlogContent.setLatestContentKey(Iterables.getLast(Arrays.asList(remoteLatestContent.getUrl().split("/"))));
    techBlogContent.setContentUrl(HOST + remoteLatestContent.getUrl());
    techBlogContentService.save(techBlogContent);

    try {
      notifier.notify(techBlogContent.getContentUrl());
    } catch (Exception e) {
      log.error("Can not notify to slack - {}", e);
    }
  }
}
