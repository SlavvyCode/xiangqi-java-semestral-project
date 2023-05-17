module cz.cvut.fel.strobad1.XiangQi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.logging;




    exports cz.cvut.fel.strobad1.XiangQi;
    opens cz.cvut.fel.strobad1.XiangQi to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens cz.cvut.fel.strobad1.XiangQi.controller to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens cz.cvut.fel.strobad1.XiangQi.view to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports cz.cvut.fel.strobad1.XiangQi.controller;
    exports cz.cvut.fel.strobad1.XiangQi.model to javafx.graphics, javafx.fxml;

    opens images;
    opens scenes.formatting;
    opens scenes;
}

