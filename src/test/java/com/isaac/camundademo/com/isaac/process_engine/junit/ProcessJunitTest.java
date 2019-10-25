package com.isaac.camundademo.com.isaac.process_engine.junit;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.RequiredHistoryLevel;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProcessJunitTest {
    @Rule
    public ProcessEngineRule processEngineRule =  new ProcessEngineRule();

    @Test
    @Deployment(resources = "Test.bpmn")//如果没有指定，默认 测试类名.测试方法名.bpmn20.xml(如果是类级别，是 测试类名.bpmn20.xml)
    public void ruleUsageExample() {
        RuntimeService runtimeService = processEngineRule.getRuntimeService();
        runtimeService.startProcessInstanceByKey("Test");

        TaskService taskService = processEngineRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        assertEquals("userTask", task.getName());

        taskService.complete(task.getId());
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }

    /**
     * history level:
     *
     * NONE no history events are fired.
     * ACTIVITY
     * AUDIT
     * FULL
     * AUTO
     */
    @Test
    @Deployment(resources = "Test.bpmn")
    @RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_ACTIVITY)
    public void ruleUsageOfHistoryLevel() {
        RuntimeService runtimeService = processEngineRule.getRuntimeService();
        runtimeService.startProcessInstanceByKey("Test");

        HistoryService historyService = processEngineRule.getHistoryService();
        //require history level >= "activity"
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery().singleResult();
        assertNotNull(variable);
    }
}
