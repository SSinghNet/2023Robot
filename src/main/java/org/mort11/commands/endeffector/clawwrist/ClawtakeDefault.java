package org.mort11.commands.endeffector.clawwrist;

import org.mort11.subsystems.Claw;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawtakeDefault extends CommandBase {
	private Claw claw;
	
	public ClawtakeDefault() {
		claw = Claw.getInstance();

		addRequirements(claw);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		// if (claw.getPiston() == true) {
		claw.setSpeed(-Constants.Claw.CUBE_SPEED);
		// }
	}

	@Override
	public void end(boolean interrupted) {

	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
