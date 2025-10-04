/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.irr00_group_project;



import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dto.WashingMachineDTO;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.security.BCryptPasswordHasher;
import com.mycompany.irr00_group_project.service.BookingService;
import com.mycompany.irr00_group_project.service.UserService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;  
import javafx.scene.layout.Pane;         
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;

/**
 * The BookingPage class handles the booking interface where users can reserve a washing machine,
 * choose additional drying options, see pricing, and make payments.
 * It also handles the countdown timer that expires the booking if not confirmed.
 * @author 2132389
 * @author 2080338
 * @author 2120887
 */
public class BookingPage {

    private boolean dryerSelected = false;
    private int countdownSeconds = 120;
    private Timeline countdownTimeline;

    /**
    * Builds and returns the booking scene for a selected washing machine.
    *
    * @param primaryStage the main application stage
    * @param washer the selected washing machine model
    * @param userPage the user's main page instance
    * @param currentUser the currently logged-in user
    * @return the constructed booking scene
    */
    public Scene getScene(
            Stage primaryStage, 
            WasherViewModel washer, 
            UserPage userPage, 
            User currentUser
    ) {
        VBox capsule = new VBox(35);
        capsule.setAlignment(Pos.TOP_LEFT);
        capsule.setPadding(new Insets(50));
        capsule.setMaxWidth(600);
        capsule.setStyle("-fx-background-color: white; " 
            + "-fx-background-radius: 24; " 
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 6);"
        );

        Text title = new Text("You’ve booked Washer #" + washer.getID());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextAlignment(TextAlignment.LEFT);

        Text countdownText = new Text();
        countdownText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        countdownText.setFill(Color.GRAY);
        countdownText.setTextAlignment(TextAlignment.LEFT);
        updateCountdownLabel(countdownText);

        startCountdown(primaryStage, countdownText, userPage);

        Label priceLabel = new Label("Price: €2.50");
        priceLabel.setFont(Font.font("Arial", 18));

        Label balanceLabel = new Label("Your Balance: €" 
                + String.format("%.2f", currentUser.getBalance()));
        balanceLabel.setFont(Font.font("Arial", 18));

        VBox textBox = new VBox(12, title, countdownText, priceLabel, balanceLabel);
        textBox.setAlignment(Pos.TOP_LEFT);

        Button toggleDryer = new Button("+ Add Drying Program");
        toggleDryer.setMinWidth(300);
        toggleDryer.setStyle("-fx-background-color: #2563eb; " 
            + "-fx-text-fill: white; " 
            + "-fx-font-size: 18px; " 
            + "-fx-font-weight: bold; " 
            + "-fx-background-radius: 10;"
        );

        toggleDryer.setOnAction(e -> {
            dryerSelected = !dryerSelected;
            String drying = "✓ Drying Program Added (€1.00)";
            String addDrying = "+ Add Drying Program";
            toggleDryer.setText(dryerSelected ? drying : addDrying);
            priceLabel.setText("Price: €" + (dryerSelected ? "3.00" : "2.00"));
        });

        toggleDryer.setOnMouseEntered(e -> toggleDryer.setCursor(Cursor.HAND));
        toggleDryer.setOnMouseExited(e -> toggleDryer.setCursor(Cursor.DEFAULT));

        Button pay = new Button("Pay and Start");
        pay.setMinWidth(300);
        pay.setStyle("-fx-background-color: #10b981; " 
            + "-fx-text-fill: white; " 
            + "-fx-font-size: 18px; " 
            + "-fx-font-weight: bold; " 
            + "-fx-background-radius: 10;"
        );

        Button cancel = new Button("Cancel Booking");
        cancel.setMinWidth(170);
        cancel.setStyle("-fx-background-color: transparent;" 
            + "-fx-border-color: #ef4444;" 
            + "-fx-text-fill: #ef4444;" 
            + "-fx-border-width: 2;" 
            + "-fx-font-size: 18px;" 
            + "-fx-font-weight: bold;" 
            + "-fx-background-radius: 10;" 
            + "-fx-border-radius: 10;"
        );

