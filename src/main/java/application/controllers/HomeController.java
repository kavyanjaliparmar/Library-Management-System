package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.time.LocalDateTime;

public class HomeController {
    @FXML private Label userLabel;
    @FXML private Label totalBooksLabel;
    @FXML private Label issuedBooksLabel;
    @FXML private Label reservationsLabel;
    @FXML private StackPane contentArea;
    @FXML private TableView<RecentActivity> recentActivitiesTable;
    @FXML private TableColumn<RecentActivity, String> dateColumn;
    @FXML private TableColumn<RecentActivity, String> activityColumn;
    @FXML private TableColumn<RecentActivity, String> bookColumn;
    @FXML private TableColumn<RecentActivity, String> userColumn;

    private String currentUsername;
    private String currentRole;

    @FXML
    public void initialize() {
        // Initialize table columns
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        activityColumn.setCellValueFactory(cellData -> cellData.getValue().activityProperty());
        bookColumn.setCellValueFactory(cellData -> cellData.getValue().bookProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());

        // Load dummy data for demonstration
        loadDummyData();
    }

    public void initData(String username, String role) {
        this.currentUsername = username;
        this.currentRole = role;
        userLabel.setText("Welcome, " + username + " (" + role + ")");
        
        // Update dashboard based on role
        updateDashboard();
    }

    private void updateDashboard() {
        // TODO: Implement actual data fetching from your backend
        totalBooksLabel.setText("150");
        issuedBooksLabel.setText("45");
        reservationsLabel.setText("12");
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            // Get the stage
            Stage stage = (Stage) userLabel.getScene().getWindow();
            
            // Create the login scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            
            // Set the scene
            stage.setScene(scene);
            stage.setTitle("Library Management System - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading login page");
        }
    }

    @FXML
    private void showDashboard() {
        // Already showing dashboard
    }

    @FXML
    private void showBooks() {
        // TODO: Implement books view
        showNotImplemented("Books Management");
    }

    @FXML
    private void showTransactions() {
        // TODO: Implement transactions view
        showNotImplemented("Issue/Return System");
    }

    @FXML
    private void showReservations() {
        // TODO: Implement reservations view
        showNotImplemented("Reservations Management");
    }

    @FXML
    private void showCategories() {
        // TODO: Implement categories view
        showNotImplemented("Categories Management");
    }

    private void showNotImplemented(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText(feature + " will be implemented in the next version.");
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDummyData() {
        ObservableList<RecentActivity> activities = FXCollections.observableArrayList(
            new RecentActivity("2024-03-10 14:30", "Book Issued", "Java Programming", "John Doe"),
            new RecentActivity("2024-03-10 15:45", "Book Returned", "Python Basics", "Jane Smith"),
            new RecentActivity("2024-03-10 16:20", "Reservation", "Data Structures", "Mike Johnson")
        );
        recentActivitiesTable.setItems(activities);
    }

    // Inner class for recent activities table
    public static class RecentActivity {
        private final javafx.beans.property.SimpleStringProperty date;
        private final javafx.beans.property.SimpleStringProperty activity;
        private final javafx.beans.property.SimpleStringProperty book;
        private final javafx.beans.property.SimpleStringProperty user;

        public RecentActivity(String date, String activity, String book, String user) {
            this.date = new javafx.beans.property.SimpleStringProperty(date);
            this.activity = new javafx.beans.property.SimpleStringProperty(activity);
            this.book = new javafx.beans.property.SimpleStringProperty(book);
            this.user = new javafx.beans.property.SimpleStringProperty(user);
        }

        public javafx.beans.property.StringProperty dateProperty() { return date; }
        public javafx.beans.property.StringProperty activityProperty() { return activity; }
        public javafx.beans.property.StringProperty bookProperty() { return book; }
        public javafx.beans.property.StringProperty userProperty() { return user; }
    }
} 