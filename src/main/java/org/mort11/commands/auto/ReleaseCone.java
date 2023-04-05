package org.mort11.commands.auto;

import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.clawwrist.SetClawPiston;
import org.mort11.commands.endeffector.clawwrist.TimedIntake;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ReleaseCone extends SequentialCommandGroup {
    public ReleaseCone() {
        addCommands(
                new SetClawPiston(true),
                new TimedIntake(0.2, false, false),
                new SetArmAndElevator(Constants.Arm.REST_POSITION, Constants.Elevator.UPPER_NODE_POSITION),
                new SetClawPiston(false),
                SetArmAndElevator.rest()
        );
    }
}
