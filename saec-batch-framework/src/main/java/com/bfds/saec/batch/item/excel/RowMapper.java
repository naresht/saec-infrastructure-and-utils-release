package com.bfds.saec.batch.item.excel;

/**
 * Map rows from an excel sheet to an object.
 * 
 * @author B Raghavender
 *
 * @param <T>
 */
public interface RowMapper<T> {

    /**
     * Implementations must implement this method to map the provided row to 
     * the parameter type T.  The row number represents the number of rows
     * into a {@link Sheet} the current line resides.
     * 
     * @param sheet the current sheet
     * @param row to be mapped
     * @param rowNumber of the current row
     * @return mapped object of type T
     * @throws Exception if error occured while parsing.
     */
    T mapRow(Sheet sheet, String[] row, int rowNum,String dda) throws Exception;

}
