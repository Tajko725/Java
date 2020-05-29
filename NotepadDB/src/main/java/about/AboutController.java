package about;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import msgBox.MessageBox;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private Hyperlink email_hl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(email_hl != null)
            email_hl.setText("kursocenter@gmail.com");
    }

    @FXML
    private void CopyEmail(){
        StringSelection stringSelection = new StringSelection(email_hl.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
        MessageBox.Show("Informacja", "E-mail skopiowany do schowka", MessageBox.MessageBoxButtons.OK);
    }
}
