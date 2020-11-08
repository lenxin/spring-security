package org.springframework.security.config;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 */
public class MockTransactionManager implements PlatformTransactionManager {

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
		return mock(TransactionStatus.class);
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
	}

}
