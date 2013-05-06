package org.springframework.batch.item.file.mapping;

import org.springframework.batch.item.file.transform.DefaultFixedLengthTokenizer;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.FlatFileAnnotationParser;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;


public class DefaultFileRecordLineMapper implements LineMapper, InitializingBean, ApplicationContextAware {

    private FlatFileFieldSetMapper fieldSetMapper;

    private DefaultFixedLengthTokenizer tokenizer;

    private Class<?> type;

    private ApplicationContext applicationContext;

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Autowired
    private FlatFileAnnotationParser parser;

    @Override
    public Object mapLine(String line, int lineNumber) throws Exception {
        return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
    }

    public void setLineTokenizer(DefaultFixedLengthTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FlatFileFieldSetMapper fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(stepExecution, "stepExecution must be set");
        if(type == null) {
            type = FileJobUtils.getFileRecordClass(stepExecution, this);
        }
        Assert.notNull(type, "type must be set");
        if(tokenizer == null) {
            tokenizer = buildFixedLengthTokenizer();
        }
        if(fieldSetMapper == null) {
            fieldSetMapper = buildFlatFileFieldSetMapper();
        }
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }

    /**
     * TODO: Find a better way to do this.
     */
    @Autowired
    @Qualifier("defaultFlatFileFieldSetMapper")
    FlatFileFieldSetMapper  flatFileFieldSetMapper;

    private  FlatFileFieldSetMapper buildFlatFileFieldSetMapper() {
        flatFileFieldSetMapper.setTargetType(type);
        return flatFileFieldSetMapper;
    }

    private DefaultFixedLengthTokenizer buildFixedLengthTokenizer() {
        DefaultFixedLengthTokenizer ret = new DefaultFixedLengthTokenizer();
        ret.setType(type);
        ret.setParser(parser);
        ret.afterPropertiesSet();
        return ret;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
