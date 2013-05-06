package com.bfds.scheduling.domain;

import com.bfds.saec.util.IMailingList;
import com.bfds.scheduling.util.BusinessDayUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * The configurable attributes of a mail sent from a business function. Examples of  business functions are all batch jobs.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit="entityManagerFactory", finders = "findMailingListsByCode")
 public class MailingList implements IMailingList {

    /**
     * Unique identifier of the business function.
     */
    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "mail_from")
    private String from;

    @Column(name="mail_to", nullable = false)
    private String to;

    private String cc;

    private String bcc;

    private String defaultSubject;

    public static MailingList findByCode(final String code ) {
        TypedQuery<MailingList> query = MailingList.findMailingListsByCode(code);
        try {
            return query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
