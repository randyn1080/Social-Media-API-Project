package DAO;

import Model.Account;

public interface AccountDAO {
    Account createAccount(Account account);
    Account getAccountById(int accountId);
    Account getAccountByUsername(String username);
}
