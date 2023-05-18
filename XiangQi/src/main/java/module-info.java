module cz.cvut.fel.strobad1.xiangqi{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.logging;


    opens cz.cvut.fel.strobad1.xiangqi.controller to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports cz.cvut.fel.strobad1.xiangqi.controller;
    exports cz.cvut.fel.strobad1.xiangqi.model to javafx.graphics, javafx.fxml;
//
    opens images;
    opens scenes.formatting;
    opens scenes;
}

