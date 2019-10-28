package com.isaac.camundademo.com.isaac.process_engine.junit.spin;

import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpinTest {
    @Test
    public void baseTest() {
        String jsonStr = "{\"name\":\"jonny\",\"address\":{\"street\":\"12 High Street\",\"post code\":1234}}\n";
        JsonValue jsonValue = SpinValues.jsonValue(jsonStr).create();
        assertEquals("jonny", jsonValue.getValue().prop("name").stringValue());
    }
}
