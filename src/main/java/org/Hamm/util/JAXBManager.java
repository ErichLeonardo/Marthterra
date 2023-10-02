package org.Hamm.util;

import org.Hamm.model.User;
import org.Hamm.model.UserHistory;

import javax.xml.bind.*;
import java.io.File;

public class JAXBManager {
    private static final String USER_FILENAME = "user.xml"; // Nombre del archivo XML para el usuario actual
    private static final String USER_HISTORY_FILENAME = "user_history.xml";

    public static User readUser() {
        try {
            File file = new File(USER_FILENAME);
            JAXBContext context = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (User) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeUser(User user) {
        try {
            File file = new File(USER_FILENAME);
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(user, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }



    public static void writeUserHistory(UserHistory userHistory) {
        try {
            File file = new File(USER_HISTORY_FILENAME);
            JAXBContext context = JAXBContext.newInstance(UserHistory.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(userHistory, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static UserHistory readUserHistory() {
        try {
            File file = new File(USER_HISTORY_FILENAME);

            // Verifica si el archivo existe y no está vacío antes de intentar leerlo
            if (!file.exists() || file.length() == 0) {
                return new UserHistory(); // Devuelve una instancia vacía si el archivo no existe o está vacío
            }

            JAXBContext context = JAXBContext.newInstance(UserHistory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (UserHistory) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
