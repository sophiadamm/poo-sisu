module sisu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.base;
    requires javafx.graphics;

    opens sisu to javafx.fxml;
    exports sisu;
}
