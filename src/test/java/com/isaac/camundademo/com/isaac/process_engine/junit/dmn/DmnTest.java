package com.isaac.camundademo.com.isaac.process_engine.junit.dmn;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.commons.utils.IoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DmnTest {
    @Rule
    public DmnEngineRule dmnEngineRule = new DmnEngineRule();

    private DmnEngine dmnEngine;
    private VariableMap variables = Variables.createVariables();

    @Before
    public void setup() {
        dmnEngine = dmnEngineRule.getDmnEngine();
    }

    @After
    public void cleanup() {
        variables.clear();
    }

    @Test
    public void testDemo() {
        InputStream is = IoUtil.fileAsStream("dmnfile/DmnDemo.dmn");
        variables.putValue("input","4");

        DmnDecision decision = this.getDmnDecisionObj("decisionDemo", is);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        assertNotNull(result);

        assertEquals("夏", result.getFirstResult().getEntryMap().get("output"));

    }

    @Test
    public void testDemo1() {
        InputStream is = IoUtil.fileAsStream("dmnfile/DmnDemo.dmn");
        variables.putValue("input","33");

        DmnDecision decision = this.getDmnDecisionObj("decisionDemo", is);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        List<Map<String, Object>> resultList = result.getResultList();

        assertNotNull(resultList);
        resultList.forEach(item -> assertNull(item.get("output")));
//        resultList.forEach(item -> assertEquals("夏", item.get("output")));
    }

    @Test
    public void testPolicy() {
        InputStream is = IoUtil.fileAsStream("dmnfile/test.dmn");
        variables.putValue("input1",1);
        variables.putValue("input2","1");

        DmnDecision dmnDecision = this.getDmnDecisionObj("test", is);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(dmnDecision, variables);

        Object value = result.getSingleEntry();
        assertEquals(2, value);
    }

    private DmnDecision getDmnDecisionObj(String decisionId, InputStream inputStream) {
        return dmnEngine.parseDecision(decisionId, inputStream);
    }
}
