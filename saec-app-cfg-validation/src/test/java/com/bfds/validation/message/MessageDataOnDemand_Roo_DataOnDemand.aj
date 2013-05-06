// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bfds.validation.message;

import com.bfds.validation.message.Message;
import com.bfds.validation.message.MessageDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect MessageDataOnDemand_Roo_DataOnDemand {
    
    declare @type: MessageDataOnDemand: @Component;
    
    private Random MessageDataOnDemand.rnd = new SecureRandom();
    
    private List<Message> MessageDataOnDemand.data;
    
    public Message MessageDataOnDemand.getSpecificMessage(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Message obj = data.get(index);
        Long id = obj.getId();
        return Message.findMessage(id);
    }
    
    public Message MessageDataOnDemand.getRandomMessage() {
        init();
        Message obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Message.findMessage(id);
    }
    
    public boolean MessageDataOnDemand.modifyMessage(Message obj) {
        return false;
    }
    
    public void MessageDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Message.findMessageEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Message' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            Message obj = getNewTransientMessage(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
