package services.user.management;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface AdminManagerService {
    Notification<Boolean> createEmployee(String username, String Password);

    Notification<Boolean> deleteEmployee(String name);

    Notification<Boolean> updateEmployee(String username, String newUsername, String newPassword, boolean changeRole);

    List<User> getAllEmployees();
}
