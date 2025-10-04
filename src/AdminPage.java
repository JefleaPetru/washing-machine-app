package com.mycompany.irr00_group_project;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.data.FileDb;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import com.mycompany.irr00_group_project.entity.enums.Status;
import com.mycompany.irr00_group_project.service.WashingMachineService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.time.Instant;
import java.util.List;

/**
 * Represents the admin interface for managing washing machines.
 * Allows status toggling, stopping/resuming machines, and viewing camera feed.
 * @author 2132389
 * @author 2080338
 * @author 2120887
 */
public class AdminPage {

    private final List<WasherViewModel> washers;
    private long appStartEpoch = Instant.now().getEpochSecond();

    /**
    * Constructs the AdminPage with a list of washer view models.
    *
    * @param washers the list of washers to be managed by the admin
    */
    public AdminPage(List<WasherViewModel> washers) {
        this.washers = washers;
    }

    /**
    * Builds and returns the main admin dashboard scene.
    *
    * @param primaryStage the primary application stage
    * @return the admin dashboard scene
    */
    public Scene getMainScene(Stage primaryStage) {
        App.loadWashingMachines();
        // Title
        VBox titleBox = new VBox(12);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(30, 0, 60, 0));

        Text welcome = new Text("Admin Dashboard");
        welcome.setFont(Font.font("Arial", 54));
        welcome.setFill(Color.web("#172554"));

        Text subtitle = new Text("Manage All Washing Machines");
        subtitle.setFont(Font.font("Arial", 24));
        subtitle.setFill(Color.web("#254fb5"));

        titleBox.getChildren().addAll(welcome, subtitle);

        // Washer Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(60);
        grid.setVgap(60);
        grid.setPadding(new Insets(20));

        for (WasherViewModel washer : washers) {
            VBox card = new VBox(18);
            card.setPadding(new Insets(30));
            card.setPrefSize(320, 320);
            card.setAlignment(Pos.TOP_LEFT);
            card.setStyle("-fx-background-color: white;"
                        + "-fx-background-radius: 22;"
                        + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 14, 0, 0, 3);");

            Image washerImage = new Image(
                    getClass()
                        .getResource("/images/EmptyWM.png")
                        .toExternalForm()
            );
            ImageView washerIcon = new ImageView(washerImage);
            washerIcon.setFitWidth(120);
            washerIcon.setFitHeight(120);

            Text washerLabel = new Text(
                    "Washer #" + washer.getID()
            );
            washerLabel.setFont(Font.font("Arial", 26));
            washerLabel.setFill(Color.web("#172554"));

            Text statusLabel = new Text();
            statusLabel.setFont(Font.font("Arial", 20));
            updateStatusLabel(washer, statusLabel);

            // Buttons
            Button toggleStatusButton = new Button();
            toggleStatusButton.setFont(Font.font("Arial", 16));
            toggleStatusButton.setPrefWidth(260);
            toggleStatusButton.setCursor(javafx.scene.Cursor.HAND);

            Button stopResumeButton = new Button();
            stopResumeButton.setFont(Font.font("Arial", 16));
            stopResumeButton.setPrefWidth(260);
            stopResumeButton.setCursor(javafx.scene.Cursor.HAND);

            // Logic for button states
            updateButtons(
                    washer,
                    toggleStatusButton,
                    stopResumeButton,
                    statusLabel,
                    primaryStage
            );

            toggleStatusButton.setOnAction(e -> {
                if (washer.getStatus().equals("Out of Order")) {
                    washer.setStatus("Available");
                    FileDb db = FileDb.getInstance();
                    db.findWashingMachineById(washer.getID()).setStatus(Status.OUT_OF_ORDER);
                    FileDb.getInstance().updateWashingMachineStatus(FileDb.getInstance().
                            findWashingMachineById(washer.getID()), Status.NOT_STARTED);
                } else {
                    washer.setStatus("Out of Order");
                    FileDb.getInstance()
                            .updateWashingMachineStatus(
                                FileDb.getInstance()
                                    .findWashingMachineById(washer.getID()),
                                Status.UNAVAILABLE
                            );
                }
                primaryStage.setScene(getMainScene(primaryStage));
            });

            stopResumeButton.setOnAction(e -> {
                if (washer.getStatus().equals("In Use")) {
                    washer.setStatus("Stopped");
                    //deletes current booking and thus hard stops
                    WashingMachineDAO washingMachineDAO = new WashingMachineDAO();
                    WashingMachineService washingMachineService;
                    washingMachineService = new WashingMachineService(washingMachineDAO);
                    FileDb.getInstance()
                            .delete(
                              washingMachineService.getCurrentBooking(washer.getID())
                            );
                } else if (washer.getStatus().equals("Stopped")) {
                    washer.setStatus("In Use");
                }
                primaryStage.setScene(getMainScene(primaryStage));
            });

            VBox buttonBox = new VBox(10, toggleStatusButton, stopResumeButton);
            buttonBox.setAlignment(Pos.CENTER);

            card.getChildren().addAll(washerIcon, washerLabel, statusLabel, buttonBox);
            int index = washers.indexOf(washer);
            grid.add(card, index % 2, index / 2);
        }

