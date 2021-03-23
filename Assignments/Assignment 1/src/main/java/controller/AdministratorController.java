package controller;

import model.validation.Notification;
import services.report.GenerateReportsMySQL;
import services.user.management.AdminManagerService;
import view.AdministratorView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorController {
    private final AdministratorView administratorView;
    private final LoginView loginView;
    private final AdminManagerService adminManagerService;
    private final GenerateReportsMySQL generateReportsMySQL;

    public AdministratorController(AdministratorView administratorView, LoginView loginView, AdminManagerService adminManagerService, GenerateReportsMySQL generateReportsMySQL) {
        this.administratorView = administratorView;
        this.loginView = loginView;
        this.adminManagerService = adminManagerService;
        this.generateReportsMySQL = generateReportsMySQL;
        administratorView.setAddButtonActionListener(new AddButtonListener());
        administratorView.setDeleteButtonActionListener(new DeleteButtonListener());
        administratorView.setUpdateButtonActionListener(new UpdateButtonListener());
        administratorView.setViewAllButtonActionListener(new ViewAllButtonListener());
        administratorView.setGenerateReportsButtonActionListener(new GenerateReportsButtonListener());
        administratorView.setBackButtonActionListener(new BackButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            String password = administratorView.getPassword();
            Notification<Boolean> registerNotification = adminManagerService.createEmployee(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                boolean result = registerNotification.getResult();
                if (result) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee add successful!");
                } else
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Duplicate user!");

            }
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            Notification<Boolean> notification = adminManagerService.deleteEmployee(username);
            if (!notification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee deleted successful!");
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), notification.getFormattedErrors());

            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            String newUsername = administratorView.getNewUsername();
            String newPassword = administratorView.getNewPassword();
            Boolean changeRole = administratorView.getChangeRole();
            Notification<Boolean> notification = adminManagerService.updateEmployee(username, newUsername, newPassword, changeRole);

            if (!notification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee updated successful!");
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), notification.getFormattedErrors());

            }
        }
    }

    private class ViewAllButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame displayFrame = new JFrame("Employees");
            displayFrame.setLocationRelativeTo(null);
            displayFrame.setSize(500, 500);
            displayFrame.setVisible(true);

            JTextArea displayText = new JTextArea();
            displayText.append(adminManagerService.getAllEmployees().toString());
            displayText.setEditable(false);
            displayFrame.add(displayText);


        }
    }

    private class GenerateReportsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            String date1 = administratorView.getFrom();
            String date2 = administratorView.getUntil();


            Notification<Boolean> notification = generateReportsMySQL.generateReports(date1, date2);
            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), notification.getFormattedErrors());

            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Done reporting!");

            }

        }
    }


    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setVisible(false);
            loginView.setVisible(true);

        }
    }
}
