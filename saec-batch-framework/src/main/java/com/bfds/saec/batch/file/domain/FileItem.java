package com.bfds.saec.batch.file.domain;

import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.MappedSuperclass;

/**
 * Base class for all entities that represent a data record in the flat file
 *
 */
@MappedSuperclass
@RooJavaBean
@RooToString
public abstract class FileItem extends FileRecord {



}
