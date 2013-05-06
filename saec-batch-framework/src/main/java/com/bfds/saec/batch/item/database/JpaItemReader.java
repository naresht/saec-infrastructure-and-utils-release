package com.bfds.saec.batch.item.database;

import org.springframework.batch.item.database.JpaPagingItemReader;

/**
 * A {@link JpaPagingItemReader} that always reads page 0. 
 * This behavior is required if the items read are modified in a way that they will 
 * no longer be picked by the query. 
 *
 * @param <T>
 */
public class JpaItemReader<T> extends JpaPagingItemReader<T> {

    @Override
    public int getPage() {
        return 0;
    }

}
