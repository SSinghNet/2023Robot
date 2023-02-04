package org.mort11.commands.control;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawControl extends CommandBase {
	private Claw claw;

	public ClawControl() {
		claw = Claw.getInstance();

		addRequirements(claw);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {

	}
}
