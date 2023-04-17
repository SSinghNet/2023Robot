package org.mort11.commands.endeffector;

import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.clawwrist.SetClawPiston;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Flop extends SequentialCommandGroup {
    public Flop() {
        addCommands(
            new SetArmAndElevator(Constants.Arm.FLOOR_POSITION - 5000, Constants.Elevator.FLOOR_POSITION),
            new SetClawPiston(true),
            new WaitCommand(0.2),
            SetArmAndElevator.rest()
        );
    }
}
