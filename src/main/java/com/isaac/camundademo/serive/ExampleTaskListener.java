package com.isaac.camundademo.serive;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

@Slf4j
public class ExampleTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
      log.info("called task listener notify.");
    }
}
