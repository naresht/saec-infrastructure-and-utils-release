package com.bfds.scheduling.domain;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileConfigTest {
	
	@Before
	public void init() throws Exception {
		(new File("target/X/saec/out")).mkdirs();	
		(new File("target/X/saec/in")).mkdirs();
		(new File("target/X/saec-archive/saec/in")).mkdirs();
		(new File("target/X/saec-archive/saec/out")).mkdirs();
	}
	
	@After
	public void cleanup() throws IOException {
		FileUtils.forceDelete(new File("target/X/"));
	}
	
	@Test
	public void testLocationFileRoot() {
		FileConfig fc = new FileConfig();
		fc.setFilePath("/a/b/c.txt");
		fc.setRootFolder("/");
		assertThat(fc.getLocationResourcePath()).isEqualTo("/a/b/c.txt");
		
		fc.setRootFolder("/saec/");
				
		assertThat(fc.getLocationResourcePath()).isEqualTo("/saec/a/b/c.txt");		
	}
	
	@Test
	public void validateFileLocation() {
		FileConfig fc = new FileConfig();
		fc.setRootFolder("target/X");
		fc.setArchiveFolderRoot("target/X/saec-archive");
		fc.setFilePath("/saec/out/a.txt");
		
		assertThat(fc.validate()).isEmpty();		
	}	
	
	@Test
	public void testTempLocationResourcePath() {
		FileConfig fc = new FileConfig();
		fc.setFilePath("/a/b/");

		fc.setRootFolder("/saec/");
		fc.setTempFolder("/temp/saec");
		fc.setArchiveFolderRoot("/archive/saec");
		
		assertThat(fc.getTempLocationResourcePath()).isEqualTo("/temp/saec/a/b");
	}
	
	@Test
	public void testArchiveLocationResourcePath() {
		FileConfig fc = new FileConfig();
		fc.setFilePath("/a/b/");

		fc.setRootFolder("/saec/");
		fc.setArchiveFolderRoot("/archive/saec");

		assertThat(fc.getArchiveLocationResourcePath()).isEqualTo("/archive/saec/a/b/");
		
		fc.setArchiveFilePath("/saec-archive/");
		
		assertThat(fc.getArchiveLocationResourcePath()).isEqualTo("/archive/saec/saec-archive/");
	}

}
