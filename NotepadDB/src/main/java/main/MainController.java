package main;

import FileChooserFromDatabase.FileChooserFromDatabaseController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import msgBox.MessageBox;
import textBoxBox.TextBoxBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private MenuItem selectAll_edit_mi;
    @FXML
    private MenuItem aboutApp_help_mi;
    @FXML
    private CheckMenuItem wrapText_cmi;

    @FXML
    private TabPane tabPane_tp;

    private MainModel mainModel = new MainModel();
    private NotepadItem selectedItem;
    private String copyiedOrCuttedText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void New(ActionEvent event){
        if(tabPane_tp== null)
            return;

        Integer nr = getNewFreeTabNumber();
        addNewTab(nr,"Nowy "+nr, "");
    }

    private Integer getNewFreeTabNumber(){
        Integer nr = 0;
        NavigableSet<Integer> set = new TreeSet<>();
        for(int i = 0; i< tabPane_tp.getTabs().size(); i++)// .size()+1;
        {
            //nr = (Integer)i.next();
            String[] tab = tabPane_tp.getTabs().get(i).getId().split("_");
            if(tab.length == 2)
                nr = Integer.parseInt(tab[1]);

            if(nr != -1) {
                set.add(nr);
                continue;
            }
            nr = -1;
        }

        // TODO do ogarniecia numeracje
        // tutaj do rozkminienia jak sprawdzic kolejny wolny numer w kolekcji jesli usunieto np masz kolekcje 1,2,3,4,5 i usuniesz 3 to zostanie 1,2,4,5 wiec kolejny element bedzie mial numer
        // 3, jaja sie robia jesli ma sie np. 1,2,3,4,5,6,7 i usunie sie 1,2,3,4,5, zostanie 6,7 i potem chce sie dodac kolejne to zamiast dac 1 potem 2,3,4,5 to ustawia od razu 8,9,10 itd.
        // czyli trzeba przed albo po 'firstMissingNumber' sprawdzic czy ten wyszukany numer jest wiekszy niz wartosc minimalna w kolekcji 'set' oraz czy pierwszy element w kolekcji jest
        // np. <= 1 lub co w podobie, cholewka wie xd
        // CHOCIAŻ obecnie nawet śmiga po sprawdzeniu kasacji początkowej np. w kolekcji 1,2,3,4,5,6,7,8,9. Do przetestowania jeszcze
        if(firstMissingNumber(set) != null)
            nr = firstMissingNumber(set);
        else if(set.size() > 0) {
            Integer min = Collections.min(set);
            if(min >1 && set.first() >1)
                nr = 1;
            else
                nr = Collections.max(set) + 1;
        }
        if(nr != null && nr <= 0)
            nr++;

        return nr;
    }

    @FXML
    private void  Open(ActionEvent event) throws Exception {
        if(tabPane_tp ==null)
            return;

        URL obj = Main.class.getResource("/FileChooserFromDatabase/FileChooserFromDatabase.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(obj);
        Parent root = loader.load();
        stage.setTitle("Pliki z bazy danych");
        stage.setScene(new Scene(root));
        FileChooserFromDatabaseController controller = loader.getController();
        stage.showAndWait();
        ObservableList<String> selectedItems = controller.fileNames_lv.getSelectionModel().getSelectedItems();

        if(selectedItems != null && selectedItems.size() >0)
        {
            for (String s : selectedItems) {
                NotepadItem item = mainModel.getNotepadItem(s);
                if (item != null) {
                    addNewTab(item.getNr(), item.getName(), item.getText());//"tab_" + nr, "Nowy " + nr);
                }
            }
        }
    }

    @FXML
    private void Save(ActionEvent event) throws Exception {
        save();
    }

    @FXML
    private void SaveAs(ActionEvent event) throws Exception {
        if(tabPane_tp.getTabs().size()>0) {
            selectedItem = mainModel.getNotepadItem(tabPane_tp.getSelectionModel().getSelectedItem().getText());
            if(saveAs(selectedItem))
                tabPane_tp.getSelectionModel().getSelectedItem().setText(selectedItem.getName());
        }
    }

    @FXML
    private void Close(ActionEvent event){
        ((Stage)tabPane_tp.getScene().getWindow()).close();
    }

    @FXML
    private void Cut(ActionEvent event){
        TextArea ta = (TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent();
        if(ta == null)
            return;

        copyiedOrCuttedText = ta.getText();
        ta.setText("");

        if(selectedItem != null)
            selectedItem.setText(copyiedOrCuttedText);
    }

    @FXML
    private void Copy(ActionEvent event){
        TextArea ta = (TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent();
        if(ta == null)
            return;

        copyiedOrCuttedText = ta.getText();

        if(selectedItem != null)
            selectedItem.setText(copyiedOrCuttedText);
    }

    @FXML
    private void Paste(ActionEvent event) {

        if(copyiedOrCuttedText == null)
            return;

        TextArea ta = (TextArea) tabPane_tp.getSelectionModel().getSelectedItem().getContent();
        if (ta == null)
            return;

        ta.setText(ta.getText() + copyiedOrCuttedText);

        if (selectedItem != null)
            selectedItem.setText(copyiedOrCuttedText);
    }

    @FXML
    private void SelectAll(ActionEvent event){
        TextArea ta = (TextArea) tabPane_tp.getSelectionModel().getSelectedItem().getContent();
        ta.selectAll();
    }

    @FXML
    private void IsWrapText()
    {
        wrapText_cmi.setSelected(wrapText_cmi.isSelected());
        ((TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent()).setWrapText(wrapText_cmi.isSelected());
        System.out.println(((TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent()).isWrapText());
    }

    @FXML
    private void About() throws IOException {
        URL obj = Main.class.getResource("/about/About.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(obj);
        Parent root = loader.load();
        stage.setTitle("O Aplikacji");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private boolean save() throws Exception {
        String selectedItemText = "";
        if(tabPane_tp.getTabs().size()>0) {
            selectedItem = mainModel.getNotepadItem(tabPane_tp.getSelectionModel().getSelectedItem().getText());
        }

        if(selectedItem != null) {
            NotepadItem oryginalItem = selectedItem;
            TextArea ta = (TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent();
            if(ta != null)
                selectedItem.setText(ta.getText());
            MessageBox.MessageBoxButtons result = MessageBox.Show("Informacja", "Czy zapisać zmiany?", MessageBox.MessageBoxButtons.OKCancel);
            if (result == MessageBox.MessageBoxButtons.OK) {
                if (mainModel.UpdateNotepadItem(oryginalItem, selectedItem)) {
                    MessageBox.Show("Informacja", "Aktualizacja zakończona powodzeniem", MessageBox.MessageBoxButtons.OK);
                    if (ta != null) {
                        ta.setText(selectedItem.getText());
                    }
                }
            }
        }
        else {
            String name = tabPane_tp.getSelectionModel().getSelectedItem().getText();
            String text = ((TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent()).getText();
            NotepadItem item = new NotepadItem(mainModel.getMaxNr()+1, name, text);
            saveAs(item);
        }
        return  false;
    }

    private boolean saveAs(NotepadItem item) throws SQLException {
        if(item == null)
            return  false;

        if(TextBoxBox.Show("Inormacja", "Nazwa zakładki:", item.getName(), TextBoxBox.TextBoxBoxButtons.OKCancel) == TextBoxBox.TextBoxBoxButtons.OK)
        {
            String newName = TextBoxBox.resultText;
            if(newName.isEmpty())
                return  false;

            TextArea ta = (TextArea)tabPane_tp.getSelectionModel().getSelectedItem().getContent();
            if(ta != null && !ta.getText().equalsIgnoreCase(item.getText()))
                item.setText(ta.getText());

            if(!item.getName().equalsIgnoreCase(newName)) {
                item.setName(newName);
                tabPane_tp.getSelectionModel().getSelectedItem().setText(newName);
            }
            return mainModel.AddNotepadItem(item);
        }

        return  false;
    }

    private void addNewTab(Integer id, String tabText, String text)
    {
        if(tabPane_tp == null)
            return;

        Tab tab = new Tab();
        tab.setText(tabText);
        tab.setId("id_"+id);
        boolean isExist = false;

        if(tabPane_tp.getTabs().size() >0) {
            for (int i = 0;i<tabPane_tp.getTabs().size();i++)
            {
                if(tabPane_tp.getTabs().get(i).getText().contentEquals(tabText))
                {
                    TextArea ta = (TextArea)tabPane_tp.getTabs().get(i).getContent();
                    if(ta != null) {
                        ta.setText(text);
                        return;
                    }
                }
            }
        }

        TextArea ta = new TextArea();
        ta.setId("textArea_" + id);
        ta.setText(text);
        tab.setContent(ta);
        tab.setOnClosed(event -> {
            try  {
                NotepadItem item = mainModel.getNotepadItem(tab.getText());
                if(item != null)
                {
                    String text1 = ((TextArea)tab.getContent()).getText();
                    if(!item.getText().equalsIgnoreCase(text1)) {
                        if(MessageBox.Show("Informacja", "Nadpisać zmiany?", MessageBox.MessageBoxButtons.OKCancel) == MessageBox.MessageBoxButtons.OK)
                            mainModel.UpdateNotepadItem(item, new NotepadItem(item.getNr(), item.getName(), text1));
                    }
                }else
                {
                    if(MessageBox.Show("Informacja", "Czy zapisać plik?", MessageBox.MessageBoxButtons.OKCancel) == MessageBox.MessageBoxButtons.OK){
                        if(TextBoxBox.Show("Informacja", "Nazwa pliku:", TextBoxBox.TextBoxBoxButtons.OKCancel) == TextBoxBox.TextBoxBoxButtons.OK)
                            mainModel.AddNotepadItem(new NotepadItem(mainModel.getMaxNr(), tab.getText(), ((TextArea)tab.getContent()).getText()));
                    }
                }
            }catch (Exception ignored){

            }
        });

        tabPane_tp.getTabs().add(tab);
    }

    private void addNewTab(String name){
        if(tabPane_tp == null)
            return;

        Tab tab = new Tab();
        tab.setText(name);
        tab.setId("id_"+name);

        TextArea ta = new TextArea();
        ta.setId("textArea_" + name);
        tab.setContent(ta);
        tabPane_tp.getTabs().add(tab);
    }

    private Integer firstMissingNumber(Set<Integer> set) {
        if(set.size() <= 1) return null;
        BitSet bs = new BitSet();
        set.forEach(bs::set);
        int firstPresent = bs.nextSetBit(0), firstAbsent = bs.nextClearBit(firstPresent);
        return firstAbsent-firstPresent == set.size()? null: firstAbsent;
    }
}
