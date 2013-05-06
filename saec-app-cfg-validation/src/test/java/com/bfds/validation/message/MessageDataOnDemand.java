package com.bfds.validation.message;

import org.springframework.binding.message.Severity;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Message.class)
public class MessageDataOnDemand {

    public Message getNewTransientMessage(int index) {
        Message obj = new Message("" + index, "" + index, Severity.ERROR);
        return obj;
    }
}
