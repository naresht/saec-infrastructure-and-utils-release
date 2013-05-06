package com.bfds.saec.batch.item.excel;

/**
 * Callback to handle skipped lines. Useful for header/footer processing.
 * 
 * @author B Raghavender
 */
public interface RowCallbackHandler {

    void handleRow(Sheet sheet, String[] row);

}
