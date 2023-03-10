package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ScoreCone extends SequentialCommandGroup {
	public ScoreCone() {
		addCommands(new UpperNode(), new SetClawPiston(true), new WaitCommand(0.4), new SetClawPiston(false),
				new Rest());
	}
}
