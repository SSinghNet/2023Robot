package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.util.Constants;

public class MiddleNode extends SequentialCommandGroup {

	public MiddleNode() {
		addCommands(new SetArm(Constants.Arm.REST_POSITION), new SetElevator(Constants.Elevator.MIDDLE_NODE_POSITION),
				new SetArm(Constants.Arm.SCORING_POSITION));
	}
}
