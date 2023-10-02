package org.Hamm.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class UserHistory {
    private List<User> userList;

    public UserHistory() {
        userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    // Agrega un método para obtener la lista de usuarios conectados
    public List<String> getConnectedUsers() {
        List<String> connectedUsers = new ArrayList<>();
        for (User user : userList) {
            connectedUsers.add(user.getName());
        }
        return connectedUsers;
    }
}