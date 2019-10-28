package com.isaac.camundademo.com.isaac.process_engine.junit.variables;

import com.isaac.camundademo.domain.UserInfo;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.Test;

import static org.junit.Assert.*;

public class VariablesTest {
    @Test
    public void testSerialization() {
        ObjectValue customDataValue = Variables
                .objectValue(new UserInfo("1001", "1001", "test", "test"))
                .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
                .create();
        assertEquals("com.isaac.camundademo.domain.UserInfo", customDataValue.getObjectType().getName());
    }
}
