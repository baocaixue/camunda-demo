package com.isaac.camundademo.com.isaac.process_engine.junit.dmn;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.commons.utils.IoUtil;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DmnTest {
    @Rule
    public DmnEngineRule dmnEngineRule = new DmnEngineRule();

    @Test
    public void testDemo() {
        InputStream demo = IoUtil.fileAsStream("dmnfile/DmnDemo.dmn");
        DmnEngine dmnEngine = dmnEngineRule.getDmnEngine();
        VariableMap variables = Variables.createVariables();
        variables.putValue("input","4");
        DmnDecision decision = dmnEngine.parseDecision("decisionDemo", demo);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        assertNotNull(result);
        assertEquals("夏", result.getFirstResult().getEntryMap().get("output"));

    }

    @Test
    public void testDemo1() {
        InputStream demo = IoUtil.fileAsStream("dmnfile/DmnDemo.dmn");
        DmnEngine dmnEngine = dmnEngineRule.getDmnEngine();
        VariableMap variables = Variables.createVariables();
        variables.putValue("input","33");
        DmnDecision decision = dmnEngine.parseDecision("decisionDemo", demo);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        List<Map<String, Object>> resultList = result.getResultList();
        assertNotNull(resultList);
        resultList.forEach(item -> assertNull(item.get("output")));
//        resultList.forEach(item -> assertEquals("夏", item.get("output")));
    }
}
