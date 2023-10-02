module org.Hamm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;

    opens org.Hamm to javafx.fxml;
    exports org.Hamm;
    exports org.Hamm.XML;
    opens org.Hamm.XML to javafx.fxml;
    opens org.Hamm.Controller to javafx.fxml;
    exports org.Hamm.util;
    opens org.Hamm.util to javafx.fxml;
    opens org.Hamm.model to java.xml.bind;


}
