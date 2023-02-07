package org.mort11.commands.defaults;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawDefault extends CommandBase {
	// TODO: implement

	private Claw claw;

	public ClawDefault() {
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
