package org.Hamm.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class UserHistory {
    private List<User> userList;

    public UserHistory() {
        userList = new ArrayList<>();
    }

    @XmlElement(name = "user")
    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }
}