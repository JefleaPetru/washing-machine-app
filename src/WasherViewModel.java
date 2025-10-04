/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.irr00_group_project;

/**
 * This class contains the options to set
 * the functionality and status of a washing machine.
 * 
 * @author 2132389
 * @author 2080338
 */
import com.mycompany.irr00_group_project.dto.WashingMachineDTO;
import com.mycompany.irr00_group_project.entity.enums.WashingMachineModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class WasherViewModel {
    private String status;
    private boolean bookedByUser;
    private boolean hasDryer;
    private int washerID;
    private WashingMachineModel washingMachineModel;

    private int remainingSeconds = -1;
    private boolean paused = false;

    private Timeline timer; // background timer

    public WasherViewModel(int washerID, WashingMachineModel washingMachineModel) {
        this.washingMachineModel = washingMachineModel;
        this.status = "Available";
        this.bookedByUser = false;
        this.hasDryer = false;
        this.washerID = washerID;
    }

    public WashingMachineModel getWashingMachineModel() {
        return this.washingMachineModel;
    }

    public int getID() {
        return this.washerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBookedByUser() {
        return bookedByUser;
    }

    public void setBookedByUser(boolean bookedByUser) {
        this.bookedByUser = bookedByUser;
    }

    public boolean hasDryer() {
        return hasDryer;
    }

    public void setHasDryer(boolean hasDryer) {
        this.hasDryer = hasDryer;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // Background Timer Logic

    public void startTimer() {
        if (timer != null) return;

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!paused && remainingSeconds > 0) {
                remainingSeconds--;
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void pauseTimer() {
        this.paused = true;
    }

    public void resumeTimer() {
        this.paused = false;
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }
}


