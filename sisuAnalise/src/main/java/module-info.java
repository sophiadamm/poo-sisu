module sisu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens sisu to javafx.fxml;
    exports sisu;
}
