package textBoxBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TextBoxBoxController implements Initializable {

    @FXML
    public Label questionLabel;

    @FXML
    public TextField answerTextField;

    @FXML
    public Button firstButton;

    @FXML
    public Button secondButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void SetQuestionText(String text){
        if(text.equals(""))
            return;

        questionLabel.setText(text);
    }

    public void SetFirstButtonText(String text){
        if(text.equals(""))
            return;

        firstButton.setText(text);
    }

    public void SetSecondButtonText(String text){
        if(text.equals(""))
            return;

        secondButton.setText(text);
    }

    public void SetVisibilityFirstButton(boolean isVisible)
    {
        firstButton.setVisible(isVisible);
    }

    public void SetVisibilitySecondButton(boolean isVisible)
    {
        secondButton.setVisible(isVisible);
    }

    public void SetDefaultFirstButton(boolean isDefault)
    {
        firstButton.setDefaultButton(isDefault);
    }
    public void SetDefaultSecondButton(boolean isDefault)
    {
        secondButton.setDefaultButton(isDefault);
    }
}
