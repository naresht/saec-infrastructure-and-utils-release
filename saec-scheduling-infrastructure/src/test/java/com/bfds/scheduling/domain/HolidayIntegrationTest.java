package com.bfds.scheduling.domain;

import java.util.Date;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Holiday.class)
public class HolidayIntegrationTest {

    @Test
    public void checkDefaultHoliday() {
    	Holiday h = new Holiday(new Date(111, 10, 30, 12, 30));
    	h.persist();
    	h.clear();
    	
    	org.junit.Assert.assertTrue(Holiday.isHoliday(new Date(111, 10, 30)));
    	
    	org.junit.Assert.assertTrue(Holiday.isHoliday(new Date(111, 11, 31)));
    	
    	org.junit.Assert.assertTrue(Holiday.isHoliday(new Date(111, 11, 25)));
    	
    	org.junit.Assert.assertTrue(Holiday.isHoliday(new Date(111, 11, 25)));
    }
}
