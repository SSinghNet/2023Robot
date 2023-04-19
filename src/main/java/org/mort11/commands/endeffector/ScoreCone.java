package org.mort11.commands.endeffector;

import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.clawwrist.SetClawPiston;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ScoreCone extends SequentialCommandGroup {
	public ScoreCone() {
		addCommands(new SetArmAndElevator(Constants.Arm.SCORING_POSITION, Constants.Elevator.UPPER_NODE_POSITION - 2),
				new SetClawPiston(true), new WaitCommand(0.5), new SetClawPiston(false)
		// , new Rest()
		);
	}
}
