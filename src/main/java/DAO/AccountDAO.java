package DAO;

import Model.Account;

public interface AccountDAO {
    /**
     * Creates a new account in the database with the provided account details.
     *
     * @param account the account object containing the username and password to be created
     * @return the Account object with its generated account_id if creation is successful,
     *         or null if the account could not be created
     */
    Account createAccount(Account account);

    /**
     * Retrieves an account from the database based on the specified account ID.
     *
     * @param accountId the ID of the account to be retrieved
     * @return the Account object if found, or null if no account exists with the given ID
     */
    Account getAccountById(int accountId);

    /**
     * Retrieves an account from the database based on the specified username.
     *
     * @param username the username of the account to be retrieved
     * @return the Account object if found, or null if no account exists with the given username
     */
    Account getAccountByUsername(String username);
}
