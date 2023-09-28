package org.Hamm.XML;
import org.Hamm.XML.User;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name= "users")
public class Users implements java.io.Serializable {
    @XmlElement(name = "user", type= User.class)
    private List<User> users = new ArrayList<>();

    public Users(){}

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users){
        this.users = users;
    }
}
