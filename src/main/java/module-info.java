module org.Hamm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;

    opens org.Hamm to javafx.fxml;
    exports org.Hamm;
    exports org.Hamm.XML;
    opens org.Hamm.XML to javafx.fxml;
    exports org.Hamm.model;
    opens org.Hamm.model to javafx.fxml;
}
