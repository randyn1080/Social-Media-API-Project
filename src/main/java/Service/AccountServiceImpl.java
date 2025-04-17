package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import Model.Account;

public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOImpl();
    }
    
    @Override
    public Account registerUser(Account account) {
        // Validate account
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            return null;
        }
        // validated, create the account
        return accountDAO.createAccount(account);
    }

    @Override
    public Account login(Account account) {
        if (account.getUsername() == null ||
                account.getUsername().isBlank() ||
                account.getPassword() == null ||
                account.getPassword().isBlank()) {
            return null;
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());

        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }

        return null;
    }

    @Override
    public Boolean accountExists(int accountId) {
        return accountDAO.getAccountById(accountId) != null;
    }
    
}
