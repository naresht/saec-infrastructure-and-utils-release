package com.bfds.saec.batch.item.excel;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;

/**
 * 
 * @author B Raghavender
 *
 */
public class DefaultExcelWorkbookFactory implements ExcelWorkbookFactory {
	
	@Override
	public Workbook create(Resource resource) throws IOException {
		String ext = getFileExtension(resource);
		Workbook wb = null;
		if("XLS".equals(ext)){
			wb = new HSSFWorkbook();
		}else{
			throw new UnknownFileTypeException("Support only *.xls and *.xlsx");
		}
		return wb;
	}
	
	private String getFileExtension(Resource resource){
		String ext = FilenameUtils.getExtension(resource.getFilename());
		ext = ext.toUpperCase();
		return ext;
	}
	
	

}
