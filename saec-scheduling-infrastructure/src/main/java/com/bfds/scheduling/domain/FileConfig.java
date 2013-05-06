package com.bfds.scheduling.domain;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

@RooJavaBean
@RooConfigurable
@RooToString
@Embeddable
public class FileConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    	
	@Value("${batchFilesRootFolder}")
	private transient String rootFolder; 	
	/**
	 * The file path including the actual file name. The file name can have substitution variable like dda and date.
	 */
    @NotNull   
	private String filePath;
    
	/**
	 * For outgoing : The temp location to store file before it is moved to the actual location. 
	 * For Incoming : The temp location to store file before it is processed. 
	 */
	@Value("${tempFolder}")
	private transient String tempFolder;
    
    @Value("${batchFilesArchiveRootFolder}")
	private transient String archiveFolderRoot;
    
	/**
	 * The archive file path including the actual file name. The file name can have substitution variable like dda and date.
	 */
	private String archiveFilePath;
	
	/**
	 * Applicable for out-files only. If true than create an empty file with an extension .cnf alongside the out-file.  
	 */
	private Boolean createCnfFile;
	
	public Map<String, String> validate() {
		 Map<String, String> ret = Maps.newHashMap();
		if(!StringUtils.hasText(this.filePath)) {
			ret.put("folderName", "Folder Name is mandatory.");
		}
		if(StringUtils.hasText(this.getLocationResourcePath()) && 
				!StringUtils.hasText(this.getArchiveLocationResourcePath())	) {
			ret.put("archiveFolder", "Archive Folder is mandatory.");
		}
		if(StringUtils.hasText(this.getLocationResourcePath()) && 
				!StringUtils.hasText(this.getTempLocationResourcePath())	) {
			ret.put("tempFolder", "Temp Folder is mandatory.");
		}
		validateResource(ret, this.getParentFolder(this.getLocationResourcePath()), "filePath");
		//validateResource(ret, this.getParentFolder(this.getArchiveLocationResourcePath()), "archiveFilePath");
		return ret;
	}

    @JsonIgnore
	private String getParentFolder(String locationResourcePath) {
		return locationResourcePath.substring(0, locationResourcePath.lastIndexOf("/"));
	}

    @JsonIgnore
	public String getFileNamePattern() {
		return this.getLocationResourcePath().substring(getLocationResourcePath().lastIndexOf("/") + 1);
	}
	private void validateResource(Map<String, String> ret,
			final String resourcePath, final String resourceName) {
		if(StringUtils.hasText(resourcePath)) {
			UrlResource resource = null;
			try {
				resource = new UrlResource((new File(resourcePath).toURI().toURL()));
				File f = resource.getFile();
				if(!f.exists() || !f.canWrite()) {
					ret.put(resourceName, "Resource is not writable: [" + resource + "]");
					
				}
				File.createTempFile("test", "test", f).delete();
				
			} catch (MalformedURLException e) {
				ret.put(resourceName, "Invalid resource. : [" + resource + "]"+e.getMessage());
			} catch (IOException e) {
				ret.put(resourceName, "Invalid resource. : [" + resource + "]"+e.getMessage());
			}			
		}
	}

    @JsonIgnore
	public String getLocationResourcePath() {
		return getResourcePath(filePath, rootFolder);
	}

    @JsonIgnore
	public String getLocationResourcePathFolder() {
		return this.getParentFolder(this.getLocationResourcePath());
	}

    @JsonIgnore
	public static String getResourcePath(String fileReletivePath, String parentDir) {
		if(!StringUtils.hasText(fileReletivePath)) {
			return null;
		}
		String ret = fileReletivePath;
		
		if(StringUtils.hasText(parentDir)) {
			if(fileReletivePath.startsWith("/")) {
				ret = fileReletivePath.substring(1, fileReletivePath.length());
			}
			if(parentDir.endsWith("/")) {
				ret = parentDir + ret;
			} else {
				ret = parentDir + "/" + ret;
			}
		}
		return ret;
	}

    @JsonIgnore
	public String getTempLocationResourcePath() { 
		return getResourcePath(this.getParentFolder(filePath), tempFolder);
	}

    @JsonIgnore
	public String getArchiveLocationResourcePath() { 
		return getResourcePath(StringUtils.hasText(archiveFilePath) ? archiveFilePath : filePath, archiveFolderRoot);
	}

	public String getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

	public String getTempFolder() {
		return tempFolder;
	}

	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}

	public String getArchiveFolderRoot() {
		return archiveFolderRoot;
	}

	public void setArchiveFolderRoot(String archiveFolderRoot) {
		this.archiveFolderRoot = archiveFolderRoot;
	}

}
