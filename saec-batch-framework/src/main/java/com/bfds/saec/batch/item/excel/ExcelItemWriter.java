package com.bfds.saec.batch.item.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.WriterNotOpenException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.util.ExecutionContextUserSupport;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 
 * 
 * 
 * @author Raghavender B
 *
 * @param <T>
 */
public class ExcelItemWriter <T> extends ExecutionContextUserSupport implements ResourceAwareItemWriterItemStream<T>,
	InitializingBean {
	
	protected static final Log logger = LogFactory.getLog(ExcelItemWriter.class);
	
	private Resource resource;
	
	private boolean initialized = false;
	
	private Workbook workbook;
	
	private String[] names;
	
	private String sheetName = "Untitled";
	/**
	 * If this property is set , It indicates which field has Sheet name.
	 * if not set, it writes in a single sheet with default sheet name i.e Untitled
	*/ 
	private Integer indexOfSheetName; 
	
	private Sheet sheet;
	
	private long linesWritten = 0;
	
	private int currentRowIndex = 0;
	
	private OutputStream outputStream;
	
	private boolean shouldDeleteIfEmpty = true;
	
	private ExcelHeaderCallback headerCallback;
	
	private ExcelFooterCallback footerCallback;
	
	private ExcelWorkbookFactory workbookFactory = new DefaultExcelWorkbookFactory();
	
	private FieldExtractor<T> fieldExtractor;
	
	private Map<String, Integer  > sheetRowIndex =new HashMap<String, Integer >(); 
	
	/**
	 *  Default date format we can specify other formats also.
	*/
	private String dateFormat = "MM/DD/YYYY";
	
	public ExcelItemWriter(){
		setName(ClassUtils.getShortName(ExcelItemWriter.class));
	}
	
	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public void setNames(String[] names) {
		this.names = names;
	}
	
	public void setSheetName(String sheetName){
		this.sheetName = sheetName;
	}
	
	

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @param indexOfSheetName the indexOfSheetName to set
	 */
	public void setIndexOfSheetName(Integer indexOfSheetName) {
		this.indexOfSheetName = indexOfSheetName;
	}

	/**
	 * headerCallback will be called before writing the first item to file.
	 * Newline will be automatically appended after the header is written.
	 */
	public void setHeaderCallback(ExcelHeaderCallback headerCallback) {
		this.headerCallback = headerCallback;
	}

	/**
	 * footerCallback will be called after writing the last item to file, but
	 * before the file is closed.
	 */
	public void setFooterCallback(ExcelFooterCallback footerCallback) {
		this.footerCallback = footerCallback;
	}
	
	public void setShouldDeleteIfEmpty(boolean shouldDeleteIfEmpty) {
		this.shouldDeleteIfEmpty = shouldDeleteIfEmpty;
	}
	
	/**
	 * 
	 * @param workbookFactory the workbookFactory to set
	 */
	public void setWorkbookFactory(ExcelWorkbookFactory workbookFactory) {
		this.workbookFactory = workbookFactory;
	}
	
	public void setFieldExtractor(FieldExtractor<T> fieldExtractor) {
		this.fieldExtractor = fieldExtractor;
	}
	
	
	@Override
	public void close() throws ItemStreamException {
		
		if(workbook==null) return;
		
		try{
			if (footerCallback != null) {
				footerCallback.writeFooter(createNextRow());
			}
			
			if(shouldDeleteIfEmpty && linesWritten == 0){
				return;
			}
			
			try {
				outputStream = new FileOutputStream(resource.getFile().getAbsolutePath(), false);
				workbook.write(outputStream);
			} catch (IOException e) {
				throw new ItemStreamException("Failed to write workbook to file", e);
			}
		}finally{
			IOUtils.closeQuietly(outputStream);
			//reset
			initialized = false;
			linesWritten = 0;
			currentRowIndex = 0;
		}
	}
	
	@Override
	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		Assert.notNull(resource, "The resource must be set");
		if(!initialized){
			doOpen(executionContext);
		}
		
	}
	
	protected void doOpen(ExecutionContext executionContext) 
			throws ItemStreamException {
		
		try {
			File file = resource.getFile();
			FileUtils.setUpOutputFile(file, false, false, true);
			workbook = workbookFactory.create(resource);
			initialized = true;
		} catch (IOException e) {
			throw new ItemStreamException("Failed to initialize writer", e);
		}
		
	}
	
	@Override
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
	}
	
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		if (!initialized) {
			throw new WriterNotOpenException("Writer must be open before it can be written to");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Writing to excel file with " + items.size() + " items.");
		}
		
		Object cellValues[] = null;
		for (T item : items) {
			cellValues = fieldExtractor.extract(item);
			linesWritten++;
		
		if(indexOfSheetName!=null){
			sheetName  =(String)cellValues[indexOfSheetName];
			cellValues=ArrayUtils.remove(cellValues, indexOfSheetName);
			}
		
		if(workbook.getSheet(sheetName) ==null){
			sheet =workbook.createSheet(sheetName);
			sheetRowIndex.put(sheetName, 0);
				if (headerCallback != null) {
				headerCallback.writeHeader(createNextRow());
				}
			}else{
			sheet =workbook.getSheet(sheetName);	
			}
		
		Row row = createNextRow();
		
		for(int i=0;i<cellValues.length;i++){
			Object cellValue = cellValues[i];
			if(cellValue==null){
				row.createCell(i, Cell.CELL_TYPE_BLANK);
			}else if(cellValue instanceof String){
				row.createCell(i, Cell.CELL_TYPE_STRING)
					.setCellValue(cellValue.toString());
			}else if(cellValue instanceof Integer){
				row.createCell(i, Cell.CELL_TYPE_NUMERIC)
					.setCellValue(((Number) cellValue).intValue());
			}else if(cellValue instanceof BigDecimal){
				row.createCell(i, Cell.CELL_TYPE_NUMERIC)
				.setCellValue(((Number) cellValue).doubleValue());
			}else if(cellValue instanceof Date){
				Cell cell =row.createCell(i);
				cell.setCellStyle(getDateStyle());
				cell.setCellValue((Date)cellValue);
			}else if(cellValue instanceof Calendar){
				row.createCell(i, Cell.CELL_TYPE_NUMERIC)
					.setCellValue((Calendar)cellValue);
			}else if(cellValue instanceof Boolean){
				row.createCell(i, Cell.CELL_TYPE_BOOLEAN)
					.setCellValue((Boolean)cellValue);
			}else{
				row.createCell(i, Cell.CELL_TYPE_STRING)
					.setCellValue(cellValue.toString());
			}
		}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(names!=null){
			if(headerCallback==null){
				headerCallback = new DefaultExcelHeaderCallback();
			}
			if(fieldExtractor==null){
				BeanWrapperFieldExtractor<T> fieldExtractor = new BeanWrapperFieldExtractor<T>();
				fieldExtractor.setNames(names);
				this.fieldExtractor = fieldExtractor;
			}
		}
		Assert.notNull(fieldExtractor, "An FieldExtractor must be provided.");
	}
	
	private Row createNextRow(){
		currentRowIndex = sheetRowIndex.get(sheetName);
		Row row = sheet.createRow(currentRowIndex);
		currentRowIndex++;
		sheetRowIndex.put(sheetName, currentRowIndex++);
		return row;
	}
	
	private CellStyle getDateStyle() {
			CellStyle dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(workbook.createDataFormat().getFormat(dateFormat));
		return dateStyle;
	}
	
	class DefaultExcelHeaderCallback implements ExcelHeaderCallback{

		@Override
		public void writeHeader(Row row) {
			for(int i=0;i<names.length;i++){
				Cell cell = row.createCell(i);
				cell.setCellValue(names[i]);
			}
		}
		
	}
}
