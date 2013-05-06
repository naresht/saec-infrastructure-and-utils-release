package com.bfds.saec.batch.format.propertyeditors;


import com.bfds.saec.batch.annotations.Padding;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
public class PaddedStringEditorTest {

    @Test
    public  void setAsTextWithLpad() {
        PaddedStringEditor pe = new PaddedStringEditor(Padding.LPAD, '0', 12);
        pe.setAsText("000123405670");
        assertThat(pe.getValue()).isEqualTo("123405670");

        pe.setAsText("123405670");
        assertThat(pe.getValue()).isEqualTo("123405670");

        pe.setAsText("");
        assertThat(pe.getValue()).isEqualTo("");

        pe.setAsText(null);
        assertThat(pe.getValue()).isNull();
    }

    @Test
    public  void getAsTextWithLpad() {
        PaddedStringEditor pe = new PaddedStringEditor(Padding.LPAD, '0', 12);
        pe.setValue("123405670");
        assertThat(pe.getAsText()).isEqualTo("000123405670");

        pe.setValue("");
        assertThat(pe.getAsText()).isEqualTo("000000000000");

        pe.setValue(null);
        assertThat(pe.getAsText()).isEqualTo("000000000000");

    }

    @Test
    public  void setAsTextWithRpad() {
        PaddedStringEditor pe = new PaddedStringEditor(Padding.RPAD, '0', 12);
        pe.setAsText("012340567000");
        assertThat(pe.getValue()).isEqualTo("012340567");

        pe.setAsText("012340567");
        assertThat(pe.getValue()).isEqualTo("012340567");

        pe.setAsText("");
        assertThat(pe.getValue()).isEqualTo("");

        pe.setAsText(null);
        assertThat(pe.getValue()).isNull();
    }

    @Test
    public  void getAsTextWithRpad() {
        PaddedStringEditor pe = new PaddedStringEditor(Padding.RPAD, '0', 12);
        pe.setValue("012340567");
        assertThat(pe.getAsText()).isEqualTo("012340567000");

        pe.setValue("");
        assertThat(pe.getAsText()).isEqualTo("000000000000");

        pe.setValue(null);
        assertThat(pe.getAsText()).isEqualTo("000000000000");
    }

}
