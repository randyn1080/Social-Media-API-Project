package Service;

import Model.Account;

public interface AccountService {
    /**
     * Registers a new user account based on the provided account details.
     * Validates the account information to ensure the username is not blank,
     * the password meets the length requirement, and the account does not already exist.
     * If the registration is successful, the newly created account is returned.
     * Otherwise, null is returned to indicate the registration failure.
     *
     * @param account the Account object containing the user's details to register
     *                (must include a non-blank username and a password with a minimum length of 4 characters)
     * @return the registered Account object if the registration is successful,
     *         or null if validation fails or the account already exists
     */
    Account registerUser(Account account);

    /**
     * Attempts to log in an account using the provided credentials.
     * Validates the username and password of the account against existing records.
     * If the credentials match an existing account, returns the account information.
     * Otherwise, returns null to indicate unsuccessful login.
     *
     * @param account the account object containing the username and password to validate
     * @return the logged-in account object if credentials are valid, otherwise null
     */
    Account login(Account account);

    /**
     * Determines whether an account with the specified ID exists in the system.
     *
     * @param accountId the unique identifier of the account to check
     * @return true if the account exists, false otherwise
     */
    Boolean accountExists(int accountId);
}
