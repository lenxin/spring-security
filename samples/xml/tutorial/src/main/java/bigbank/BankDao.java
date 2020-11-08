package bigbank;

public interface BankDao {
	Account readAccount(Long id);

	void createOrUpdateAccount(Account account);

	Account[] findAccounts();
}
