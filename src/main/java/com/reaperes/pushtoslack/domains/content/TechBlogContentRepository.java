package com.reaperes.pushtoslack.domains.content;

import org.springframework.data.repository.CrudRepository;

public interface TechBlogContentRepository extends CrudRepository<TechBlogContent, Integer> {
  TechBlogContent findOneByTechBlog(TechBlogContent.TechBlog techBlog);
}