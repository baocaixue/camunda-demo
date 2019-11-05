package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class GateWayTest extends ProcessEngineTestCase {

    @Deployment(resources = "diagram/gateway/event_base_gateway.bpmn")
    public void testEventBaseGateway() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("eventBaseGateway");
        assertThat(pi).isWaitingAt("ExclusiveGateway_1okzydv");

        runtimeService.correlateMessage("msg");
        runtimeService.signalEventReceived("signal");

        assertThat(pi).isWaitingAt("msgTask");

        String taskId = taskService.createTaskQuery().processInstanceId(pi.getId()).active().singleResult().getId();
        taskService.complete(taskId);

        assertThat(pi).isEnded();
    }
}
