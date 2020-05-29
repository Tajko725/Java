package msgBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageBoxController implements Initializable {

    @FXML
    private Label questionText_lb;

    @FXML
    public Button firstButton;

    @FXML
    public Button secondButton;

    @FXML
    public Button thirdButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void SetQuestionText(String text){
        if(text.equals(""))
            return;

        questionText_lb.setText(text);
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

    public void SetThirdButtonText(String text){
        if(text.equals(""))
            return;

        thirdButton.setText(text);
    }

    public void SetVisibilityFirstButton(boolean isVisible)
    {
        firstButton.setVisible(isVisible);
    }

    public void SetVisibilitySecondButton(boolean isVisible)
    {
        secondButton.setVisible(isVisible);
    }

    public void SetVisibilityThirdButton(boolean isVisible)
    {
        thirdButton.setVisible(isVisible);
    }

    public void SetDefaultFirstButton(boolean isDefault)
    {
        firstButton.setDefaultButton(isDefault);
    }

    public void SetDefaultSecondButton(boolean isDefault)
    {
        secondButton.setDefaultButton(isDefault);
    }

    public void SetDefaultThirdButton(boolean isDefault)
    {
        thirdButton.setDefaultButton(isDefault);
    }
}
