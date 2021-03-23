package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private User employee;

    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClient;
    private JButton btnViewAllClient;
    private JButton btnPayBill;
    private JButton btnTransferMoney;
    private JButton btnViewAccount;
    private JButton btnAddAccount;
    private JButton btnDeleteAccount;
    private JButton btnUpdateAccount;
    private JButton btnBack;


    private JTextField tfClientName;
    private JTextField tfNewClientName;
    private JTextField tfCardNumber;
    private JTextField tfNewCardNumber;
    private JTextField tfPersonalNumericalCode;
    private JTextField tfAddress;
    private JTextField tfNewAddress;
    private JTextField tfAccountType;
    private JTextField tfAccountIdNumber;


    private JTextField tfAccountFrom;
    private JTextField tfAccountTo;
    private JTextField tfAmount;

    private JLabel lClientName;
    private JLabel lNewClientName;
    private JLabel lCardNumber;
    private JLabel lNewCardNumber;
    private JLabel lPersonalNumericalCode;
    private JLabel lAddress;
    private JLabel lNewAddress;

    private JLabel lAccountFrom;
    private JLabel lAccountTo;
    private JLabel lAmount;
    private JLabel lAccountType;
    private JLabel lAccountIdNumber;


    public EmployeeView() throws HeadlessException {
        setSize(600, 900);
        setLocationRelativeTo(null);
        setTitle("EMPLOYEE");
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(lClientName);
        add(tfClientName);
        add(lCardNumber);
        add(tfCardNumber);
        add(lPersonalNumericalCode);
        add(tfPersonalNumericalCode);
        add(lAddress);
        add(tfAddress);
        add(lNewClientName);
        add(tfNewClientName);
        add(lNewCardNumber);
        add(tfNewCardNumber);
        add(lNewAddress);
        add(tfNewAddress);
        add(btnAddClient);
        add(btnUpdateClient);
        add(btnViewClient);
        add(btnViewAllClient);

        add(lAccountIdNumber);
        add(tfAccountIdNumber);
        add(lAccountType);
        add(tfAccountType);
        add(btnAddAccount);
        add(btnUpdateAccount);
        add(btnViewAccount);
        add(btnDeleteAccount);


        add(lAmount);
        add(tfAmount);
        add(lAccountFrom);
        add(tfAccountFrom);
        add(btnPayBill);
        add(lAccountTo);
        add(tfAccountTo);
        add(btnTransferMoney);
        add(btnBack);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnAddClient = new JButton("AddClient");
        btnUpdateClient = new JButton("UpdateClient");
        btnViewClient = new JButton("ViewClient");
        btnViewAllClient = new JButton("ViewAllClients");
        btnPayBill = new JButton("PayBill");
        btnViewAccount = new JButton("ViewClientAccount");
        btnTransferMoney = new JButton("TransferMoney");
        btnAddAccount = new JButton("AddAccount");
        btnDeleteAccount = new JButton("DeleteAccount");
        btnUpdateAccount = new JButton("UpdateAccount");
        btnBack = new JButton("Back");


        tfClientName = new JTextField();
        tfNewClientName = new JTextField();
        tfCardNumber = new JTextField();
        tfNewCardNumber = new JTextField();
        tfPersonalNumericalCode = new JTextField();
        tfAddress = new JTextField();
        tfNewAddress = new JTextField();
        tfAccountFrom = new JTextField();
        tfAccountTo = new JTextField();
        tfAmount = new JTextField();
        tfAccountIdNumber = new JTextField();
        tfAccountType = new JTextField();

        lClientName = new JLabel("Client Name");
        lNewClientName = new JLabel("New Client Name");
        lCardNumber = new JLabel("Card Number");
        lNewCardNumber = new JLabel("New Card Number");
        lPersonalNumericalCode = new JLabel("Personal Numerical Code");
        lAddress = new JLabel("Address");
        lNewAddress = new JLabel("New Address");
        lAccountFrom = new JLabel("Account From");
        lAccountTo = new JLabel("Account To");
        lAmount = new JLabel("Amount");
        lAccountType = new JLabel("Account Type");
        lAccountIdNumber = new JLabel("AccountIdNumber");



    }


    public void setAddButtonActionListener(ActionListener AddButtonListener) {
        this.btnAddClient.addActionListener(AddButtonListener);

    }

    public void setUpdateButtonActionListener(ActionListener UpdateButtonListener) {
        this.btnUpdateClient.addActionListener(UpdateButtonListener);
    }

    public void setViewButtonActionListener(ActionListener ViewButtonListener) {

        this.btnViewClient.addActionListener(ViewButtonListener);

    }

    public void setViewAllButtonActionListener(ActionListener ViewAllButtonListener) {

        this.btnViewAllClient.addActionListener(ViewAllButtonListener);

    }

    public void setPayBillButtonActionListener(ActionListener PayBillButtonListener) {

        this.btnPayBill.addActionListener(PayBillButtonListener);

    }

    public void setTransferMoneyButtonActionListener(ActionListener TransferMoneyButtonListener) {

        this.btnTransferMoney.addActionListener(TransferMoneyButtonListener);

    }

    public void setAddAccountButtonActionListener(ActionListener AddAccountButtonListener) {

        this.btnAddAccount.addActionListener(AddAccountButtonListener);

    }

    public void setViewAccountButtonActionListener(ActionListener ViewAccountButtonListener) {

        this.btnViewAccount.addActionListener(ViewAccountButtonListener);

    }

    public void setDeleteAccountButtonActionListener(ActionListener DeleteAccountButtonListener) {

        this.btnDeleteAccount.addActionListener(DeleteAccountButtonListener);

    }

    public void setUpdateAccountButtonActionListener(ActionListener UpdateAccountButtonListener) {

        this.btnUpdateAccount.addActionListener(UpdateAccountButtonListener);

    }

    public void setBackButtonActionListener(ActionListener BackButtonListener) {

        this.btnBack.addActionListener(BackButtonListener);
    }



    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getEmployee() {
        return employee;
    }


    public String getClientName() {
        return tfClientName.getText();
    }

    public String getNewClientName() {
        return tfNewClientName.getText();
    }

    public String getPNCode() {
        return tfPersonalNumericalCode.getText();
    }

    public String getCardNumber() {
        return tfCardNumber.getText();
    }

    public String getNewCardNumber() {
        return tfNewCardNumber.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getNewAddress() {
        return tfNewAddress.getText();
    }

    public String getAccountFrom() {
        return tfAccountFrom.getText();
    }

    public String getAccountTo() {
        return tfAccountTo.getText();
    }

    public String getAmount() {
        return tfAmount.getText();
    }

    public String getAccountType() {
        return tfAccountType.getText();
    }

    public String getAccountId() {
        return tfAccountIdNumber.getText();
    }


}
