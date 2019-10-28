package com.isaac.camundademo.serive;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
@Data
public class JavaDelegateDemo implements JavaDelegate {

    private Expression text;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Java Delegate execute for service task.");
        String value = (String) text.getValue(execution);
        execution.setVariable("delegateFlag", "1 "+value);
    }
}
