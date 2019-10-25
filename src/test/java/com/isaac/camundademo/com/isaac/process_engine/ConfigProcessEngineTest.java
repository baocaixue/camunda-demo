package com.isaac.camundademo.com.isaac.process_engine;

import org.camunda.bpm.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigProcessEngineTest {

    @Test
    public void testBuildProcessEngineByApi(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
                .setDatabaseSchemaUpdate("create-drop")
                .setJobExecutorActivate(true)
                .buildProcessEngine();
        assertNotNull(processEngine);
    }

    @Test
    public void testBuildProcessEngineByXml() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        assertNotNull(processEngine);
    }

    @Test
    public void testApi() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        assertNotNull(repositoryService);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        assertNotNull(runtimeService);
        TaskService taskService = processEngine.getTaskService();
        assertNotNull(taskService);
        IdentityService identityService = processEngine.getIdentityService();
        assertNotNull(identityService);
        FormService formService = processEngine.getFormService();
        assertNotNull(formService);
        HistoryService historyService = processEngine.getHistoryService();
        assertNotNull(historyService);
        ManagementService managementService = processEngine.getManagementService();
        assertNotNull(managementService);
        FilterService filterService = processEngine.getFilterService();
        assertNotNull(filterService);
        ExternalTaskService externalTaskService = processEngine.getExternalTaskService();
        assertNotNull(externalTaskService);
        CaseService caseService = processEngine.getCaseService();
        assertNotNull(caseService);
        DecisionService decisionService = processEngine.getDecisionService();
        assertNotNull(decisionService);
    }
}
