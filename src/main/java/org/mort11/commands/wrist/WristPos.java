package org.mort11.commands.wrist;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WristPos extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	private double position;

	public WristPos(double position) {
		wrist = Wrist.getInstance();

		this.position = position;

		addRequirements(wrist);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		wrist.setPosition(position);
	}

	@Override
	public boolean isFinished() {
		return wrist.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		wrist.setSpeed(0);
	}
}
