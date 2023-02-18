package org.mort11.util;

import static org.mort11.util.Constants.ControlPorts.*;
import static org.mort11.util.Constants.RobotSpecs.*;
import org.mort11.util.Constants;

import org.mort11.commands.drivetrain.*;
import org.mort11.commands.endeffector.*;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Wrist;
import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Control {
	private static CommandJoystick joystick;
	private static CommandXboxController xboxController;

	private static Drivetrain drivetrain;
	private static Arm arm;
	private static Claw claw;
	private static Elevator elevator;
	private static Wrist wrist;

	public static void init() {
		joystick = new CommandJoystick(JOYSTICK);
		xboxController = new CommandXboxController(XBOX_CONTROLLER);

		drivetrain = Drivetrain.getInstance();
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		elevator = Elevator.getInstance();
		wrist = Wrist.getInstance();

		configureBindings();
	}

	/**
	 * Configure secondary button bindings
	 */
	public static void configureBindings() {
		drivetrain
				.setDefaultCommand(new Drive(Control::getJoystickX, Control::getJoystickY, Control::getJoystickTwist));

		// driver
		joystick.button(1).onTrue(new InstantCommand(drivetrain::zeroGyroscope));

		joystick.povRight().whileTrue(new RotateToAngle(-90, false));
		joystick.povUp().whileTrue(new RotateToAngle(0, false));
		joystick.povLeft().whileTrue(new RotateToAngle(90, false));
		joystick.povDown().whileTrue(new RotateToAngle(180, false));

		// endeffector
		xboxController.povRight().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.RIGHT_POSITION)));
		xboxController.povUp().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.UP_POSITION)));
		xboxController.povLeft().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.LEFT_POSITION)));
		xboxController.povDown().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.DOWN_POSITION)));

		xboxController.a().onTrue(new Floor());
		xboxController.b().onTrue(new Shelf());
		xboxController.x().onTrue(new MiddleNode());
		xboxController.y().onTrue(new UpperNode());

		xboxController.rightBumper().onTrue(new InstantCommand(claw::togglePiston, claw));

		xboxController.axisLessThan(1, -0.5).whileTrue(new InstantCommand(() -> claw.setSpeed(-0.1)));
		xboxController.axisGreaterThan(1, 0.5).whileTrue(new InstantCommand(() -> claw.setSpeed(0.1)));
	}

	/**
	 * Calculate value based on deadband
	 *
	 * @param value
	 * @param deadband
	 */
	private static double deadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * Change joystick axis
	 *
	 * @param value
	 * @param throttleValue
	 *            from [-1, 1]
	 */
	private static double modifyJoystickAxis(double value, double throttleValue) {
		// Deadband
		value = deadband(value, 0.1);

		// Square the axis
		value = Math.copySign(value * value, value);

		// takes the throttle value and takes it from [-1, 1] to [0.2, 1], and
		// multiplies it by the value
		return value * (-throttleValue * -0.4 + 0.6);
	}

	public static double getJoystickX() {
		return -modifyJoystickAxis(joystick.getX(), joystick.getRawAxis(2)) * MAX_VELOCITY_METERS_PER_SECOND;
	}

	public static double getJoystickY() {
		return -modifyJoystickAxis(joystick.getY(), joystick.getRawAxis(2)) * MAX_VELOCITY_METERS_PER_SECOND;
	}

	public static double getJoystickTwist() {
		return -modifyJoystickAxis(joystick.getRawAxis(3), joystick.getRawAxis(2))
				* MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
	}

}
