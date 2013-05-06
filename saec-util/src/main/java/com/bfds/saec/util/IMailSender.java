package com.bfds.saec.util;


import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;

public interface IMailSender extends JavaMailSender {
	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 * @return true if the mail sending did not encounter any errors
	 */
    boolean sendMail(String from, String to, final String subject, final String text);

    /**
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @param attachmentFilename
     * @param attachmentFile
     * @return  rue if the mail sending did not encounter any errors
     */
    boolean sendMail(String from, String to, String subject, String text, String attachmentFilename, InputStreamSource attachmentFile);
    /**
     * 
     * @param mailingList
     * @param subject
     * @param text
     * @return true if the mail sending did not encounter any errors
     */
    boolean send(IMailingList mailingList, final String subject, final String text);

    /**
     *
     * @param mailingList
     * @param subject
     * @param text
     * @param attachmentFilename
     * @param attachmentFile
     * @return  true if the mail sending did not encounter any errors
     */
    boolean send(IMailingList mailingList, final String subject, String text,
                 String attachmentFilename, InputStreamSource attachmentFile);
}
