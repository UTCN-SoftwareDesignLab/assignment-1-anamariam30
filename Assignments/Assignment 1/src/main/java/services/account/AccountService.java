package services.account;

import model.Account;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface AccountService {
    Boolean createAccount(Client client, Account account);

    Boolean updateAccount(Account account);

    Boolean deleteAccount(Client client, Account account);

    List<Account> getAccount(Client client);

    Notification<Boolean> transferMoney(Account account1, Account account2, Long amount);

    Notification<Boolean> processBill(String accountNo, Long amount);

    Account getAccountById(Long accountId);

    Notification<Account> getAccountByIdNumber(String accountIdNumber);


}
