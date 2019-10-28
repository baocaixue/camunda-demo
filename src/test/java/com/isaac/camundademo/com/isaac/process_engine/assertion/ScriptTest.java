package com.isaac.camundademo.com.isaac.process_engine.assertion;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import java.util.HashMap;
import java.util.Optional;

public class ScriptTest extends ProcessEngineTestCase {
    @Deployment(resources = "diagram/script/script_start.bpmn")
    public void testScriptTask1() {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("inputArray", new Integer[]{1,2,3,4,5});
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("scriptStart", variables);

        Optional<HistoricVariableInstance> historicVariableInstance1 = historyService
                .createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list().stream()
                .filter(historicVariableInstance -> "Sum".equals(historicVariableInstance.getName())).findFirst();

        assertEquals("15", historicVariableInstance1.map(HistoricVariableInstance::getValue).orElse(""));
    }
}
