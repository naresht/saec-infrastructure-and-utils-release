package com.bfds.saec.batch.format.propertyeditors;

import org.junit.Test;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
public class CustomDateEditorTest {

    @Test
    public  void setAsTextYYMMDD() {
        CustomDateEditor de = new CustomDateEditor("YYMMDD");
        de.setAsText("120605");
        assertThat(de.getValue()).isEqualTo(new Date(112, 05, 05));

        de.setAsText("");
        assertThat(de.getValue()).isNull();

        de.setAsText(null);
        assertThat(de.getValue()).isNull();
    }

    @Test
    public  void getAsTextYYMMDD() {
        CustomDateEditor de = new CustomDateEditor("YYMMDD");
        de.setValue(new Date(112, 05, 05));
        assertThat(de.getAsText()).isEqualTo("120605");

        de.setValue(null);
        assertThat(de.getAsText()).isEqualTo(""); // TODO: WHy is it not null ?
    }

    @Test
    public  void setAsTextMMDDYYYY() {
        CustomDateEditor de = new CustomDateEditor("MMDDYYYY");
        de.setAsText("06052012");
        assertThat(de.getValue()).isEqualTo(new Date(112, 05, 05));
    }

    @Test
    public  void getAsTextMMDDYYYY() {
        CustomDateEditor de = new CustomDateEditor("MMDDYYYY");
        de.setValue(new Date(112, 05, 05));
        assertThat(de.getAsText()).isEqualTo("06052012");

        de.setValue(null);
        assertThat(de.getAsText()).isEqualTo("");
    }

    @Test
    public  void setAsTextYYYYMMDD() {
        CustomDateEditor de = new CustomDateEditor("YYYYMMDD");
        de.setAsText("20120605");
        assertThat(de.getValue()).isEqualTo(new Date(112, 05, 05));
    }

    @Test
    public  void getAsTextYYYYMMDD() {
        CustomDateEditor de = new CustomDateEditor("YYYYMMDD");
        de.setValue(new Date(112, 05, 05));
        assertThat(de.getAsText()).isEqualTo("20120605");

        de.setValue(null);
        assertThat(de.getAsText()).isEqualTo("");
    }

    @Test
    public  void setAsTextMMDDYY() {
        CustomDateEditor de = new CustomDateEditor("MMDDYY");
        de.setAsText("060512");
        assertThat(de.getValue()).isEqualTo(new Date(112, 05, 05));
    }

    @Test
    public  void getAsTextMMDDYY() {
        CustomDateEditor de = new CustomDateEditor("MMDDYY");
        de.setValue(new Date(112, 05, 05));
        assertThat(de.getAsText()).isEqualTo("060512");

        de.setValue(null);
        assertThat(de.getAsText()).isEqualTo("");
    }
}
