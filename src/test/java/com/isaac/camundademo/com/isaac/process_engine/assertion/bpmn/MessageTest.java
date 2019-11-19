package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;


import java.util.HashMap;
import java.util.Map;

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

    @Deployment(resources = "diagram/message/multi_msg_signal.bpmn")
    public void testMultiMsgAndSignalStartEvent() {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("msgSignalTest").singleResult();

        runtimeService.createMessageCorrelation("msg1").correlate();
        assertThat(processDefinition).hasActiveInstances(1);

        runtimeService.createMessageCorrelation("msg2").correlate();
        assertThat(processDefinition).hasActiveInstances(2);

        runtimeService.signalEventReceived("signal1");

        assertThat(processDefinition).hasActiveInstances(3);

        runtimeService.signalEventReceived("signal2");

        assertThat(processDefinition).hasActiveInstances(4);
    }

    @Deployment(resources = "diagram/message/test.bpmn")
    public void testDemo(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("test","test");
        assertThat(pi).isWaitingAt("task1", "task2");

        Map<String, Object> variables = new HashMap<>();
        variables.put("test","test");
        runtimeService.createMessageCorrelation("msg1").processInstanceBusinessKey(pi.getBusinessKey()).setVariableLocal("aaa",variables).setVariables(variables).correlate();

        assertThat(pi).isWaitingAt("task2");

        runtimeService.createMessageCorrelation("msg2").processInstanceBusinessKey(pi.getBusinessKey()).setVariableLocal("aaa",variables).setVariables(variables).correlate();

        assertThat(pi).isEnded();
    }


    @Deployment(resources = "diagram/message/msg_self_pickup.bpmn")
    public void testMsgSelfPick() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("test");
        assertThat(pi).isWaitingAt("task","task1");
        taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).taskName("task1").singleResult().getId());
        assertThat(pi).isEnded();
    }

    @Deployment(resources = "diagram/message/signal_self_pickup.bpmn")
    public void testSignalSelfPick() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("test");
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("test");
        taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).taskName("task1").singleResult().getId());
        assertThat(pi).isEnded();
        assertThat(pi1).isWaitingAt("task1");
    }
}
