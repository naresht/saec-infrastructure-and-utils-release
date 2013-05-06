/**
 * 
 */
package org.springframework.batch.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.retry.listener.RetryListenerSupport;


public class BatchRetryListener extends RetryListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(BatchRetryListener.class);

	@Override
	public <T> void onError(RetryContext context, RetryCallback<T> callback, Throwable throwable) {
		log.debug("retried operation", throwable);
	}
}