package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.dto.WashingMachineRegisterDTO;
import com.mycompany.irr00_group_project.entity.WashingMachine;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class WashingMachineDTOMapper {
    private WashingMachineDTOMapper() {}

    public static WashingMachine mapToWashingMachineDTO(WashingMachineRegisterDTO washingMachineRegisterDTO) {
        return new WashingMachine(
                washingMachineRegisterDTO.getWashingMachineModel(),
                washingMachineRegisterDTO.getSerialNumber());
    }
}
