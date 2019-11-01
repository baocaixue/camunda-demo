package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;


import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class MessageTest extends ProcessEngineTestCase {

    @Deployment(resources = "diagram/message/message_event1.bpmn")
    public void testMessage() {
//        runtimeService.startProcessInstanceByKey("messageEvent1");
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation("messageDemo").correlateWithResult();
        ProcessInstance pi = messageCorrelationResult.getProcessInstance();

        assertThat(pi).isWaitingAt("handleData");

        runtimeService.createMessageCorrelation("msgDemo").correlate();

        assertThat(pi).isWaitingAt("handleData","callManager");
    }
}
