package com.bfds.saec.util;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * This test actually sends mails and cannot be guarenteed to run in all environments.
 *
 * Hence run this test manually when you want to test mail features.
 *
 *
 */
public class MailSenderImplIntegrationTest {

    private JavaMailSenderImpl target;
    private MailSenderImpl mailSender;

    @Before
    public void before() {
        target = new JavaMailSenderImpl();
        target.setHost("mailrelay.dstcorp.net");
        mailSender = new MailSenderImpl(target);

    }

    @Test
    @Ignore
    public void test() {
        mailSender.sendMail("sgiridhar@dstworldwideservices.com", "sgiridhar@dstworldwideservices.com",
                            "test mail: With attachments", "doc attached",
                            "doc.txt", new ClassPathResource("testMailAttachment.txt"));
    }    
    
}
