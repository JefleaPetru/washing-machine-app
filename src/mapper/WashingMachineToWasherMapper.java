package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.WasherViewModel;
import com.mycompany.irr00_group_project.entity.WashingMachine;

public class WashingMachineToWasherMapper {
    private WashingMachineToWasherMapper() {
    }

    public static WasherViewModel mapToWashingMachineEntity(WashingMachine washingMachine) {
        return new WasherViewModel(washingMachine.getId().intValue(), washingMachine.getModel());
    }
}
