package com.mycompany.irr00_group_project.dto;

import com.mycompany.irr00_group_project.entity.enums.WashingMachineModel;

/**
 * Data Transfer Object for registering a washing machine.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class WashingMachineRegisterDTO {
    private WashingMachineModel washingMachineModel;
    private String serialNumber;

    public WashingMachineRegisterDTO() {}

    public WashingMachineRegisterDTO(WashingMachineModel washingMachineDTO) {
        this.washingMachineModel = washingMachineDTO;
    }

    public WashingMachineModel getWashingMachineModel() {
        return washingMachineModel;
    }

    public void setWashingMachineModel(WashingMachineModel washingMachineModel) {
        this.washingMachineModel = washingMachineModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
