package org.Hamm;

public class Room {
    private String name;
    User users; //poner una lista en vez de esta mierda

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

}
