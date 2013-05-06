package com.bfds.saec.batch.item.excel;
/**
 * @author B Raghavender
 */
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;

public interface ExcelWorkbookFactory {
	
	Workbook create(Resource resource) throws IOException;
	
}
