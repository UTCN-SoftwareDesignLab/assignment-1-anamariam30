package services.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountServiceMySQL(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Boolean createAccount(Client client, Account account) {

        Boolean save = accountRepository.save(account);
        Boolean addAccount = accountRepository.addAccountToClient(account, client);

        return save && addAccount;

    }

    @Override
    public Boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    @Override
    public Boolean deleteAccount(Client client, Account account) {
        Boolean aBoolean = accountRepository.deleteAccountFromClient(account);
        Boolean aBoolean1 = accountRepository.deleteAccount(account);
        return aBoolean && aBoolean1;
    }

    @Override
    public List<Account> getAccount(Client client) {
        return accountRepository.findClientAccount(client);
    }

    @Override
    public Notification<Boolean> transferMoney(Account account1, Account account2, Long amount) {

        Notification<Boolean> notification = new Notification<>();
        if (account1 == null || account2 == null) {
            notification.addError("Invalid Account!");
            notification.setResult(false);
            return notification;
        }
        Long newBalanceAccount1 = account1.getAmountOfMoney() - amount;
        Long newBalanceAccount2 = account2.getAmountOfMoney() + amount;

        if (newBalanceAccount1 < 0) {
            notification.addError("Not enough money!");
            notification.setResult(false);
            return notification;
        }

        Account newAccount1 = new AccountBuilder()
                .setId(account1.getId())
                .setAmountOfMoney(newBalanceAccount1)
                .build();

        Account newAccount2 = new AccountBuilder()
                .setId(account2.getId())
                .setAmountOfMoney(newBalanceAccount2)
                .build();
        if (!accountRepository.updateAccount(newAccount2) || !accountRepository.updateAccount(newAccount1)) {
            notification.addError("Error!");
            notification.setResult(false);
        } else {
            notification.setResult(true);
        }


        return notification;


    }

    @Override
    public Notification<Boolean> processBill(String accountNo, Long amount) {
        Account account = accountRepository.findAccountByIdNumber(accountNo);
        Notification<Boolean> notification = new Notification<>();
        if (account == null) {
            notification.addError("Invalid Account");
            notification.setResult(false);
            return notification;
        }
        Long newBalanceAccount = account.getAmountOfMoney() - amount;
        if (newBalanceAccount < 0) {
            notification.addError("Not enough funds!");
            notification.setResult(false);
            return notification;
        }
        Account build = new AccountBuilder()
                .setId(account.getId())
                .setAmountOfMoney(newBalanceAccount)
                .build();
        if (!accountRepository.updateAccount(build)) {
            notification.addError("Error!");
            notification.setResult(false);
            return notification;
        }

        return notification;
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findAccountById(accountId);
    }

    @Override
    public Notification<Account> getAccountByIdNumber(String accountIdNumber) {
        Notification<Account> notification = new Notification<>();

        Account accountByIdNumber = accountRepository.findAccountByIdNumber(accountIdNumber);
        if (accountByIdNumber == null) {
            notification.addError("Invalid Account!");
            notification.setResult(null);
        } else {
            notification.setResult(accountByIdNumber);
        }

        return notification;
    }
}
