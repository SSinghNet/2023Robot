package org.mort11.commands.claw;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawIntake extends CommandBase {

	private Claw claw;
	private double speed;

	public ClawIntake(double speed) {
		claw = Claw.getInstance();

		this.speed = speed;

		addRequirements(this.claw);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		claw.setSpeed(speed);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
    public void end(boolean interrupted) {
        claw.setSpeed(0);
	}
}
