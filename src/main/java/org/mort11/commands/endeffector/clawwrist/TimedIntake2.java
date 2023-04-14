package org.mort11.commands.endeffector.clawwrist;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedIntake2 extends CommandBase {
	private Claw claw;

	private Timer timer;
	private double time;
	private double speed;

	public TimedIntake2(double time, double speed) {
		claw = Claw.getInstance();
		timer = new Timer();
		this.time = time;
		this.speed = speed;

		addRequirements(claw);
	}

	@Override
	public void initialize() {
		timer.reset();
		timer.start();

		claw.setSpeed(speed);
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
