package com.reaperes.pushtoslack.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Component
public class Scheduler {

  @Autowired
  private ApplicationContext context;

//  @Scheduled(cron = "*/5 * * * * *")  // for dubug
  @Scheduled(cron = "0 0 10-12 * * *")
  public void run() throws InvocationTargetException, IllegalAccessException {
    boolean noTaskMethodToRun = true;

    Map<String, Object> beans = context.getBeansWithAnnotation(ScheduledTask.class);
    for (Object bean : beans.values()) {
      for (Method method : AopUtils.getTargetClass(bean).getDeclaredMethods()) {
        if (method.isAnnotationPresent(ScheduledTaskMethod.class)) {
          log.info("run task {}", bean);
          noTaskMethodToRun = false;
          method.invoke(bean);
        }
      }
    }

    if (noTaskMethodToRun) {
      log.warn("There is no task method to run.");
    }
  }
}
