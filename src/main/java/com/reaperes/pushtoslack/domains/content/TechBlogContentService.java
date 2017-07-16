package com.reaperes.pushtoslack.domains.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechBlogContentService {

  @Autowired
  private TechBlogContentRepository techBlogContentRepository;

  public TechBlogContent save(TechBlogContent data) {
    return techBlogContentRepository.save(data);
  }

  public TechBlogContent getLatestContent(TechBlogContent.TechBlog techBlog) {
    return techBlogContentRepository.findOneByTechBlog(techBlog);
  }
}
