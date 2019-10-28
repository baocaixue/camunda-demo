package com.isaac.camundademo.com.isaac.process_engine.assertion;

import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

public class ProcessExecuteTest extends ProcessEngineTestCase {
    @Deployment(resources = "diagram/HelloWorld.bpmn")
    public void testDeployment() {
        runtimeService.startProcessInstanceByKey("Test");
        Task task = taskService.createTaskQuery().singleResult();
        assertEquals("userTask", task.getName());
        taskService.complete(task.getId());
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }
}
