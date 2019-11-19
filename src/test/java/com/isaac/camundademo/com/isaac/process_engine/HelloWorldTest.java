package com.isaac.camundademo.com.isaac.process_engine;

import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class HelloWorldTest extends ProcessEngineTestCase {
    @Deployment(resources = {"diagram/HelloWorld.bpmn"})
    public void testDeployment() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Test");
//        assertThat(pi).isWaitingAt("Task2");
    }
}
