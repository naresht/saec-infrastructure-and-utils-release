
package com.bfds.saec.batch.item.excel;

import org.springframework.batch.item.ParseException;

/**
 * Exception thrown when parsing excel files. The name of the sheet, the row number on that sheet and the
 * name of the excel file can be passed in so that in exception handling we can reuse it. This class only has 
 * simply dependencies to make it is generic as possible.
 * 
 * @author B Raghavender
 *
 */
public class ExcelFileParseException extends ParseException {

    /**
     * 
     */
    private static final long serialVersionUID = -3939056060545496492L;

    private final String filename;
    private final String sheet;
    private final String[] row;
    private final int rowNumber;

    /**
     * Construct an {@link ExcelFileParseException}.
     * 
     * @param message the message
     * @param cause the root cause
     * @param filename the name of the excel file
     * @param sheet the name of the sheet
     * @param rowNumber the row number in the current sheet
     * @param row the row data as text
     */
    public ExcelFileParseException(final String message, final Throwable cause, final String filename,
            final String sheet, final int rowNumber, final String[] row) {
        super(message, cause);
        this.filename = filename;
        this.sheet = sheet;
        this.rowNumber = rowNumber;
        this.row = row;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getSheet() {
        return this.sheet;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public String[] getRow() {
        return this.row;
    }

}
