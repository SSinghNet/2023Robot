package org.mort11.commands.wrist;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WristSpeed extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	private double speed;

	public WristSpeed(double speed) {
		wrist = Wrist.getInstance();

		this.speed = speed;

		addRequirements(wrist);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		wrist.setSpeed(speed);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		wrist.setSpeed(0);
	}
}
