package com.isaac.camundademo.com.isaac.process_engine.assertion;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.List;

public class ServiceTaskTest extends ProcessEngineTestCase {

    @Deployment(resources = "diagram/serviceTaskTest.bpmn")
    public void testServiceTaskJavaDelegate() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("serviceTaskTest");
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list();

        Object variable = variables.stream().filter(var -> "delegateFlag".equals(var.getName())).findFirst().map(HistoricVariableInstance::getValue).orElseThrow(() -> new RuntimeException("Not Found variable"));
        assertEquals("1 Hello World", variable);
    }
}
