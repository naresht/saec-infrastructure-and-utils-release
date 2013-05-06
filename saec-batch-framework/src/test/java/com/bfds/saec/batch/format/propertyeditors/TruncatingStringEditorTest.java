package com.bfds.saec.batch.format.propertyeditors;


import static org.fest.assertions.Assertions.assertThat;

import java.beans.PropertyEditor;
import java.lang.annotation.Annotation;

import org.junit.Test;

import com.bfds.saec.batch.annotations.PaddedStringFormat;
import com.bfds.saec.batch.annotations.Padding;
import com.bfds.saec.batch.file.util.FlatFileAnnotationParser;
public class TruncatingStringEditorTest {

	@Test
    public  void setAsText() {
		TruncatingStringEditor ne = new TruncatingStringEditor(5);
        ne.setAsText("10000");
        assertThat(ne.getValue()).isEqualTo("10000");        
        ne.setAsText(null);
        assertThat(ne.getValue()).isNull();
        ne.setAsText("");
        assertThat(ne.getValue()).isEqualTo(""); 
        
       
    }

    @Test
    public void getAsText() {
    	TruncatingStringEditor ne = new TruncatingStringEditor(5);
        ne.setValue("100001");
        assertThat(ne.getAsText()).isEqualTo("10000");
        ne.setValue(null);
        assertThat(ne.getAsText()).isNull();
        ne.setValue("");
        assertThat(ne.getAsText()).isEqualTo(""); 
    }
    
    @Test
    public void getAsTextWithTruncationAndPadding() {
    	PropertyEditor pe = getPaddingAndTruncatingEditor();    	
    	pe.setValue("12345678");
    	assertThat(pe.getAsText()).isEqualTo("12345");
    	
    	pe.setValue("123");
    	assertThat(pe.getAsText()).isEqualTo("00123");
    	
    }
        
    public void setAsTextWithTruncationAndPadding() {
    	PropertyEditor pe = getPaddingAndTruncatingEditor();    
    	
    	pe.setAsText("12345678");
    	assertThat(pe.getValue()).isEqualTo("12345");
    	
    }

	private PropertyEditor getPaddingAndTruncatingEditor() {
		PaddedStringFormat annotation = new PaddedStringFormat() {

 			@Override
 			public Class<? extends Annotation> annotationType() { return PaddedStringFormat.class;}

 			@Override
 			public Padding padding() { return Padding.LPAD; }

 			@Override
 			public char paddingChar() { return '0'; }

 			@Override
 			public int size() { return 5; }

 			@Override
 			public boolean truncate() { return true;}
 		};
 			
 			FlatFileAnnotationParser processor = new FlatFileAnnotationParser();
 			return processor.buildPropertyEditor(annotation);
	}

}
