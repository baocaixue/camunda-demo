package com.isaac.camundademo.serive;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

@Slf4j
public class ExampleExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
      log.info("listener notify");
      delegateExecution.setVariable("listener", "1");
    }
}
