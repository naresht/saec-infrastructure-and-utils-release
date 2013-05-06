package com.bfds.saec.batch.format.propertyeditors;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AssumedDecimalNumberEditorTest {

    @Test
    public  void setAsText() {
        AssumedDecimalNumberEditor ne = new AssumedDecimalNumberEditor(Double.class, 2);
        ne.setAsText("10000");
        assertThat(ne.getValue()).isEqualTo(new Double(100));
        ne.setAsText("10050");
        assertThat(ne.getValue()).isEqualTo(new Double(100.50));
        ne.setAsText(null);
        assertThat(ne.getValue()).isNull();
        ne.setAsText("");
        assertThat(ne.getValue()).isNull();
    }

    @Test
    public void getAsText() {
        AssumedDecimalNumberEditor ne = new AssumedDecimalNumberEditor(Double.class, 2);
        ne.setValue(100d);
        assertThat(ne.getAsText()).isEqualTo("10000");
        ne.setValue(100.5d);
        assertThat(ne.getAsText()).isEqualTo("10050");
        ne.setValue(0d);
        assertThat(ne.getAsText()).isEqualTo("000");  // TODO: Is this ok.
    }
}