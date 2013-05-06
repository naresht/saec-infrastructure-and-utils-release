package com.bfds.validation.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.binding.message.Severity;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

public class DefaultValidationMessageContext implements ObservableMessageContext {

	private String defaultSource;

	List<Message> messages = new ArrayList<Message>();

	List<MessageListener> messageListeners = new ArrayList<MessageListener>();

	/**
	 * @see {@link ValidationMessageContext#getAllMessages()}
	 */
	@Override
	public Message[] getAllMessages() {
		return (Message[]) messages.toArray(new Message[messages.size()]);
	}

	/**
	 * @see {@link ValidationMessageContext#info(String)}
	 */
	@Override
	public void info(String text) {
		add(new Message(defaultSource, text, Severity.INFO));
	}

	/**
	 * @see {@link ValidationMessageContext#error(String)}
	 */
	@Override
	public void error(String text) {
		add(new Message(defaultSource, text, Severity.ERROR));
	}

	/**
	 * @see {@link ValidationMessageContext#warn(String)}
	 */
	@Override
	public void warn(String text) {
		add(new Message(defaultSource, text, Severity.WARNING));
	}

	/**
	 * @see {@link ValidationMessageContext#fatal(String)}
	 */
	@Override
	public void fatal(String text) {
		add(new Message(defaultSource, text, Severity.FATAL));
	}

	/**
	 * @see {@link ValidationMessageContext#info(String, Object...)}
	 */
	@Override
	public void info(String format, Object... args) {
		this.info(String.format(format, args));
	}

	/**
	 * @see {@link ValidationMessageContext#error(String, Object...)}
	 */
	@Override
	public void error(String format, Object... args) {
		this.error(String.format(format, args));
	}

	/**
	 * @see {@link ValidationMessageContext#warn(String, Object...)}
	 */
	@Override
	public void warn(String format, Object... args) {
		this.warn(String.format(format, args));
	}

	/**
	 * @see {@link ValidationMessageContext#fatal(String, Object...)}
	 */
	@Override
	public void fatal(String format, Object... args) {
		this.fatal(String.format(format, args));
	}

	/**
	 * @see {@link ValidationMessageContext#clearMessages()}
	 */
	@Override
	public void clearMessages() {
		messages.clear();
	}

	/**
	 * @see {@link ValidationMessageContext#setDefaultSource(String)}
	 */
	@Override
	public void setDefaultSource(String source) {
		defaultSource = source;
	}

	/**
	 * Adds the {@link Message} to {@link #messages} and notifies all the
	 * {@link com.bfds.validation.execution.ValidationListener}s
	 * 
	 * @param message
	 *            the {@link Message} to be added.
	 */
	@VisibleForTesting
	protected void add(Message message) {
		Preconditions.checkNotNull(message, "message is null");
		messages.add(message);
		for (MessageListener listener : messageListeners) {
			listener.messageCreated(message);
		}
	}

	/**
	 * @see {@link ValidationMessageContext#getMessagesBySeverity(org.springframework.binding.message.Severity)}
	 */
	@Override
	public Message[] getMessagesBySeverity(Severity severity) {
		return MessageUtils.getMessagesBySeverity(this.messages, severity);
	}

	@Override
	public void addMessageListener(MessageListener listener) {
		Preconditions.checkNotNull(listener, "listener is null");
		this.messageListeners.add(listener);
	}

	public void setValidationListeners(List<MessageListener> messageListeners) {
		this.messageListeners = messageListeners;
	}

}
