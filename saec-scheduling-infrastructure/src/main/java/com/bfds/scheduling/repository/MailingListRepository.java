package com.bfds.scheduling.repository;


import com.bfds.scheduling.domain.MailingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingListRepository extends JpaRepository<MailingList, Long> {

    MailingList findByCode(String code);
}
