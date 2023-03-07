package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.util.Constants;

public class Rest extends SequentialCommandGroup {

	public Rest() {
		addCommands(new SetArm(Constants.Arm.REST_POSITION), new SetElevator(Constants.Elevator.FLOOR_POSITION));
	}
}
