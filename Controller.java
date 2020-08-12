package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField regionField;

    @FXML
    private TextArea dataField;

    @FXML
    void pressFind(ActionEvent event) throws IOException {
        statsReaderClass.validateRegion(regionField.getText());
        statsReaderClass.setData(usernameField.getText(), regionField.getText(), dataField);
        statsReaderClass.updateData();
        statsReaderClass.getDataFromWebsite();
    }

    private StatsReaderClass statsReaderClass;

    public void setStatsReaderClass(StatsReaderClass _statsReaderClass){
        statsReaderClass = _statsReaderClass;
    }
}
