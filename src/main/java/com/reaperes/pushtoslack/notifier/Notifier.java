package com.reaperes.pushtoslack.notifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class Notifier {

  private static final String URL = "https://hooks.slack.com/services/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";

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public void notify(String message) throws JsonProcessingException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> payload = new HashMap<>();
    payload.put("text", message);

    HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payload), headers);

    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      log.error("Fail to notify to slack - {}", response);
      return;
    }

    log.debug("Send message to slack {}", message);
  }
}
