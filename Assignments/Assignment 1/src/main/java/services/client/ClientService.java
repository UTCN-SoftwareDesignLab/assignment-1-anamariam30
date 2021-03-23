package services.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    Notification<Boolean> addClient(Client client);

    Notification<Boolean> updateClient(String CNP, String newName, String newAddress, String newCardNo);

    Notification<Client> getClientByCNP(String CNP);

    Notification<Client> getClient(String clientName);

    List<Client> getAllClients();

}
