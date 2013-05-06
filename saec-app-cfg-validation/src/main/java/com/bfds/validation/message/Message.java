package com.bfds.validation.message;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.Severity;
import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * An object of communication that provides text information. For example, a
 * validation message may inform a web application user a business rule was
 * violated. A message can be associated with a particular source element or
 * component, has text providing the basis for communication, and has severity
 * indicating the priority or intensity of the message for its receiver.
 * 
 */
@RooJpaActiveRecord(persistenceUnit = "entityManagerFactory", table = "app_config_error")
@RooJavaBean(settersByDefault = false)
@RooToString
@RooConfigurable
@Qualifier("validationMessage")
public class Message implements Serializable {

	/**
	 * A reference to the source element this message is associated with. This
	 * could be a field on a form in UI, or null (or empty "" in the case of
	 * global bean validation) if the message is not associated with a any
	 * particular element.
	 */
	@Column(nullable = false)
	private String source;

	/**
	 * The message text. The text is the message's communication payload.
	 */
	@Column(nullable = false, length = 2000)
	private String text;

	/**
	 * The severity of this message. The severity indicates the intensity or
	 * priority of the communication.
	 * 
	 * @return the message severity
	 */
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Severity severity;

	@Column(nullable = false)
	private Date createdOn;

	/**
	 * Creates a new message.
	 * 
	 * @param source
	 *            the source of the message
	 * @param text
	 *            the message text
	 * @param severity
	 *            the message severity
	 */
	public Message(String source, String text, Severity severity) {
		this.source = source;
		this.text = text;
		this.severity = severity;
		createdOn = new Date();
	}

}
