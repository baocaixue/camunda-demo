package com.isaac.camundademo.serive;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.Expression;

@Slf4j
@Data
public class ExampleExecutionListener implements ExecutionListener {
    private Expression text;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
      log.info("listener notify");
      delegateExecution.setVariable("listener", "1");
    }
}
