package FileChooserFromDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainModel;
import main.NotepadItem;
import msgBox.MessageBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FileChooserFromDatabaseController implements Initializable {

    @FXML
    public ListView<String> fileNames_lv;

    @FXML
    private Label lpFiles_lb;

    @FXML
    private Button load_btn;
    @FXML
    private Button delete_btn;

    private MainModel mainModel = new MainModel();
    private ArrayList<NotepadItem> items = new ArrayList<>();
    public boolean isLoadOrDeleteClick;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Load();

            // dodanie eventu / nasłuchu do momentu zmiany zaznaczonego elementu w listView i ustawienie liczby zaznaczonych elementów w Label
            fileNames_lv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> lpFiles_lb.setText(String.valueOf(fileNames_lv.getSelectionModel().getSelectedItems().size())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Load() throws Exception {
        fileNames_lv.getItems().clear();
        items = (ArrayList<NotepadItem>) mainModel.getNotepadItems(); //ArrayList<NotepadItem> items = (ArrayList<NotepadItem>) mainModel.getNotepadItems();
        fileNames_lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if (items != null && items.size() > 0) {
            for (NotepadItem item : items) {
                fileNames_lv.getItems().add(item.getName());
            }
        }
    }

    @FXML
    private  void LoadAndAdd(ActionEvent event) {
       // Load();
        isLoadOrDeleteClick = true;
        Stage stage = (Stage)load_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void DeleteItems(ActionEvent event) throws SQLException {
        isLoadOrDeleteClick = true;
        ArrayList<NotepadItem> _items = new ArrayList<>();
        for(int i=0;i<fileNames_lv.getSelectionModel().getSelectedItems().size();i++) {
            for (NotepadItem item : items) {
                if (fileNames_lv.getSelectionModel().getSelectedItems().get(i).equalsIgnoreCase(item.getName())) {
                    _items.add(item);
                    break;
                }
            }
        }
        if(mainModel.DeleteItems(_items)) {
            MessageBox.Show("Informacja", "Usuwanie zakończone powodzeniem", MessageBox.MessageBoxButtons.OK);

            while (fileNames_lv.getSelectionModel().getSelectedItems().size()>0)
            {
                int index = fileNames_lv.getSelectionModel().getSelectedItems().size()-1;
                String item = fileNames_lv.getSelectionModel().getSelectedItems().get(index);
                for(int i=0;i<fileNames_lv.getItems().size();i++)
                {
                    if(fileNames_lv.getItems().get(i).equalsIgnoreCase(item))
                    {
                        fileNames_lv.getItems().remove(i);
                        break;
                    }
                }
                //fileNames_lv.getSelectionModel().getSelectedItems().remove(index);
            }
        //    fileNames_lv.getItems().removeAll(fileNames_lv.getSelectionModel().getSelectedItems());
        }
    }

    public ArrayList<String> SelectedItems() {
        ArrayList<String> str = new ArrayList<>();
        if(fileNames_lv != null && fileNames_lv.getSelectionModel().getSelectedItems().size() >0)
            str.addAll(fileNames_lv.getSelectionModel().getSelectedItems());

        return str;
    }
}
