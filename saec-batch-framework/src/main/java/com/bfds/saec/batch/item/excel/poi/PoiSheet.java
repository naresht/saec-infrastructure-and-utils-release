
package com.bfds.saec.batch.item.excel.poi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import com.bfds.saec.batch.item.excel.Sheet;

/**
 * Sheet implementation for Apache POI.
 * 
 * @author Raghavender B
 *
 */
public class PoiSheet implements Sheet {

    private final org.apache.poi.ss.usermodel.Sheet delegate;

    /**
     * Constructor which takes the delegate sheet.
     * 
     * @param delegate the apache POI sheet
     */
    PoiSheet(final org.apache.poi.ss.usermodel.Sheet delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfRows() {
        return this.delegate.getLastRowNum() + 1;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.delegate.getSheetName();
    }

    /**
     * {@inheritDoc}
     */
    public String[] getRow(final int rowNumber) {
        if (rowNumber > this.delegate.getLastRowNum()) {
            return null;
        }
        final Row row = this.delegate.getRow(rowNumber);
        final List<String> cells = new LinkedList<String>();
        int noOfColumns = getNumberOfColumns();
        for(int i=0; i<= noOfColumns;i++){
        	final Cell cell =row.getCell(i);
        	if(cell ==null){
        		cells.add(null);
        		continue;
        	}
        	switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					cells.add(new DataFormatter(Locale.getDefault()).formatCellValue(cell));
				} else {
					cells.add(String.valueOf(cell.getNumericCellValue()));
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cells.add(String.valueOf(cell.getBooleanCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:
			case Cell.CELL_TYPE_BLANK:
				cells.add(cell.getStringCellValue());
				break;
			default:
				throw new IllegalArgumentException(
						"Cannot handle cells of type " + cell.getCellType());

			}
        }
        
        return cells.toArray(new String[cells.size()]);
    }

    /**
     * {@inheritDoc}
     */
    public String[] getHeader() {
    	final Row row = this.delegate.getRow(0);
        final List<String> cells = new LinkedList<String>();
        final Iterator<Cell> cellIter = row.iterator();
        while(cellIter.hasNext()){
        	final Cell cell = cellIter.next();
        	switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					cells.add(new DataFormatter(Locale.getDefault()).formatCellValue(cell));
				} else {
					cells.add(String.valueOf(cell.getNumericCellValue()));
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cells.add(String.valueOf(cell.getBooleanCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:
			case Cell.CELL_TYPE_BLANK:
				cells.add(cell.getStringCellValue());
				break;
			default:
				throw new IllegalArgumentException(
						"Cannot handle cells of type " + cell.getCellType());

			}
        }
        return cells.toArray(new String[cells.size()]);
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfColumns() {
    	final String[] columns = this.getHeader();
    	if (columns != null) {
        	return columns.length;
    		}
        return 0;
    }
}
