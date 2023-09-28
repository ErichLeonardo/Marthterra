module org.Hamm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;

    opens org.Hamm to javafx.fxml;
    exports org.Hamm;
}
