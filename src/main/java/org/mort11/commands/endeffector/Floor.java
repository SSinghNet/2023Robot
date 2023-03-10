package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.util.Constants;

public class Floor extends SequentialCommandGroup {

	public Floor() {
		addCommands(new SetArm(Constants.Arm.REST_POSITION), new SetElevator(Constants.Elevator.FLOOR_POSITION),
				new SetArm(Constants.Arm.FLOOR_POSITION));
	}
}
