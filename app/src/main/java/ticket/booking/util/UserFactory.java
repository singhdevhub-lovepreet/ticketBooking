package ticket.booking.util;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserFactory {

    public static User createUser(String name, String password) {
        String hashedPassword = UserServiceUtil.hashPassword(password);
        return new User(name, password, hashedPassword, new ArrayList<>(), UUID.randomUUID().toString());
    }
}
