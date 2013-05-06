package com.bfds.saec.batch.file.util;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component("fillerParser")
public class FillerParser {

    private Map<String, String[]> fillParamsCache = Maps.newConcurrentMap();

    public String getFiller(String fillFunction) {
        return parseFiller(fillFunction);
    }

    private String parseFiller(String propertyName) {
        String ret = null;
        if(isFiller(propertyName)) {
            propertyName    = StringUtils.trimAllWhitespace(propertyName);
            //TODO: Validate filler pattern
            String[] fillerParams = getFillerParams(propertyName);
            char fillerChar = fillerParams[0].toCharArray()[0];
            int fillSize    = Integer.parseInt(fillerParams[1]);
            return buildFiller(fillerChar,fillSize );
        }
        return ret;
    }

    private String[] getFillerParams(String str) {
        String[] ret = fillParamsCache.get(str);
        if(ret == null) {
            ret = parseFillerParams(str);
        }
        return ret;
    }

    private String[] parseFillerParams(String str) {
        str = str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        if(str.indexOf('@') != -1) {
            return str.split("@");
        }
        return new String[] {" ", str};
    }

    private String buildFiller(char fillerChar, int fillSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fillSize; i++) {
            sb.append(fillerChar);
        }
        return sb.toString();
    }

    private boolean isFiller(String str) {
        return str.contains("(");
    }

}
