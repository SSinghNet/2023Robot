package org.mort11.commands.endeffector.clawwrist;

import org.mort11.subsystems.Claw;
import org.mort11.util.Constants;
import org.mort11.util.Control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Clawtake extends CommandBase {
	private Claw claw;
	private int in;

	public Clawtake(boolean in) {
		this.in = in ? 1 : -1;
		claw = Claw.getInstance();
		addRequirements(claw);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		if (SmartDashboard.getBoolean("FastSpeed", false)) {
			claw.setSpeed(in * Constants.Claw.CONE_SPEED);
			Control.setControllerRumble(0.3);
		} else {
			claw.setSpeed(in * Constants.Claw.CUBE_SPEED);
			Control.setControllerRumble(0);
		}
	}

	@Override
	public void end(boolean interrupted) {
		claw.setSpeed(0);
		Control.setControllerRumble(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
