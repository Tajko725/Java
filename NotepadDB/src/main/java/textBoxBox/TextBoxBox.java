package textBoxBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import msgBox.MessageBox;

import java.io.IOException;
import java.net.URL;

public class TextBoxBox {

    public static String resultText;

    public enum TextBoxBoxButtons {
        OK,
        Cancel,
        OKCancel
    }

    public static TextBoxBoxButtons Show(String title, String question, TextBoxBoxButtons buttons) {
        final TextBoxBoxButtons[] result = {TextBoxBoxButtons.OK};
        try {
            URL obj = Main.class.getResource("/textBoxBox/TextBoxBox.fxml");
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
            TextBoxBoxController controller = loader.getController();
            controller.SetQuestionText(question);

            switch (buttons) {
                case OK: {
                    controller.SetSecondButtonText("OK");
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultFirstButton(true);
                    // Ok
                    controller.secondButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.OK;
                        resultText = controller.answerTextField.getText();
                        stage.close();
                    });
                }
                break;
                case Cancel: {
                    controller.SetVisibilityFirstButton(false);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultSecondButton(true);
                    controller.secondButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.Cancel;
                        stage.close();
                    });
                }
                break;
                case OKCancel: {
                    controller.SetFirstButtonText("OK");
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultFirstButton(true);
                    controller.secondButton.setCancelButton(true);

                    // Ok
                    controller.firstButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.OK;
                        stage.close();
                    });

                    // Cancel
                    controller.secondButton.setOnAction(event -> {
                        result[0] = TextBoxBoxButtons.Cancel;
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

    public static TextBoxBoxButtons Show(String title, String question, String readyResultText, TextBoxBoxButtons buttons) {
        final TextBoxBoxButtons[] result = {TextBoxBoxButtons.OK};
        try {
            URL obj = Main.class.getResource("/textBoxBox/TextBoxBox.fxml");
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
            TextBoxBoxController controller = loader.getController();
            controller.SetQuestionText(question);
            controller.answerTextField.setText(readyResultText);

            switch (buttons) {
                case OK: {
                    controller.SetSecondButtonText("OK");
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultFirstButton(true);
                    // Ok
                    controller.firstButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.OK;
                        resultText = controller.answerTextField.getText();
                        stage.close();
                    });
                }
                break;
                case Cancel: {
                    controller.SetVisibilityFirstButton(false);
                    controller.SetVisibilitySecondButton(true);
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultSecondButton(true);
                    controller.secondButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.Cancel;
                        stage.close();
                    });
                }
                break;
                case OKCancel: {
                    controller.SetFirstButtonText("OK");
                    controller.SetSecondButtonText("Cancel");
                    controller.SetDefaultFirstButton(true);
                    controller.secondButton.setCancelButton(true);

                    // Ok
                    controller.firstButton.setOnAction(event -> {
                        if (controller.answerTextField.getText().isEmpty()) {
                            MessageBox.Show("Informacja", "Nazwa nie może być pusta", MessageBox.MessageBoxButtons.OK);
                            return;
                        }
                        result[0] = TextBoxBoxButtons.OK;
                        resultText = controller.answerTextField.getText();
                        stage.close();
                    });

                    // Cancel
                    controller.secondButton.setOnAction(event -> {
                        result[0] = TextBoxBoxButtons.Cancel;
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