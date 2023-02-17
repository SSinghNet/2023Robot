package org.mort11.commands.wrist;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateWristSpeed extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	private double speed;

	public RotateWristSpeed(double speed) {
		wrist = Wrist.getInstance();

		this.speed = speed;

		addRequirements(wrist);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		wrist.setWristPercentOutput(0.1);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		wrist.setWristPercentOutput(0);
	}
}
