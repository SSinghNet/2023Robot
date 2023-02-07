package org.mort11.commands.defaults;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WristDefault extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	public WristDefault() {
		wrist = Wrist.getInstance();
		addRequirements(wrist);
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
