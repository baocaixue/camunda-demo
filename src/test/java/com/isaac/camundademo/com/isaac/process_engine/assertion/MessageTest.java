package com.isaac.camundademo.com.isaac.process_engine.assertion;

import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

@Deployment(resources = "diagram/message/message_event.bpmn")
public class MessageTest extends ProcessEngineTestCase {
    public void testDemo() {
        //correlate the message
//        MessageCorrelationResult result = runtimeService.createMessageCorrelation("newInvoice")
//                .processInstanceBusinessKey("AB-123")
//                .correlateWithResult();

        //explicitly query for the subscription and trigger it
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("messageEvent", "BusinessKey");
        EventSubscription message = runtimeService.createEventSubscriptionQuery().processInstanceId(pi.getId()).eventType("message").singleResult();
        assertEquals("paymentMessage-BusinessKey", message.getEventName());
        runtimeService.messageEventReceived(message.getEventName(), message.getExecutionId());
    }
}
