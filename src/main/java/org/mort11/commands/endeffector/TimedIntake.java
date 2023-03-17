package org.mort11.commands.endeffector;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedIntake extends CommandBase {
	private Claw claw;

	private Timer timer;
	private double time;
	private boolean isCone;
	private boolean in;

	/**
	 * Run intake for specified amount of time
	 * @param time time (seconds)
	 * @param isCone cone?
	 * @param in intake?
	 */
	public TimedIntake(double time, boolean isCone, boolean in) {
		claw = Claw.getInstance();
		timer = new Timer();
		this.time = time;
		this.isCone = isCone;
		this.in = in;

		addRequirements(claw);
	}

	@Override
	public void initialize() {
		timer.reset();
		timer.start();

		SmartDashboard.putBoolean("FastSpeed", isCone);
		claw.setSpeed(in);
	}

	@Override
	public void execute() {

	}

	@Override
	public void end(boolean interrupted) {
		claw.setSpeed(0);
	}

	@Override
	public boolean isFinished() {
		return timer.get() > time;
	}
}
