package com.isaac.camundademo.com.isaac.process_engine.assertion.bpmn;

import org.camunda.bpm.engine.migration.MigrationPlan;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class MigrationTest extends ProcessEngineTestCase {

    @Deployment(resources = {"diagram/migration/exampleProcessV1.bpmn"})
    public void testProcessMigration() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("exampleProcess");
        assertThat(pi).isWaitingAt("task1", "task2");
        repositoryService.createDeployment().addClasspathResource("diagram/migration/exampleProcessV2.bpmn").deploy();
        //create migration plan
        String sourceDefinitionId = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("exampleProcess1").singleResult().getId();
        String targetDefinitionId = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("exampleProcess2").singleResult().getId();
        System.out.println(sourceDefinitionId);
        System.out.println(targetDefinitionId);
        MigrationPlan migrationPlan = runtimeService
                .createMigrationPlan(sourceDefinitionId, targetDefinitionId)
                .mapActivities("task1", "task1")
                .mapActivities("task2", "task2")
                .build();
        runtimeService.newMigration(migrationPlan).processInstanceIds(pi.getId()).execute();

        pi = runtimeService.createProcessInstanceQuery().active().singleResult();

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskName("Archive Application").singleResult();
        taskService.complete(task.getId());
        assertThat(pi).isWaitingAt("task3","task2");

    }
}
