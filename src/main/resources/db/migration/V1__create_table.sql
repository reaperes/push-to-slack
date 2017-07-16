CREATE TABLE tech_blog_content (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  tech_blog VARCHAR(100) UNIQUE NOT NULL,
  latest_content_key VARCHAR(255) NOT NULL,
  content_url VARCHAR(255) NOT NULL
)