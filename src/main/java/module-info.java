module com.bosonit.project.lotrjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.reactivex.rxjava3;
    requires static lombok;


    opens com.bosonit.project.lotrjfx to javafx.fxml;
    exports com.bosonit.project.lotrjfx;
    exports com.bosonit.project.lotrjfx.core;
    opens com.bosonit.project.lotrjfx.core to javafx.fxml;
    exports com.bosonit.project.lotrjfx.controller;
    opens com.bosonit.project.lotrjfx.controller to javafx.fxml;
    exports com.bosonit.project.lotrjfx.services;
    opens com.bosonit.project.lotrjfx.services to javafx.fxml;
}