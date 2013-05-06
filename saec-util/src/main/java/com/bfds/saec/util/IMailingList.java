package com.bfds.saec.util;


public interface IMailingList {

    String getFrom();
    String getTo();
    String getCc();
    String getBcc();
    String getDefaultSubject();
}
