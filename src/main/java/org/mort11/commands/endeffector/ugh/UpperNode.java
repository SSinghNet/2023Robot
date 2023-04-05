package org.mort11.commands.endeffector.ugh;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.commands.endeffector.armelevator.SetArm;
import org.mort11.commands.endeffector.armelevator.SetElevator;
import org.mort11.util.Constants;

public class UpperNode extends SequentialCommandGroup {

	public UpperNode() {
		addCommands(new SetArm(Constants.Arm.REST_POSITION), new SetElevator(Constants.Elevator.UPPER_NODE_POSITION),
				new SetArm(Constants.Arm.SCORING_POSITION));
	}
}