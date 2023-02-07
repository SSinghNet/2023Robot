package org.mort11.commands.wrist;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateWrist extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	private double position;

	public RotateWrist(double position) {
		wrist = Wrist.getInstance();

		this.position = position;

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
