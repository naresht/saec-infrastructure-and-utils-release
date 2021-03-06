// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bfds.scheduling.domain;

import com.bfds.scheduling.domain.Holiday;
import com.bfds.scheduling.domain.HolidayDataOnDemand;
import com.bfds.scheduling.domain.HolidayIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HolidayIntegrationTest_Roo_IntegrationTest {
    
    declare @type: HolidayIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: HolidayIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: HolidayIntegrationTest: @Transactional;
    
    @Autowired
    private HolidayDataOnDemand HolidayIntegrationTest.dod;
    
    @Test
    public void HolidayIntegrationTest.testCountHolidays() {
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", dod.getRandomHoliday());
        long count = Holiday.countHolidays();
        Assert.assertTrue("Counter for 'Holiday' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void HolidayIntegrationTest.testFindHoliday() {
        Holiday obj = dod.getRandomHoliday();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to provide an identifier", id);
        obj = Holiday.findHoliday(id);
        Assert.assertNotNull("Find method for 'Holiday' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Holiday' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void HolidayIntegrationTest.testFindAllHolidays() {
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", dod.getRandomHoliday());
        long count = Holiday.countHolidays();
        Assert.assertTrue("Too expensive to perform a find all test for 'Holiday', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Holiday> result = Holiday.findAllHolidays();
        Assert.assertNotNull("Find all method for 'Holiday' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Holiday' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void HolidayIntegrationTest.testFindHolidayEntries() {
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", dod.getRandomHoliday());
        long count = Holiday.countHolidays();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Holiday> result = Holiday.findHolidayEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Holiday' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Holiday' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void HolidayIntegrationTest.testFlush() {
        Holiday obj = dod.getRandomHoliday();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to provide an identifier", id);
        obj = Holiday.findHoliday(id);
        Assert.assertNotNull("Find method for 'Holiday' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyHoliday(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Holiday' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void HolidayIntegrationTest.testMergeUpdate() {
        Holiday obj = dod.getRandomHoliday();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to provide an identifier", id);
        obj = Holiday.findHoliday(id);
        boolean modified =  dod.modifyHoliday(obj);
        Integer currentVersion = obj.getVersion();
        Holiday merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Holiday' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void HolidayIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", dod.getRandomHoliday());
        Holiday obj = dod.getNewTransientHoliday(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Holiday' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Holiday' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Holiday' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void HolidayIntegrationTest.testRemove() {
        Holiday obj = dod.getRandomHoliday();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Holiday' failed to provide an identifier", id);
        obj = Holiday.findHoliday(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Holiday' with identifier '" + id + "'", Holiday.findHoliday(id));
    }
    
}
