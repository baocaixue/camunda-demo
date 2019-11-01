package com.isaac.camundademo.com.isaac.process_engine.assertion.dmn;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.HashMap;
import java.util.Map;

public class DecisionTableTest extends ProcessEngineTestCase {

    @Deployment(resources = {"diagram/dmn/dmnProcessTest.bpmn", "dmnfile/DmnDemo.dmn"})
    public void testDemo() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", "0");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("dmnProcessTest", variables);

        HistoricVariableInstance result = historyService.createHistoricVariableInstanceQuery().processInstanceId(pi.getId()).list().stream()
                .filter(var -> "result".equals(var.getName())).findFirst().orElseThrow(() -> new RuntimeException("not found"));
//        assertNull(result.getValue());
//        assertEquals("夏", ((Map<String, String>) result.getValue()).get("output"));
    }
}