package com.bfds.saec.util;


import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

public class MailSenderImpl implements IMailSender {

    Logger logger = LoggerFactory.getLogger(MailSenderImpl.class);

    private JavaMailSender target;

    public MailSenderImpl(JavaMailSender javaMailSender) {
        Preconditions.checkNotNull(javaMailSender, "javaMailSender is null.");
       this.target = javaMailSender;
    }

    @Override
    public boolean sendMail(String from, String to, String subject, String text) {
        return sendMail(from, to, subject, text, null, null);
    }

    @Override
    public boolean sendMail(String from, String to, String subject, String text, String attachmentFilename, InputStreamSource attachmentFile) {
        try {
            MimeMessage message = this.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            if(attachmentFile != null) {
                helper = new MimeMessageHelper(message, true);
                attachFile(helper, attachmentFilename, attachmentFile);
            } else {
                helper = new MimeMessageHelper(message, false);
            }
            helper.setTo(to);
            helper.setFrom(from);
            if (subject != null) {
                helper.setSubject(subject);
            }
            helper.setText(text, true);
            this.send(message);
            return true;

        } catch (MessagingException e) {
            // We do not want to throw an exception on mail processing errors.
            logger.error("error sending email. subject["+subject+"], to : " + to, e);
            return false;
        }
    }

    private void attachFile(MimeMessageHelper helper, String attachmentFilename, InputStreamSource attachmentFile) throws MessagingException {
        attachmentFilename = StringUtils.hasText(attachmentFilename) ? attachmentFilename : "attachment.dat";
        helper.addAttachment(attachmentFilename, attachmentFile);
    }

    @Override
    public boolean send(IMailingList mailingList, String subject, String text) {
       return send(mailingList, subject, text, null, null);
    }

    @Override
    public boolean send(IMailingList mailingList, String subject, String text, String attachmentFilename, InputStreamSource attachmentFile) {
        if(mailingList == null) {
            // We do not want to throw an exception on mail processing errors.
            logger.error("mailingList is null. Mail subject : " + subject);
            return false;
        }
        if(!StringUtils.hasText(mailingList.getTo())) {
            // We do not want to throw an exception on mail processing errors.
            logger.error("mailingList#to is null/empty. MailingList#code :"+mailingList+".Mail subject : " + subject);
            return false;
        }
        subject = StringUtils.hasText(subject) ? subject : mailingList.getDefaultSubject();
        return this.sendMail(mailingList.getFrom(), mailingList.getTo(), subject, text, attachmentFilename, attachmentFile);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return target.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return target.createMimeMessage(contentStream);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        target.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage[] mimeMessages) throws MailException {
        target.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        target.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
        target.send(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        target.send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
        target.send(simpleMessages);
    }
}
