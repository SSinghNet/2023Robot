package org.mort11.commands.control;

import org.mort11.subsystems.Claw;
import org.mort11.util.Constants;
import org.mort11.util.Control;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawControl extends CommandBase {
	private Claw claw;

	public ClawControl() {
		claw = Claw.getInstance();

		addRequirements(claw);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		/**
		 * Uses PID Loop when given values of the D pad to change the wrist positions
		 */
		if(Control.getXboxController().povRight().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povUp().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povLeft().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povDown().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povDownLeft().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povDownRight().getAsBoolean()){
			claw.setWristPosition(0);

		} else if(Control.getXboxController().povUpLeft().getAsBoolean()){
			claw.setWristPosition(0);

		} else{
			claw.setWristPosition(0);
		}
		/**
		 * Claw Intake runs based on right bumper of left bumper feedback
		 */
		if(Control.getXboxController().rightBumper().getAsBoolean()){
			claw.setIntakePercentOutput(Constants.Claw.SPEED);
		} else if(Control.getXboxController().leftBumper().getAsBoolean()){
			claw.setIntakePercentOutput(-(Constants.Claw.SPEED));
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {

	}
}
