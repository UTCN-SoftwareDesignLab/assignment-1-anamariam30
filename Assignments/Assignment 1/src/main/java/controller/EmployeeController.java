package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import model.validation.AccountValidation;
import model.validation.Notification;
import services.account.AccountService;
import services.activity.ActivityService;
import services.client.ClientService;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import static database.Constants.Rights.*;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final LoginView loginView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;

    public EmployeeController(EmployeeView employeeView, LoginView loginView, ClientService clientService, AccountService accountService, ActivityService activityService) {
        this.employeeView = employeeView;
        this.loginView = loginView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.activityService = activityService;
        employeeView.setAddButtonActionListener(new AddButtonListener());
        employeeView.setUpdateButtonActionListener(new UpdateButtonListener());
        employeeView.setViewButtonActionListener(new ViewButtonListener());
        employeeView.setViewAllButtonActionListener(new ViewAllButtonListener());
        employeeView.setPayBillButtonActionListener(new PayBillButtonListener());
        employeeView.setTransferMoneyButtonActionListener(new TransferMoneyButtonListener());
        employeeView.setAddAccountButtonActionListener(new AddAccountButtonListener());
        employeeView.setViewAccountButtonActionListener(new ViewAccountButtonListener());
        employeeView.setUpdateAccountButtonActionListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonActionListener(new DeleteAccountButtonListener());
        employeeView.setBackButtonActionListener(new LogOutButtonListener());
    }


    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = employeeView.getClientName();
            String cardNumber = employeeView.getCardNumber();
            String CNP = employeeView.getPNCode();
            String address = employeeView.getAddress();

            Client client = new ClientBuilder()
                    .setName(clientName)
                    .setCardNumber(cardNumber)
                    .setCNP(CNP)
                    .setAddress(address)
                    .build();

            Notification<Boolean> notification = clientService.addClient(client);
            if (notification.hasErrors()) {

                JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());

            } else {
                if (notification.getResult() == false) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Duplicate CNP!");

                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client successfully added!");
                    updateActivities(ADD_CLIENT);
                }
            }


        }
    }

    private class ViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientCNP = employeeView.getPNCode();


            Notification<Client> notification = clientService.getClientByCNP(clientCNP);
            Client client = notification.getResult();


            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());

            } else {
                JFrame displayFrame = new JFrame("Client");
                displayFrame.setLocationRelativeTo(null);
                displayFrame.setSize(800, 300);
                displayFrame.setVisible(true);
                JTextArea displayText = new JTextArea();
                displayText.append(client.toString());
                displayText.setEditable(false);
                displayFrame.add(displayText);
                updateActivities(VIEW_CLIENT);
            }

        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientCNP = employeeView.getPNCode();
            String newClientName = employeeView.getNewClientName();
            String newAddress = employeeView.getNewAddress();
            String newCardNumber = employeeView.getNewCardNumber();

            Notification<Boolean> notification = clientService.updateClient(clientCNP, newClientName, newAddress, newCardNumber);
            if (!notification.hasErrors()) {

                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated successful!");
                updateActivities(UPDATE_CLIENT);


            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());
            }

        }
    }

    private class ViewAllButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame displayFrame = new JFrame("Clients");
            displayFrame.setLocationRelativeTo(null);
            displayFrame.setSize(800, 300);
            displayFrame.setVisible(true);
            JTextArea displayText = new JTextArea();
            displayText.append(clientService.getAllClients().toString());
            displayText.setEditable(false);
            displayFrame.add(displayText);
            updateActivities(VIEW_CLIENT);


        }
    }

    private class PayBillButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String accountFrom = employeeView.getAccountFrom();
            String amount = employeeView.getAmount();
            try {
                Notification<Boolean> notification = accountService.processBill(accountFrom, Long.parseLong(amount));
                if (!notification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Approved!");
                    updateActivities(PROCESS_UTILITIES);
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());

                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid input!");
            }

        }
    }

    private class TransferMoneyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String amount = employeeView.getAmount();
            String accountFrom = employeeView.getAccountFrom();
            String accountTo = employeeView.getAccountTo();
            try {

                long amount1 = Long.parseLong(amount);

                Notification<Account> accountNotification = accountService.getAccountByIdNumber(accountFrom);
                Notification<Account> accountNotification1 = accountService.getAccountByIdNumber(accountTo);
                if (accountNotification.hasErrors() || accountNotification1.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid Account!");

                } else {
                    Account accountF = accountNotification.getResult();
                    Account accountT = accountNotification1.getResult();

                    Notification<Boolean> notification = accountService.transferMoney(accountF, accountT, amount1);

                    if (notification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid Operation!");

                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transaction success !");

                        updateActivities(TRANSFER_MONEY);
                    }
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid Accounts!");

            }

        }
    }

    private class ViewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            String clientCNP = employeeView.getPNCode();
            Notification<Client> notification = clientService.getClientByCNP(clientCNP);
            if (!notification.hasErrors()) {
                List<Account> accounts = accountService.getAccount(notification.getResult());
                if (accounts == null) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid Account!");
                } else {
                    JFrame displayFrame = new JFrame("Accounts");
                    displayFrame.setLocationRelativeTo(null);
                    displayFrame.setSize(600, 300);
                    displayFrame.setVisible(true);
                    JTextArea displayText = new JTextArea();
                    displayText.append(accounts.toString());
                    displayText.setEditable(false);
                    displayFrame.add(displayText);
                    updateActivities(VIEW_ACCOUNT);


                }
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());
            }
        }
    }

    private class AddAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String clientCNP = employeeView.getPNCode();
            String accountId = employeeView.getAccountId();
            String accountType = employeeView.getAccountType();
            String amount = employeeView.getAmount();

            Notification<Boolean> notification = new Notification<>();
            try {
                Long amountL = Long.parseLong(amount);
                Account newAccount = new AccountBuilder()
                        .setAmountOfMoney(amountL)
                        .setType(accountType)
                        .setDateCreation(LocalDate.now())
                        .setIdentificationNumber(accountId)
                        .build();
                AccountValidation accountValidation = new AccountValidation(newAccount);

                if (!accountValidation.validate()) {

                    accountValidation.getErrors().forEach(notification::addError);
                    notification.setResult(false);

                } else {

                    Notification<Client> clientNotification = clientService.getClientByCNP(clientCNP);
                    if (!clientNotification.hasErrors()) {
                        Client clientByCNP = clientNotification.getResult();

                        if (accountService.createAccount(clientByCNP, newAccount)) {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully created!");
                            updateActivities(CREATE_ACCOUNT);
                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid Operation!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());

                    }
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid amount!");

            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String accountIdNo = employeeView.getAccountId();
            String amountS = employeeView.getAmount();
            try {

                Long amount = Long.parseLong(amountS);
                Notification<Account> notification1 = accountService.getAccountByIdNumber(accountIdNo);
                if (!notification1.hasErrors()) {
                    Account oldAccount = notification1.getResult();
                    Account newAccount = new AccountBuilder()
                            .setId(oldAccount.getId())
                            .setIdentificationNumber(accountIdNo)
                            .setAmountOfMoney(amount)
                            .setDateCreation(oldAccount.getDateCreation())
                            .setType(oldAccount.getType())
                            .build();

                    if (accountService.updateAccount(newAccount)) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successful updated!");
                        updateActivities(UPDATE_ACCOUNT);
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid operation");

                    }
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), notification1.getFormattedErrors());

                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Invalid amount!");
            }


        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientCNP = employeeView.getPNCode();
            String accountIdNo = employeeView.getAccountId();

            Notification<Account> accountNotification = accountService.getAccountByIdNumber(accountIdNo);
            if (!accountNotification.hasErrors()) {

                Account accountByIdNumber = accountNotification.getResult();
                Notification<Client> clientNotification = clientService.getClientByCNP(clientCNP);

                if (!clientNotification.hasErrors()) {
                    Client clientByCNP = clientNotification.getResult();

                    if (accountService.deleteAccount(clientByCNP, accountByIdNumber)) {

                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully deleted !");
                        updateActivities(DELETE_ACCOUNT);

                    }

                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
                }

            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());

            }
        }


    }


    private void updateActivities(String Activity) {
        activityService.addActivity(new ActivityBuilder()
                .setUser(employeeView.getEmployee())
                .setAction(Activity).build());
    }


    private class LogOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeView.setVisible(false);
            loginView.setVisible(true);
        }
    }
}
