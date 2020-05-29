package msgBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;

public class MessageBox {


    public enum MessageBoxButtons{
        OK,
        Cancel,
        Abort,
        OKCancel,
        OkCancelAbort
    }

    public static MessageBoxButtons Show(String title, String text, MessageBoxButtons buttons){
        final MessageBoxButtons[] result = {MessageBoxButtons.OK};
        try{
            URL obj = Main.class.getResource("/msgBox/MessageBox.fxml");
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(obj);
            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setAlwaysOnTop(true);
            stage.setIconified(false);
            MessageBoxController controller = loader.getController();
            controller.SetQuestionText(text);

            switch (buttons) {
                case OK: {
                    controller.SetVisibilityFirstButton(false);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetVisibilityThirdButton(false);
                    controller.SetSecondButtonText("OK");
                    controller.SetDefaultSecondButton(true);
                    // Ok
                    controller.secondButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.OK;
                        stage.close();
                    });
                }
                break;
                case Cancel: {
                    controller.SetVisibilityFirstButton(false);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetVisibilityThirdButton(false);
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultSecondButton(true);
                    controller.secondButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.Cancel;
                        stage.close();
                    });
                }
                break;
                case OKCancel: {
                    controller.SetVisibilityFirstButton(false);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetVisibilityThirdButton(true);
                    controller.SetSecondButtonText("OK");
                    controller.SetThirdButtonText("Cancel");
                    controller.SetDefaultSecondButton(true);
                    controller.secondButton.setCancelButton(true);

                    // Ok
                    controller.secondButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.OK;
                        stage.close();
                    });

                    // Cancel
                    controller.thirdButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.Cancel;
                        stage.close();
                    });
                }
                break;
                case OkCancelAbort: {
                    controller.SetVisibilityFirstButton(true);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetVisibilityThirdButton(true);
                    controller.SetFirstButtonText("OK");
                    controller.SetSecondButtonText("Cancel");
                    controller.SetThirdButtonText("Abort");
                    controller.SetDefaultFirstButton(true);

                    // Ok
                    controller.firstButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.OK;
                        stage.close();
                    });

                    // Cancel
                    controller.secondButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.Cancel;
                        stage.close();
                    });

                    // Abort
                    controller.thirdButton.setOnAction(event -> {
                        result[0] = MessageBoxButtons.Abort;
                        stage.close();
                    });
                }
                break;
            }

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result[0];
    }
}
