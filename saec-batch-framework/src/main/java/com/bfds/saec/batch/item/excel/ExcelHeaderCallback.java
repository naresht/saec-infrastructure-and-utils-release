package com.bfds.saec.batch.item.excel;

import org.apache.poi.ss.usermodel.Row;

/**
 * Callback interface for writing to a header to a file.
 * 
 * @author Raghavender Bandar
 */
public interface ExcelHeaderCallback {
	
	/**
	 * Write contents to a excel file using the supplied {@link Row}. It is not
	 * required to flush the writer inside this method.
	 */
	void writeHeader(Row row);
	
}
