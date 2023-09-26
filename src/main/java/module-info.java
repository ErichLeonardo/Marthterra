module org.Hamm {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.Hamm to javafx.fxml;
    exports org.Hamm;
}
