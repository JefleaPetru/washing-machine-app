package com.mycompany.irr00_group_project.dto;

import com.mycompany.irr00_group_project.entity.enums.Status;
import com.mycompany.irr00_group_project.entity.enums.WashingMachineModel;

/**
 * Data Transfer Object for washing machine information.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class WashingMachineDTO {
    private long id;
    private String serialNumber;
    private WashingMachineModel washingMachineModel;
    private Status status;

    public WashingMachineDTO() {}
    
    /**
    * Constructs a new WashingMachineDTO.
    *
    * @param id the washing machine ID
    * @param washingMachineModel the model of the washing machine
    */
    public WashingMachineDTO(long id, WashingMachineModel washingMachineModel) {
        this.id = id;
        this.washingMachineModel = washingMachineModel;
        this.status = Status.NOT_STARTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public WashingMachineModel getWashingMachineModel() {
        return washingMachineModel;
    }

    public void setWashingMachineModel(WashingMachineModel washingMachineModel) {
        this.washingMachineModel = washingMachineModel;
    }
}
