package bigbank;

import org.springframework.security.access.prepost.PreAuthorize;

public interface BankService {

	Account readAccount(Long id);

	Account[] findAccounts();

	@PreAuthorize("hasAuthority('supervisor') or "
			+ "hasAuthority('teller') and (#account.balance + #amount >= -#account.overdraft)")
	Account post(Account account, double amount);
}
