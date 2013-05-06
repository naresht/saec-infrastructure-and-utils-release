package org.springframework.batch.item.file.transform;


import com.bfds.saec.batch.annotations.RangeVo;
import com.bfds.saec.batch.file.util.FlatFileAnnotationParser;
import com.google.common.collect.Lists;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

public class DefaultFixedLengthTokenizer extends org.springframework.batch.item.file.transform.FixedLengthTokenizer implements InitializingBean {
    private Class<?> type;

    @Autowired
    private FlatFileAnnotationParser parser;

    @Override
    public void afterPropertiesSet()  {
        Assert.notNull(type, "type is null");
        Assert.notNull(parser, "parser is null");

        buildRange();
    }

    public void buildRange() {
        List<RangeVo> propertyRanges = parser.parsePropertyRanges(type);
        StringBuilder sb = new StringBuilder();
        List<String> names = Lists.newArrayList();
        for(RangeVo vo : propertyRanges) {
            if(sb.length() > 0) {
                sb.append(",");
            }
            sb.append(vo.getValue());
            names.add(vo.getPropertyName());
        }

        this.setNames(names.toArray(new String[names.size()]));
        this.setColumns(toRanges(sb.toString()));
    }

    private org.springframework.batch.item.file.transform.Range[] toRanges(String columns) {
        RangeArrayPropertyEditor pe = new RangeArrayPropertyEditor();
        pe.setAsText(columns);
        return (org.springframework.batch.item.file.transform.Range[]) pe.getValue();
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setParser(FlatFileAnnotationParser parser) {
        this.parser = parser;
    }
}
