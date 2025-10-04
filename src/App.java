/*
 *Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.irr00_group_project;

import java.util.ArrayList;

import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.data.FileDb;
import com.mycompany.irr00_group_project.entity.enums.Status;
import com.mycompany.irr00_group_project.mapper.WashingMachineToWasherMapper;
import com.mycompany.irr00_group_project.service.WashingMachineService;
import javafx.application.Application;

/**
 * This is the main class which is used to invoke the StartPage GUI class.
 * 
 * @author 2080338
 * @author 2132389
 */
public class App {
    public static ArrayList<WasherViewModel> washers = new ArrayList<>();
    
    public static void main(String[] args) {
        loadWashingMachines();
        Application.launch(StartPage.class, args);
    }

    public static void loadWashingMachines() {
        washers = new ArrayList<>();
        FileDb db = FileDb.getInstance();
        WashingMachineService service = new WashingMachineService(new WashingMachineDAO());
        var washingMachines = db.selectAllWashingMachines();
        for (var washingMachine : washingMachines) {
            var washer = WashingMachineToWasherMapper.mapToWashingMachineEntity(washingMachine);
            if (service.getCurrentBooking(washingMachine.getId().intValue()) != null) {
                washer.setStatus("In Use");
            } else if(washingMachine.getStatus() == Status.UNAVAILABLE) {
                washer.setStatus("Out of Order");
                washer.setBookedByUser(false);
            } else {
                washer.setStatus("Available");
                washer.setBookedByUser(false);
            }
            washers.add(washer);
        }
    }
}


