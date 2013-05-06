package com.bfds.saec.batch.format.propertyeditors;

import com.bfds.saec.batch.annotations.Padding;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyEditor;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CompositeEditorTest {

    private CompositeEditor ne = null;

    @Before
    public void before() {
        List<PropertyEditor> list = Lists.newArrayList();
        list.add(new PaddedStringEditor(Padding.LPAD, '0', 11));
        list.add(new AssumedDecimalNumberEditor(Double.class, 2));
        ne = new CompositeEditor(list);
    }

    @Test
    public  void setAsText() {
        ne.setAsText("00000001050");
        assertThat(ne.getValue()).isEqualTo(10.50d);

        ne.setAsText("");
        assertThat(ne.getValue()).isNull();

        ne.setAsText(null);
        assertThat(ne.getValue()).isNull();
    }

    @Test
    public  void getAsText() {
        ne.setValue(100d);
        assertThat(ne.getAsText()).isEqualTo("00000010000");
        ne.setValue(100.50d);
        assertThat(ne.getAsText()).isEqualTo("00000010050");
        ne.setValue(0d);
        assertThat(ne.getAsText()).isEqualTo("00000000000");  // TODO: Is this ok.
    }
}
