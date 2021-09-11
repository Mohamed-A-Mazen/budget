package sample;

import budget.*;
import com.aspose.words.Document;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private TextField companyName;
    @FXML
    private TextField year;
    @FXML
    private TextField capital;
    @FXML
    private TextField profits;
    @FXML
    private Button createButton;
    @FXML
    private CheckBox checkBox;

    public void initialize() {
        createButton.setDisable(true);
        profits.setDisable(true);
        companyName.setPromptText("company name");
        year.setPromptText("year");
        profits.setPromptText("profits");
        capital.setPromptText("capital");
        checkBox.setContentDisplay(ContentDisplay.RIGHT);
    }

    public void checkBoxClicked() {
        //if the checkBox is selected we activate the profits textField
        if (checkBox.isSelected()) {
            profits.setDisable(false);
            //and disable the create button to force the user to write some in the profits textField
            if (!createButton.isDisabled()) {
                createButton.setDisable(true);
            }
        } else {
            //if the box is not selected we deactivate the profits
            profits.setDisable(true);
            //and call keyPressed to activate the create button if the other textFields are not blank
            keyPressed();
        }
    }

    public void keyPressed() {
        //checking if the text fields are blank or not and if not we activate the create button
        if ((!companyName.getText().isBlank()) && (!capital.getText().isBlank()) && (!year.getText().isBlank()) && (!checkBox.isSelected())) {
            createButton.setDisable(false);
        } else if ((!companyName.getText().isBlank()) && (!capital.getText().isBlank()) && (!year.getText().isBlank()) &&
                (!profits.getText().isBlank()) && (checkBox.isSelected())) {
            createButton.setDisable(false);
        } else {
            createButton.setDisable(true);
        }
    }

    public void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.setTitle("exit");
        alert.setHeaderText("confirmation");
        alert.setContentText("do you want to exit the app");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void createButtonClicked() throws Exception {
        Budget budget = null;
        BudgetDoc budgetDoc;
        //getting information from text fields and creating the budget object depending on the text fields content
        if (checkBox.isSelected()) {
            switch (Integer.parseInt(capital.getText())) {
                case 100000:
                    budget = new Budget100000(year.getText(), companyName.getText(),
                            Integer.parseInt(profits.getText()));
                    break;
                case 30000:
                    budget = new Budget30000(year.getText(), companyName.getText(),
                            Integer.parseInt(profits.getText()));
                    break;
                case 15000:
                    budget = new Budget15000(year.getText(), companyName.getText(),
                            Integer.parseInt(profits.getText()));
                    break;
            }
        } else{
            switch (Integer.parseInt(capital.getText())) {
                case 100000:
                    budget = new Budget100000(year.getText(), companyName.getText());
                    break;
                case 30000:
                    budget = new Budget30000(year.getText(), companyName.getText());
                    break;
                case 15000:
                    budget = new Budget15000(year.getText(), companyName.getText());
                    break;
            }
        }
        budgetDoc = new BudgetDoc(budget);
        File docxFile = budgetDoc.createDoc();
        //create a dialog to check whether the user wants to print the document immediately or wants to modify it furthermore
        ButtonType print = new ButtonType("print", ButtonBar.ButtonData.OK_DONE);
        ButtonType openInChooser = new ButtonType("open in word", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "press print to print document immediately \n, or press open in word for more custom options ",
                print,
                openInChooser);
        alert.setResizable(true);
        alert.setTitle("print");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()&&result.get()==print) {
            //printing document using the aspose api
            Document document = new Document(docxFile.getName());
            document.print();
        }else if (result.isPresent()&&result.get()==openInChooser){
            //opening the document using the system favourite app
            editFile(docxFile);
        }
    }
    public boolean editFile(final File file) {
        //if desktop is not supported return false
        if (!Desktop.isDesktopSupported()) {
            return false;
        }

        Desktop desktop = Desktop.getDesktop();
        //if the editing file is not supported return false
        if (!desktop.isSupported(Desktop.Action.EDIT)) {
            return false;
        }

        try {
            //edit file
            desktop.edit(file);
        } catch (IOException e) {
            // Log an error
            return false;
        }

        return true;
    }
}








