package application.controllers;

import com.library.management.model.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(
            Role.ROLE_ADMIN.getDisplayName(),
            Role.ROLE_STUDENT.getDisplayName()
        );
        roleComboBox.setValue(Role.ROLE_STUDENT.getDisplayName());
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }

        // TODO: Implement actual authentication logic with your backend
        if (authenticateUser(username, password, role)) {
            try {
                // Load the home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
                Parent root = loader.load();
                
                HomeController homeController = loader.getController();
                homeController.initData(username, role);

                // Get the stage from the event source
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                
                // Create the home scene
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                
                // Set the scene
                stage.setScene(scene);
                stage.setTitle("Library Management System - Home");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error loading home page");
            }
        } else {
            showError("Invalid username or password");
        }
    }

    private boolean authenticateUser(String username, String password, String role) {
        // TODO: Implement actual authentication logic with your backend
        // This is just a placeholder that always returns true
        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
} 