package com.isaac.camundademo.com.isaac.process_engine;

import org.camunda.bpm.engine.impl.test.PluggableProcessEngineTestCase;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import java.util.*;

public class HelloWorldTest extends PluggableProcessEngineTestCase {
    @Deployment(resources = {"diagram/HelloWorld.bpmn"})
    public void testDeployment() {
        List<ProcessInstance> process = runtimeService.createProcessInstanceQuery().active().list();
        assertEquals(0, process.size());
        Date startTime = ClockUtil.getCurrentTime();

        Date firstTime = new Date(startTime.getTime() + (60 * 3 * 1000));
        ClockUtil.setCurrentTime(firstTime);
        executeAllJobs();
        process = runtimeService.createProcessInstanceQuery().active().list();
        assertEquals(1, process.size());

//        Date secondTime = new Date(firstTime.getTime() + (60 * 3 * 1000));
//        ClockUtil.setCurrentTime(secondTime);
//        executeAllJobs();
//        process = runtimeService.createProcessInstanceQuery().active().list();
//        assertEquals(2, process.size());

    }

    protected void executeAllJobs() {
        String nextJobId = getNextExecutableJobId();

        while (nextJobId != null) {
            try {
                managementService.executeJob(nextJobId);
            } catch (Throwable t) { /* ignore */
            }
            nextJobId = getNextExecutableJobId();
        }

    }

    protected String getNextExecutableJobId() {
        List<Job> jobs = managementService.createJobQuery().executable().listPage(0, 1);
        if (jobs.size() == 1) {
            return jobs.get(0).getId();
        } else {
            return null;
        }
    }
    public void testApi(){
    }
}
