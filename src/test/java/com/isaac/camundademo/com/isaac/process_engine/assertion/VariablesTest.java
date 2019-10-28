package com.isaac.camundademo.com.isaac.process_engine.assertion;

import com.isaac.camundademo.domain.UserInfo;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.commons.utils.IoUtil;

import java.io.InputStream;

@Deployment(resources = "diagram/HelloWorld.bpmn")
public class VariablesTest extends ProcessEngineTestCase {

    public void testPrimitiveValues() {
        //primitive type
        StringValue typedStringValue = Variables.stringValue("a string value");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test");
        String executionId = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).singleResult().getId();
        runtimeService.setVariable(executionId, "stringVariable", typedStringValue);

        StringValue retrievedTypedStringValue = runtimeService.getVariableTyped(executionId, "stringVariable");
        assertNotNull(retrievedTypedStringValue);
        assertEquals("a string value", retrievedTypedStringValue.getValue());
        assertNotNull("java.lang.String", retrievedTypedStringValue.getType().getName());
    }

    public void testFileValues() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test");
        String executionId = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).singleResult().getId();
        //file values
        FileValue typedFileValue = Variables.fileValue("test.txt")
                .file(IoUtil.getClasspathFile("test.txt"))
                .mimeType("text/plain")
                .encoding("UTF-8")
                .create();
        runtimeService.setVariable(executionId, "fileVariable", typedFileValue);
        FileValue retrievedTypedFileValue = runtimeService.getVariableTyped(executionId, "fileVariable");
        InputStream value = retrievedTypedFileValue.getValue();
        assertNotNull(value);
        assertEquals("1",IoUtil.inputStreamAsString(value));
        String filename = retrievedTypedFileValue.getFilename();
        assertEquals("test.txt", filename);

        //change file
        InputStream newContent = IoUtil.fileAsStream(IoUtil.getClasspathFile("test1.txt"));
        FileValue newFileValue = Variables.fileValue(retrievedTypedFileValue.getFilename()).file(newContent).encoding(retrievedTypedFileValue.getEncoding())
                .mimeType(retrievedTypedFileValue.getMimeType()).create();
        runtimeService.setVariable(executionId, "fileVariable", newFileValue);
        retrievedTypedFileValue = runtimeService.getVariableTyped(executionId, "fileVariable");
        assertEquals("test.txt", retrievedTypedFileValue.getFilename());
        assertEquals("2",IoUtil.inputStreamAsString(retrievedTypedFileValue.getValue()));
    }

    // an invalid variable value will only be detected when runtimeService#getVariableTyped is called.
    public void testObjectValues() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test");
        String executionId = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).singleResult().getId();
        UserInfo userInfo = new UserInfo("1", "111", "tom", "tom");
        ObjectValue user = Variables.objectValue(userInfo).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
        runtimeService.setVariableLocal(executionId, "user", user);

        TypedValue reUser = runtimeService.getVariableTyped(executionId, "user");//deserialize
        assertTrue(reUser.getValue() instanceof UserInfo);

    }
}
