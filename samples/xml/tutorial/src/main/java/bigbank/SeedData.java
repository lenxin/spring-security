package bigbank;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SeedData implements InitializingBean {
	private BankDao bankDao;

	public void afterPropertiesSet() {
		Assert.notNull(bankDao, "bankDao cannot be null");
		bankDao.createOrUpdateAccount(new Account("rod"));
		bankDao.createOrUpdateAccount(new Account("dianne"));
		bankDao.createOrUpdateAccount(new Account("scott"));
		bankDao.createOrUpdateAccount(new Account("peter"));
	}

	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

}
