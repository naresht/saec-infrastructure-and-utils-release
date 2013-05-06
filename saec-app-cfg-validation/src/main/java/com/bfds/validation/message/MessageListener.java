package com.bfds.validation.message;

public interface MessageListener {

    /**
     * Must be called when a new {@link Message} is added to the {@link ObservableMessageContext}
     * @param message
     */
    void messageCreated(Message message);
}
