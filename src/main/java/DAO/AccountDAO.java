package DAO;

import Model.Account;

public interface AccountDAO {
    Account createAccount(Account account);
    Account getAccountById(int id);
    Account getAccountByUsername(String username);
}
