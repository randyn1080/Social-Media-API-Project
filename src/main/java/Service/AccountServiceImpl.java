package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import Model.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountDAO accountDAO;

    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOImpl();
    }

    @Override
    public Account registerUser(Account account) {
        // Validate account
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            logger.warn("Registration failed: Username is blank");
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            logger.warn("Registration failed: Password is too short");
            return null;
        }
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            logger.warn("Registration failed: Account already exists");
            return null;
        }
        // validated, create the account
        logger.info("Registering new user: {}", account.getUsername());
        return accountDAO.createAccount(account);
    }

    @Override
    public Account login(Account account) {
        if (account.getUsername() == null ||
                account.getUsername().isBlank() ||
                account.getPassword() == null ||
                account.getPassword().isBlank()) {
            logger.warn("Login failed: Username or Password is blank");
            return null;
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());

        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            logger.info("Login successful");
            return existingAccount;
        }

        logger.info("Login failed: Username or Password does not match");
        return null;
    }

    @Override
    public Boolean accountExists(int accountId) {
        boolean exists = accountDAO.getAccountById(accountId) != null;

        if (exists) {
            logger.info("Account exists");
        } else {
            logger.info("Account does not exist");
        }

        return exists;
    }

}