package org.Hamm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JAXBManager {
    public static void marshal(Object obj, File file) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, file);
    }

    public static Object unmarshal(File file, Class<?> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }

    public static void marshalRooms(List<Room> rooms, File file) throws IOException, JAXBException {
        Rooms roomsObj = new Rooms();
        roomsObj.setRooms(rooms);
        marshal(roomsObj, file);
    }

    public static Rooms unmarshalRooms(File file) throws JAXBException {
        return (Rooms) unmarshal(file, Rooms.class);
    }

    public static void marshalUsers(List<User> users, File file) throws IOException, JAXBException {
        Users usersObj = new Users();
        usersObj.setUsers(users);
        marshal(usersObj, file);
    }

    public static Users unmarshalUsers(File file) throws JAXBException {
        return (Users) unmarshal(file, Users.class);
    }
}
