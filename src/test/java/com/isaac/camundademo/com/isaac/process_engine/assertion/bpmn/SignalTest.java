package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.List;


public class SignalTest extends ProcessEngineTestCase {

    @Deployment(resources = "diagram/signal/signal.bpmn")
    public void testSignalSendAndReceive() {
        runtimeService.signalEventReceived("signalDemo");
        List<Task> tasks = taskService.createTaskQuery().active().list();
        assertEquals("demo", tasks.get(0).getName());

    }
}
