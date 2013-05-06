package com.bfds.validation.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.binding.message.Severity;

public class MessageUtils {
	/**
	 * @see {@link ValidationMessageContext#getMessagesBySeverity(org.springframework.binding.message.Severity)}
	 */

	public static Message[] getMessagesBySeverity(final List<Message> messages,
			final Severity... severity) {
		final List<Message> ret = new ArrayList<Message>();
		for (final Message message : messages) {
			if (severity == null) {
				return new Message[]{};
			}
			final Severity messageSeverity = message.getSeverity();
			for (final Severity givenSeverity : severity) {
				if (givenSeverity == messageSeverity) {
					ret.add(message);
					break;
				}
			}
		}
		return (Message[]) ret.toArray(new Message[ret.size()]);
	}

	public static boolean hasAnyMessagesBySeverity(
			final List<Message> messages, final Severity... severity) {
		for (final Message message : messages) {
			if (severity == null) {
				return false;
			}
			final Severity messageSeverity = message.getSeverity();
			for (final Severity givenSeverity : severity) {
				if (givenSeverity == messageSeverity) {
					return true;
				}
			}
		}
		return false;
	}
}
