package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;


public class ServiceTaskTest extends ProcessEngineTestCase {

    @Deployment(resources = "diagram/service/serviceTaskTest.bpmn")
    public void testServiceTaskJavaDelegate() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("initiator", "isaac");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("serviceTaskTest", vars);
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list();

        Object variable = variables.stream().filter(var -> "initiator".equals(var.getName())).findFirst().map(HistoricVariableInstance::getValue).orElseThrow(() -> new RuntimeException("Not Found variable"));
        assertEquals("isaac", variable);

        variable = variables.stream().filter(var -> "delegateFlag".equals(var.getName())).findFirst().map(HistoricVariableInstance::getValue).orElseThrow(() -> new RuntimeException("Not Found variable"));
        assertEquals("1 Hello World", variable);

        variable = variables.stream().filter(var -> "testisaac".equals(var.getName())).findFirst().map(HistoricVariableInstance::getValue).orElseThrow(() -> new RuntimeException("Not Found variable"));
        assertEquals("foo", variable);
    }

    @Deployment(resources = "diagram/service/serviceTest1.bpmn")
    public void testUndefinedServiceTask() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("undefined");

        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list();

        HistoricVariableInstance result = variables.stream().filter(var -> "listener".equals(var.getName())).findFirst().orElseThrow(() -> new RuntimeException("not found variable"));
        assertEquals("1", result.getValue());
    }
}
