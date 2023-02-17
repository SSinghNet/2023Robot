package org.mort11.commands.wrist;

import org.mort11.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateWristPos extends CommandBase {
	// TODO: implement

	private Wrist wrist;

	private double position;

	public RotateWristPos(double position) {
		wrist = Wrist.getInstance();

		this.position = position;

		addRequirements(wrist);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		wrist.setWristPosition(position);
	}

	@Override
	public boolean isFinished() {
		return wrist.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		wrist.setWristPercentOutput(0);
	}
}
