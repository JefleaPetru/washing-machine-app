/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * @author 2132389
 * @author 2080338
 */

package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.data.FileDb;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.security.BCryptPasswordHasher;
import com.mycompany.irr00_group_project.service.UserService;
import com.mycompany.irr00_group_project.service.WashingMachineService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.scene.Cursor;

public class UserPage {

    private final User currentUser;

    public UserPage(User user) {
        this.currentUser = user;
    }

    public Scene getMainScene(Stage primaryStage) {
        App.loadWashingMachines();
        // Welcome Title
        VBox titleBox = new VBox(12);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(60, 0, 30, 0));

        Text welcome = new Text("Welcome back, " + currentUser.getName() + "!");
        welcome.setFont(Font.font("Arial", 54));
        welcome.setFill(Color.web("#172554"));

        Text subheading = new Text("Ready to Freshen Up? Pick a Washer Below.");
        subheading.setFont(Font.font("Arial", 24));
        subheading.setFill(Color.web("#254fb5"));

        titleBox.getChildren().addAll(welcome, subheading);

        // Washer Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(60);
        grid.setVgap(60);
        grid.setPadding(new Insets(20));

        for (int i = 0; i < App.washers.size(); i++) {
            WasherViewModel washer = App.washers.get(i);
            WashingMachineService washingMachineService = new WashingMachineService(new WashingMachineDAO());
            if (washingMachineService
                    .isWashingMachineBookedByUser(currentUser.getId().intValue(), washer.getID())) {
                washer.setBookedByUser(true);
            } else {
                washer.setBookedByUser(false);
            }

            VBox card = new VBox(18);
            card.setPadding(new Insets(38));
            card.setPrefSize(320, 320);
            card.setAlignment(Pos.TOP_LEFT);
            card.setStyle("-fx-background-color: white;" +
                          "-fx-background-radius: 22;" +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 14, 0, 0, 3);");

            Image washerImage = new Image(getClass().getResource("/images/EmptyWM.png").toExternalForm());
            ImageView washerIcon = new ImageView(washerImage);
            washerIcon.setFitWidth(130);
            washerIcon.setFitHeight(130);

            Text washerLabel = new Text("Washer #" + washer.getID());
            washerLabel.setFont(Font.font("Arial", 30));
            washerLabel.setFill(Color.web("#172554"));

            Text statusLabel = new Text();
            statusLabel.setFont(Font.font("Arial", 24));

            Button actionButton = new Button();
            actionButton.setPrefWidth(300);
            actionButton.setPrefHeight(50);
            actionButton.setFont(Font.font("Arial", 20));
            actionButton.setCursor(Cursor.HAND);
            actionButton.setOnMousePressed(e -> actionButton.setTranslateY(2));
            actionButton.setOnMouseReleased(e -> actionButton.setTranslateY(0));
            if (washer.getStatus().equals("Available")) {
                statusLabel.setText("Available");
                statusLabel.setFill(Color.web("#16a34a"));
                actionButton.setText("Book");
                actionButton.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-background-radius: 10;");
                actionButton.setOnAction(e ->
                    primaryStage.setScene(new BookingPage().getScene(primaryStage, washer, this, currentUser))
                );
            } else if (washer.isBookedByUser() == true) {
                statusLabel.setText("In Use (You)");
                statusLabel.setFill(Color.web("#2563eb"));
                actionButton.setText("View Status");
                actionButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: #white; -fx-background-radius: 10;");
                actionButton.setOnAction(e ->
                    primaryStage.setScene(new WM().getScene(primaryStage, washer, this))
                );
            } else if (washer.getStatus().equals("In Use")) {
                statusLabel.setText("In Use (Another User)");  
                statusLabel.setFill(Color.web("dc2626"));
                actionButton.setText("Disabled");
                actionButton.setDisable(true);
                actionButton.setStyle("-fx-background-color: #e5e7eb; -fx-text-fill: #9ca3af; -fx-background-radius: 10;");
            } else {
                statusLabel.setText("Out of Order");
                statusLabel.setFill(Color.web("#dc2626"));
                actionButton.setText("Disabled");
                actionButton.setDisable(true);
                actionButton.setStyle("-fx-background-color: #e5e7eb; -fx-text-fill: #9ca3af; -fx-background-radius: 10;");
            }

            HBox buttonBox = new HBox(actionButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(35, 0, 0, 0));

            card.getChildren().addAll(washerIcon, washerLabel, statusLabel, buttonBox);
            grid.add(card, i % 2, i / 2);
        }

