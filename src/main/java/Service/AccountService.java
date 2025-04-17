package Service;

import Model.Account;

public interface AccountService {
    Account registerUser(Account account);
    Account login(Account account);
    Boolean accountExists(int accountId);
}
