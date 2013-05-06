package com.bfds.validation.message;



public class MessageUtil {

    public void deleteAllMessages() {
        for(Message m : Message.findAllMessages()) {
            m.remove();
        }
    }
}
