package com.bfds.saec.util;

import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.RegexFileFilter;

import org.springframework.core.io.ClassPathResource;

public class FileUtils {
	public static String getFileContents(final String classPathFile) {
		ClassPathResource r = new ClassPathResource(classPathFile);
		try {
			return org.apache.commons.io.FileUtils.readFileToString(r.getFile());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static String getFileName(final String fileStr) {
		File f = new File(fileStr);
		return f.getName();
	}

    public static List<File> getFilesWithFilePattern(File directory,
                                                     String filePattern) {
        FileFilter filter = new RegexFileFilter(filePattern,IOCase.INSENSITIVE);
        File[] files = directory.listFiles(filter);
        return files == null ? java.util.Collections.<File> emptyList()
                : Arrays.asList(files);
    }

    public static List<File> getFilesWithFilePatternDDA(File directory,
                                                        String filePattern, String dda) {

        FileFilter filter = new RegexFileFilter(filePattern);
        File[] files = directory.listFiles(filter);
        return files == null ? java.util.Collections.<File> emptyList()
                : Arrays.asList(files);

    }
}