        cancel.setOnMouseEntered(e -> {
            cancel.setStyle("-fx-background-color: #fee2e2;" 
                + "-fx-border-color: #ef4444;" 
                + "-fx-text-fill: #ef4444;" 
                + "-fx-border-width: 2;" 
                + "-fx-font-size: 18px;" 
                + "-fx-font-weight: bold;" 
                + "-fx-background-radius: 10;" 
                + "-fx-border-radius: 10;"
            );
            cancel.setCursor(Cursor.HAND);
        });

        cancel.setOnMouseExited(e -> {
            cancel.setStyle("-fx-background-color: transparent;" 
                + "-fx-border-color: #ef4444;" 
                + "-fx-text-fill: #ef4444;" 
                + "-fx-border-width: 2;" 
                + "-fx-font-size: 18px;" 
                + "-fx-font-weight: bold;" 
                + "-fx-background-radius: 10;" 
                + "-fx-border-radius: 10;"
            );
            cancel.setCursor(Cursor.DEFAULT);
        });

        pay.setOnMouseEntered(e -> pay.setCursor(Cursor.HAND));
        pay.setOnMouseExited(e -> pay.setCursor(Cursor.DEFAULT));

        pay.setOnAction(e -> {
            double cost = dryerSelected ? 3 : 2;
            if (currentUser.getBalance() < cost) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Balance Notice");
                alert.setHeaderText(null);
                alert.setContentText("You do not have enough balance to start the wash.");
                alert.setOnHidden(event -> {
                    countdownTimeline.stop();
                    Scene userScene = userPage.getMainScene(primaryStage);
                    primaryStage.setScene(userScene);
                    primaryStage.setFullScreen(true);
                    primaryStage.setFullScreenExitHint("");
                });
                alert.show();
            } else {
                BookingService bookingService = new BookingService();
                UserService userService;
                userService = new UserService(new UserDAO(), new BCryptPasswordHasher());
                bookingService.SaveBookingService(
                        currentUser.getId(), 
                        washer.getID(), 
                        dryerSelected
                );
                userService.decreaseBalance(currentUser.getId().intValue(), cost);
                washer.setHasDryer(dryerSelected);
                userPage.markWasherBooked(washer, dryerSelected);
                countdownTimeline.stop();
                primaryStage.setScene(new WM().getScene(primaryStage, washer, userPage));
            }
        });

        cancel.setOnAction(e -> {
            countdownTimeline.stop();
            Scene userScene = userPage.getMainScene(primaryStage);
            primaryStage.setScene(userScene);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
        });

        HBox buttonRow = new HBox(24, pay, cancel);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        capsule.getChildren().addAll(textBox, toggleDryer, buttonRow);

        // Backgrund SVG
        StackPane root = new StackPane();

        WebView svgView = new WebView();
        String path = "/images/BackgroundBooking.svg";
        svgView.getEngine().load(getClass().getResource(path).toExternalForm());
        svgView.setContextMenuEnabled(false);
        svgView.setMouseTransparent(true); // So it doesn’t block button clicks

        svgView.prefWidthProperty().bind(root.widthProperty());
        svgView.prefHeightProperty().bind(root.heightProperty());

        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(60));
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(capsule);

        root.getChildren().addAll(svgView, wrapper);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        return scene;
    }

    private void startCountdown(Stage stage, Text label, UserPage userPage) {
        countdownTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                countdownSeconds--;
                updateCountdownLabel(label);
                if (countdownSeconds <= 0) {
                    countdownTimeline.stop();
                    Scene userScene = userPage.getMainScene(stage);
                    stage.setScene(userScene);
                    stage.setFullScreen(true);
                    stage.setFullScreenExitHint("");
                }
            })
        );
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

    private void updateCountdownLabel(Text label) {
        int minutes = countdownSeconds / 60;
        int seconds = countdownSeconds % 60;
        label.setText(String.format("You will lose this booking in %d:%02d", minutes, seconds));
    }
}


