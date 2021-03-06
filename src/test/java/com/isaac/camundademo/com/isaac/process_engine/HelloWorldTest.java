package com.isaac.camundademo.com.isaac.process_engine;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class HelloWorldTest extends ProcessEngineTestCase {
    @Deployment(resources = {"diagram/HelloWorld.bpmn"})
    public void testDeployment() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Test");
        assertThat(pi).isWaitingAt("userTask");
        long count = runtimeService.createVariableInstanceQuery().processInstanceIdIn(pi.getId()).list()
                .parallelStream()
                .filter(var -> "flag".equals(var.getName()))
                .count();
        assertEquals(1, count);
    }
}