        VBox scrollContent = new VBox(20, titleBox, grid);
        scrollContent.setAlignment(Pos.TOP_CENTER);
        scrollContent.setPadding(new Insets(0, 0, 60, 0));

        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent;"
                            + "-fx-background-color: transparent;"
        );

        //Log Out Button
        Button logout = new Button("Log Out");
        logout.setPrefSize(120, 50);
        logout.setFont(Font.font("Arial", 16));
        logout.setCursor(javafx.scene.Cursor.HAND);
        logout.setStyle("-fx-background-color: #ef4444;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10;"
        );
        logout.setOnAction(e -> {
            try {
                StartPage startPage = new StartPage();
                startPage.start(primaryStage);
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreenExitHint("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Camera Button
        Button cameraButton = new Button("Camera");
        cameraButton.setPrefSize(120, 50);
        cameraButton.setFont(Font.font("Arial", 16));
        cameraButton.setCursor(javafx.scene.Cursor.HAND);
        cameraButton.setStyle("-fx-background-color: #2563eb;"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 10;"
        );

        HBox topLeftBox = new HBox(logout);
        topLeftBox.setAlignment(Pos.TOP_LEFT);
        topLeftBox.setPadding(new Insets(20, 0, 0, 20));

        HBox topRightBox = new HBox(cameraButton);
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        topRightBox.setPadding(new Insets(20, 20, 0, 0));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(topLeftBox);
        topBar.setRight(topRightBox);

        // Background
        WebView background = new WebView();
        background.setContextMenuEnabled(false);
        background.setMouseTransparent(true);
        background.getEngine()
                .load(
                    getClass()
                      .getResource("/images/BackgroundUserPage.svg")
                      .toExternalForm()
                );

        StackPane root = new StackPane();
        VBox content = new VBox(topBar, scrollPane);
        content.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(background, content);

        //Camera Video
        StackPane videoOverlay = new StackPane();
        videoOverlay.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        videoOverlay.setVisible(false);

        String videoPath = getClass()
                            .getResource("/images/SecurityCamera.mp4")
                            .toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(1280);
        mediaView.setFitHeight(720);

        Button closeVideo = new Button("✕");
        closeVideo.setFont(Font.font(18));
        closeVideo.setStyle("-fx-background-color: white;"
                            + "-fx-text-fill: black;"
                            + "-fx-background-radius: 50;");
        closeVideo.setCursor(javafx.scene.Cursor.HAND);
        closeVideo.setOnAction(e -> {
            mediaPlayer.pause();
            videoOverlay.setVisible(false);
        });

        StackPane.setAlignment(closeVideo, Pos.TOP_RIGHT);
        StackPane.setMargin(closeVideo, new Insets(20));

        videoOverlay.getChildren().addAll(mediaView, closeVideo);
        root.getChildren().add(videoOverlay);

        cameraButton.setOnAction(e -> {
            long elapsedSeconds = Instant.now().getEpochSecond() - appStartEpoch;
            double resumeAt = Math.min(elapsedSeconds % 30, 29);
            mediaPlayer.seek(
                    javafx.util.Duration.seconds(resumeAt)
            );
            videoOverlay.setVisible(true);
            mediaPlayer.play();
        });

        background.prefWidthProperty()
                .bind(root.widthProperty());
        background.prefHeightProperty()
                .bind(root.heightProperty());

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        return scene;
    }

    private void updateStatusLabel(WasherViewModel washer, Text label) {
        switch (washer.getStatus()) {
            case "Available":
                label.setText("Available");
                label.setFill(Color.web("#16a34a"));
                break;
            case "Out of Order":
                label.setText("Out of Order");
                label.setFill(Color.web("#dc2626"));
                break;
            case "In Use":
                label.setText("In Use");
                label.setFill(Color.web("#2563eb"));
                break;
            case "Stopped":
                label.setText("Stopped");
                label.setFill(Color.web("#1d4ed8"));
                break;
            default:
                label.setText("Unknown");
                label.setFill(Color.GRAY);
        }
    }

    private void updateButtons(
            WasherViewModel washer,
            Button toggle,
            Button stopResume,
            Text statusLabel,
            Stage stage) {
        if (washer.getStatus().equals("Out of Order")) {
            toggle.setText("Mark as Available");
            toggle.setStyle("-fx-background-color: #10b981;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 10;");
        } else {
            toggle.setText("Mark as Out of Order");
            toggle.setStyle("-fx-background-color: #f97316;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 10;");
        }

        if (washer.getStatus().equals("Stopped")) {
            stopResume.setText("Resume");
            stopResume.setStyle("-fx-background-color: #2563eb;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 10;");
        } else {
            stopResume.setText("Hard Stop");
            stopResume.setStyle("-fx-background-color: #ef4444;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 10;");
        }
    }
}


