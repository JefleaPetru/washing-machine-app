/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.irr00_group_project;


/**
 * @author 2132389
 * @author 2080338
 */

import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.service.BookingService;
import com.mycompany.irr00_group_project.service.WashingMachineService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;

import java.time.LocalDateTime;
import java.time.Period;

public class WM {

    private int totalSeconds;
    private boolean isChangingSettings = false;

    public Scene getScene(Stage primaryStage, WasherViewModel washer, UserPage userPage) {
        WashingMachineDAO dao = new WashingMachineDAO();
        WashingMachineService washingMachineService = new WashingMachineService(dao);
        Booking currentBooking = washingMachineService.getCurrentBooking(washer.getID());
        totalSeconds = (int)java.time.Duration.between(LocalDateTime.now(), currentBooking.getBookingEnd())
                .getSeconds();

        if (washer.getRemainingSeconds() > totalSeconds || washer.getRemainingSeconds() <= 0) {
            washer.setRemainingSeconds(totalSeconds);
        }

        washer.startTimer(); // Timer runs independently

        VBox capsule = new VBox(30);
        capsule.setPadding(new Insets(40));
        capsule.setAlignment(Pos.TOP_CENTER);
        capsule.setMaxWidth(480);
        capsule.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 5);"
        );

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Button back = new Button("← Back");
        back.setStyle("-fx-font-size: 18px; -fx-padding: 8 20;" +
                "-fx-background-color: transparent; -fx-text-fill: #1f2937;");
        back.setOnAction(e -> userPage.refresh(primaryStage));
        back.setOnMouseEntered(e -> back.setCursor(Cursor.HAND));
        back.setOnMouseExited(e -> back.setCursor(Cursor.DEFAULT));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label("In Use");
        statusBadge.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        statusBadge.setTextFill(Color.WHITE);
        statusBadge.setStyle("-fx-background-color: #1e3a8a; " +
                "-fx-padding: 6 16; -fx-background-radius: 16;");
        header.getChildren().addAll(back, spacer, statusBadge);

        Label washerLabel = new Label("Washer #" + washer.getID());
        washerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        washerLabel.setAlignment(Pos.CENTER);

        HBox machineAndSettings = new HBox(40);
        machineAndSettings.setAlignment(Pos.CENTER);

        String iconPath = "file:src/main/resources/images/EmptyWM.png";
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(160);
        icon.setFitHeight(160);

        VBox settingsBox = new VBox(10);
        settingsBox.setAlignment(Pos.CENTER);

        StackPane tempPane = new StackPane();
        Label tempLabel = new Label("60°");
        ChoiceBox<String> tempChoice = new ChoiceBox<>(FXCollections.observableArrayList("30°", "40°", "60°", "90°"));
        tempChoice.setValue("60°");
        tempPane.getChildren().add(tempLabel);

        StackPane spinPane = new StackPane();
        Label spinLabel = new Label("1200 rpm");
        ChoiceBox<String> spinChoice = new ChoiceBox<>(FXCollections.observableArrayList("800 rpm", "1000 rpm", "1200 rpm", "1400 rpm"));
        spinChoice.setValue("1200 rpm");
        spinPane.getChildren().add(spinLabel);

        StackPane modePane = new StackPane();
        Label modeLabel = new Label("Normal");
        ChoiceBox<String> modeChoice = new ChoiceBox<>(FXCollections.observableArrayList("Normal", "Quick", "Delicate"));
        modeChoice.setValue("Normal");
        modePane.getChildren().add(modeLabel);

        for (Label label : new Label[]{tempLabel, spinLabel, modeLabel}) {
            label.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        }

        settingsBox.getChildren().addAll(tempPane, spinPane, modePane);
        machineAndSettings.getChildren().addAll(icon, settingsBox);

        Text timerText = new Text(formatTime(washer.getRemainingSeconds()));
        timerText.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        StackPane progressStack = new StackPane();
        progressStack.setPrefWidth(320);
        progressStack.setPrefHeight(12);
        progressStack.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 6;");

        Region progressFill = new Region();
        progressFill.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6;");
        progressFill.setPrefHeight(12);
        progressFill.setPrefWidth(0);

        HBox fillWrapper = new HBox(progressFill);
        fillWrapper.setAlignment(Pos.CENTER_LEFT);
        progressStack.getChildren().add(fillWrapper);

        Label phaseLabel = new Label("Washing");
        phaseLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        phaseLabel.setAlignment(Pos.CENTER);

        Button pause = new Button(washer.isPaused() ? "Resume" : "Pause");
        pause.setMinWidth(160);
        pause.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        pause.setStyle("-fx-background-color: #2563eb;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;");
        pause.setOnMouseEntered(e -> pause.setCursor(Cursor.HAND));
        pause.setOnMouseExited(e -> pause.setCursor(Cursor.DEFAULT));

        Button changeSettings = new Button("Change Settings");
        changeSettings.setMinWidth(160);
        changeSettings.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        changeSettings.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: #1e3a8a;" +
                "-fx-text-fill: #1e3a8a;" +
                "-fx-border-width: 2;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;");
        changeSettings.setOnMouseEntered(e -> changeSettings.setCursor(Cursor.HAND));
        changeSettings.setOnMouseExited(e -> changeSettings.setCursor(Cursor.DEFAULT));

        changeSettings.setOnAction(e -> {
            if (!isChangingSettings) {
                isChangingSettings = true;
                washer.pauseTimer();
                tempPane.getChildren().setAll(tempChoice);
                spinPane.getChildren().setAll(spinChoice);
                modePane.getChildren().setAll(modeChoice);
                changeSettings.setText("Confirm");
            } else {
                isChangingSettings = false;
                washer.resumeTimer();
                tempLabel.setText(tempChoice.getValue());
                spinLabel.setText(spinChoice.getValue());
                modeLabel.setText(modeChoice.getValue());
                tempPane.getChildren().setAll(tempLabel);
                spinPane.getChildren().setAll(spinLabel);
                modePane.getChildren().setAll(modeLabel);
                changeSettings.setText("Change Settings");
            }
        });

        pause.setOnAction(e -> {
            if (washer.isPaused()) {
                washer.resumeTimer();
                pause.setText("Pause");
            } else {
                washer.pauseTimer();
                pause.setText("Resume");
            }
        });

        HBox buttons = new HBox(20, pause, changeSettings);
        buttons.setAlignment(Pos.CENTER);

        Text dryingNote = new Text("Drying will start after wash.");
        dryingNote.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        dryingNote.setFill(Color.GRAY);

        capsule.getChildren().addAll(
                header, washerLabel, machineAndSettings,
                timerText, progressStack, phaseLabel,
                buttons, dryingNote
        );

        // Background using SVG
        StackPane root = new StackPane();

        WebView svgView = new WebView();
        String svgPath = "/images/BackgroundWM.svg";
        svgView.getEngine().load(getClass().getResource(svgPath).toExternalForm());
        svgView.setContextMenuEnabled(false);
        svgView.setMouseTransparent(true);
        svgView.prefWidthProperty().bind(root.widthProperty());
        svgView.prefHeightProperty().bind(root.heightProperty());

        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(60));
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(capsule);

        root.getChildren().addAll(svgView, wrapper);

        //Timer to reflect washer state
        Timeline uiTimer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            timerText.setText(formatTime(washer.getRemainingSeconds()));
            double progressValue = 1.0 * (totalSeconds - washer.getRemainingSeconds()) / totalSeconds;
            progressFill.setPrefWidth(320 * progressValue);

            if (washer.getRemainingSeconds() == 0) {
                phaseLabel.setText("Program is over. Pick up your clothes.");
            } else if (washer.hasDryer() && washer.getRemainingSeconds() <= 10 * 60) {
                progressFill.setStyle("-fx-background-color: #f97316; -fx-background-radius: 6;");
                phaseLabel.setText("Drying");
            } else {
                progressFill.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6;");
                phaseLabel.setText("Washing");
            }
        }));
        uiTimer.setCycleCount(Timeline.INDEFINITE);
        uiTimer.play();

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        return scene;
    }

    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}