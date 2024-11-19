module org.example.softfun_funsoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    requires com.dlsc.formsfx;

    opens org.example.softfun_funsoft to javafx.fxml;
    exports org.example.softfun_funsoft;
}