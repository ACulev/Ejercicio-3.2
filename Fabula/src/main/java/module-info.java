module com.mycompany.fabula {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.fabula to javafx.fxml;
    exports com.mycompany.fabula;
}
