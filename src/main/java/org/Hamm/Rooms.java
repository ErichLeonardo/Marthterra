package org.Hamm;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name= "rooms")
public class Rooms implements java.io.Serializable {
    @XmlElement(name = "room", type=User.class)
    private List<Room> rooms = new ArrayList<>();

    Rooms(){}

    public List<Room> getRooms(){
        return rooms;
    }

    public void setRooms(List<Room> rooms){
        this.rooms = rooms;
    }
}
