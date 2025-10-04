/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.irr00_group_project;

/**
 * @author 2132389
 * @author 2080338
 */

import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dto.UserRegisterDTO;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.mapper.UserRegisterDTOMapper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;

public class StartPage extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button loginButton = new Button("Log In");
        Button createAccountButton = new Button("Create Account");

        String buttonStyle = "-fx-background-color: white;" +
                "-fx-text-fill: #1e3a8a;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: normal;" +
                "-fx-background-radius: 12;" +
                "-fx-padding: 10 20;" +
                "-fx-cursor: hand;";

        loginButton.setStyle(buttonStyle);
        createAccountButton.setStyle(buttonStyle);
        loginButton.setPrefWidth(400);
        createAccountButton.setPrefWidth(400);

        VBox vbox = new VBox(30, loginButton, createAccountButton);
        vbox.setPadding(new Insets(580, 0, 0, 35));
        vbox.setAlignment(Pos.TOP_CENTER);

        StackPane root = new StackPane(vbox);

        WebView svgView = new WebView();
        try {
            String path = "/images/StartBackground.svg";
            String svgPath = getClass().getResource(path).toExternalForm();
            System.out.println("Loaded SVG: " + svgPath);
            svgView.getEngine().load(svgPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load background SVG.");
        }
        svgView.setContextMenuEnabled(false);
        svgView.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        svgView.setMouseTransparent(true);

        root.getChildren().add(0, svgView);
        svgView.prefWidthProperty().bind(root.widthProperty());
        svgView.prefHeightProperty().bind(root.heightProperty());

        Scene mainScene = new Scene(root, 800, 600);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Main Page with Background");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        loginButton.setOnAction(e -> showLoginPopup(primaryStage));
        createAccountButton.setOnAction(e -> showCreateAccountPopup(primaryStage));
    }
    
    private void applyBaseFieldStyle(Control field) {
        field.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #ccc;
            -fx-border-width: 2;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-padding: 6 8;
            -fx-font-size: 14px;
        """);
    }
    
    private void applyFocusBorderStyle(Control field) {
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle("""
                    -fx-background-color: white;
                    -fx-border-color: #00aaff;
                    -fx-border-width: 2;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    -fx-padding: 6 8;
                    -fx-font-size: 14px;
                """);
            } else {
                field.setStyle("""
                    -fx-background-color: white;
                    -fx-border-color: #ccc;
                    -fx-border-width: 2;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    -fx-padding: 6 8;
                    -fx-font-size: 14px;
                """);
            }
        });
    }

    private void showLoginPopup(Stage owner) {
        Stage popup = new Stage(StageStyle.UTILITY);
        popup.initOwner(owner);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Log In");

        String themeBlue = "#1E40AF";

        //title log in
        Label title = new Label("Log In");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");

        //email
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        TextField emailField = new TextField();
        applyBaseFieldStyle(emailField);
        applyFocusBorderStyle(emailField);

        //password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        PasswordField passwordField = new PasswordField();
        applyBaseFieldStyle(passwordField);
        applyFocusBorderStyle(passwordField);

        //log in button
        Button loginButton = new Button("Log In");
        loginButton.setStyle("""
            -fx-background-color: #1E40AF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-background-radius: 10;
            -fx-padding: 10 36;
            -fx-cursor: hand;
        """);

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.equals("admin") && password.equals("1234")) {
                popup.close();
                User admin = new User("Admin", email, password);
                admin.setBalance(100.0);
                showAdminPage(App.washers);
                return;
            }

            UserDAO userDAO = new UserDAO();
            Optional<User> registeredUser = userDAO.findByEmail(email);

            if (registeredUser.isPresent()) {
                if (registeredUser.get().getPassword().equals(password)) {
                    popup.close();
                    showUserPage(registeredUser.get());
                } else {
                    showAlert("Incorrect password.", popup);
                }
            } else {
                showAlert("Invalid credentials.", popup);
            }
        });

        VBox form = new VBox(8, emailLabel, emailField, passwordLabel, passwordField);
        form.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(20, title, form, loginButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        Scene scene = new Scene(layout, 320, 300);
        popup.setScene(scene);
        popup.show();
    }
    

    private void showCreateAccountPopup(Stage owner) {
        Stage popup = new Stage(StageStyle.UTILITY);
        popup.initOwner(owner);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Create Account");

        String themeBlue = "#1E40AF";

        //title create account
        Label title = new Label("Create Account");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");

        //name
        Label nameLabel = new Label("Full Name:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        TextField nameField = new TextField();
        applyBaseFieldStyle(nameField);
        applyFocusBorderStyle(nameField);

        //email
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        TextField emailField = new TextField();
        applyBaseFieldStyle(emailField);
        applyFocusBorderStyle(emailField);

        //set password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        PasswordField passwordField = new PasswordField();
        applyBaseFieldStyle(passwordField);
        applyFocusBorderStyle(passwordField);
        Label confirmLabel = new Label("Confirm Password:");
        confirmLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + themeBlue + ";");
        PasswordField confirmField = new PasswordField();
        applyBaseFieldStyle(confirmField);
        applyFocusBorderStyle(confirmField);

        //account Button
        Button createButton = new Button("Create Account");
        createButton.setStyle("""
            -fx-background-color: #1E40AF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-background-radius: 10;
            -fx-padding: 10 36;
            -fx-cursor: hand;
        """);

        createButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("All fields are required.", popup);
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Passwords do not match.", popup);
                return;
            }

            UserDAO userDAO = new UserDAO();
            if (userDAO.findByEmail(email).isPresent()) {
                showAlert("An account with this email already exists.", popup);
                return;
            }

            User newUser = new User(name, email, password);
            userDAO.save(newUser);
            popup.close();
            showUserPage(newUser);
        });
        
        VBox form = new VBox(8,
            nameLabel, nameField,
            emailLabel, emailField,
            passwordLabel, passwordField,
            confirmLabel, confirmField
        );
        form.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(20, title, form, createButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        Scene scene = new Scene(layout, 320, 450);
        popup.setScene(scene);
        popup.show();
    }



    private void showUserPage(User user) {
        UserPage userPage = new UserPage(user);
        Scene mainScene = userPage.getMainScene(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Pay2Wash - User");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    private void showAdminPage(List<WasherViewModel> washers) {
        AdminPage adminPage = new AdminPage(washers);
        Scene scene = adminPage.getMainScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pay2Wash - Admin");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    private void showAlert(String message, Stage owner) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.initOwner(owner);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