        // Scrollable Content
        VBox scrollContent = new VBox(20, titleBox, grid);
        scrollContent.setAlignment(Pos.TOP_CENTER);
        scrollContent.setPadding(new Insets(0, 0, 60, 0));
        scrollContent.setStyle("-fx-background-color: transparent;"); //Make VBox transparent

        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;"); // Make ScrollPane transparent

        // Balance + Add Money
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;");
        logoutButton.setCursor(Cursor.HAND);
        logoutButton.setPrefWidth(120);
        logoutButton.setPrefHeight(80);
        logoutButton.setFont(Font.font("Arial", 16));
        logoutButton.setOnAction(e -> {
            try {
                StartPage startPage = new StartPage();
                startPage.start(primaryStage); // Go back to the start screen
                primaryStage.setFullScreen(true); //  Make it full-screen
                primaryStage.setFullScreenExitHint("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        // Balance Text
        Text balanceText = new Text(String.format("Balance: €%.2f", currentUser.getBalance()));
        balanceText.setFont(Font.font("Arial", 20));
        balanceText.setFill(Color.web("#172554"));

        // Add Money Button
        Button addMoneyButton = new Button("Add Money");
        addMoneyButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-background-radius: 8;");
        addMoneyButton.setCursor(Cursor.HAND);
        addMoneyButton.setOnAction(e -> showAddMoneyDialog(primaryStage));
        addMoneyButton.setPrefWidth(120);
        addMoneyButton.setPrefHeight(80);
        addMoneyButton.setFont(Font.font("Arial", 16));

        // Left and Right HBoxes
        HBox leftBox = new HBox(logoutButton);
        leftBox.setAlignment(Pos.BASELINE_LEFT);
        leftBox.setPadding(new Insets(30, 0, 0, 30));

        HBox rightBox = new HBox(20, balanceText, addMoneyButton);
        rightBox.setAlignment(Pos.BASELINE_RIGHT);
        rightBox.setPadding(new Insets(30, 30, 0, 0));

        // Top Bar Layout
        BorderPane topBar = new BorderPane();
        topBar.setLeft(leftBox);
        topBar.setRight(rightBox);

        // Background SVG
        WebView background = new WebView();
        background.setContextMenuEnabled(false);
        background.setMouseTransparent(true);
        background.getEngine().load(getClass().getResource("/images/BackgroundUserPage.svg").toExternalForm());

        background.setPrefWidth(1920);
        background.setPrefHeight(1080);

        StackPane root = new StackPane();

        VBox foregroundContent = new VBox(topBar, scrollPane);
        foregroundContent.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(background, foregroundContent);

        // Auto-resize background
        root.widthProperty().addListener((obs, oldVal, newVal) -> background.setPrefWidth(newVal.doubleValue()));
        root.heightProperty().addListener((obs, oldVal, newVal) -> background.setPrefHeight(newVal.doubleValue()));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        return scene;
    }

    private void showAddMoneyDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add Money");

        Label label = new Label("Enter amount to add:");
        TextField amountField = new TextField();
        Button confirm = new Button("Add");

        Label error = new Label();
        error.setTextFill(Color.RED);
        confirm.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    throw new NumberFormatException();
                //throw an exception when balance is too big
                } if (currentUser.getBalance() + amount > 10000) {
                    throw new IllegalArgumentException();
                }
                UserDAO userDAO = new UserDAO();
                FileDb db = FileDb.getInstance();
                userDAO.increaseBalance(db.findUserById(currentUser.getId()), amount);
                dialog.close();
                refresh(owner);
            } catch (NumberFormatException ex) {
                error.setText("Please enter a valid amount.");
            } catch (IllegalArgumentException ex) {
                error.setText("The total balance cannot be larger than €10 000");
            }
        });

        VBox layout = new VBox(10, label, amountField, confirm, error);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        dialog.setScene(scene);
        dialog.show();
    }

    public void refresh(Stage stage) {
        stage.setScene(getMainScene(stage));
    }

    public void markWasherBooked(WasherViewModel washer, boolean dryer) {
        washer.setStatus("In Use");
        washer.setBookedByUser(true);
        washer.setHasDryer(dryer);
    }
}















