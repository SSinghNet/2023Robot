package org.mort11.commands.endeffector;

import org.mort11.subsystems.Arm;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClearArm extends CommandBase {
    private Arm arm;

    public ClearArm() {
        arm = Arm.getInstance();
        addRequirements(arm);
    }
    
    @Override
    public void initialize() {
        if (arm.getPosition() < Constants.Arm.BOTTOM_CLEAR) {
            arm.setSetpoint(Constants.Arm.BOTTOM_CLEAR + 3);
        } else {
            arm.setSetpoint(Constants.Arm.TOP_CLEAR - 3);
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return arm.isClear();
    }
}
