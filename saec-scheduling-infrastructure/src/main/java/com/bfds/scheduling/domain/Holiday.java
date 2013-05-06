package com.bfds.scheduling.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.bfds.scheduling.util.BusinessDayUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit="entityManagerFactory")
 public class Holiday {
	
	public Holiday() {}
	
	public Holiday(final Date holidayDate) {
		this.holidayDate = holidayDate;
		this.stockMarketOpen = Boolean.FALSE;
		this.banksOpen = Boolean.FALSE;
	}
	
	public Holiday(final Date holidayDate, final Boolean stockMarketOpen, final Boolean banksOpen) {
		this.holidayDate = holidayDate;
		this.stockMarketOpen = stockMarketOpen;
		this.banksOpen = banksOpen;
	}
	
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date holidayDate;

    private String type;

    @NotNull
    private Boolean stockMarketOpen;
    
    @NotNull
    private Boolean banksOpen;
    
    public static boolean isHoliday(final Date dateToCheck) {
    	if(!BusinessDayUtil.isBusinessDay(dateToCheck)) {
    		return true;
    	}
    	Calendar calendarDateToCheck = Calendar.getInstance();
    	calendarDateToCheck.setTime(DateUtils.truncate(dateToCheck, Calendar.DATE));
		
        return exists(dateToCheck);
    }

	private static boolean exists(final Date dateToCheck) {		
		Calendar calendarDateToCheck = Calendar.getInstance();
    	calendarDateToCheck.setTime(DateUtils.truncate(dateToCheck, Calendar.DATE));        
		Long count = entityManager().createQuery(
										   "SELECT count(o) FROM Holiday o"+
        		                           " where month(o.holidayDate) = :month "+
        		                           " and day(o.holidayDate) = :day " + 
        		                           " and year(o.holidayDate) = :year ", Long.class)
        		                   .setParameter("month", calendarDateToCheck.get(Calendar.MONTH) + 1)
        		                   .setParameter("day", calendarDateToCheck.get(Calendar.DAY_OF_MONTH))
        		                   .setParameter("year", calendarDateToCheck.get(Calendar.YEAR))
                                   .getSingleResult();
        return count > 0;
	}
}
