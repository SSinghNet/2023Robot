package org.mort11.util;

import static org.mort11.util.Constants.ControlPorts.*;
import static org.mort11.util.Constants.RobotSpecs.*;

import org.mort11.commands.arm.MoveArm;
import org.mort11.commands.drivetrain.RotateToAngle;
import org.mort11.commands.wrist.RotateWrist;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Wrist;
import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Control {
	private static CommandJoystick leftJoystick;
	private static CommandJoystick rightJoystick;
	private static CommandXboxController xboxController;

	private static Drivetrain drivetrain;
	private static Arm arm;
	private static Claw claw;
	private static Elevator elevator;
	private static Wrist wrist;

	public static void init() {
		leftJoystick = new CommandJoystick(LEFT_JOYSTICK);
		rightJoystick = new CommandJoystick(RIGHT_JOYSTICK);
		xboxController = new CommandXboxController(XBOX_CONTROLLER);

		drivetrain = Drivetrain.getInstance();
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		elevator = Elevator.getInstance();
		wrist = Wrist.getInstance();
	}

	/**
	 * Configure secondary button bindings
	 */
	public static void configureBindings() {
		// left joystick

		// right joystick
		rightJoystick.trigger().onTrue(new InstantCommand(drivetrain::zeroGyroscope));

		rightJoystick.povRight().whileTrue(new RotateToAngle(-90, false));
		rightJoystick.povUp().whileTrue(new RotateToAngle(0, false));
		rightJoystick.povLeft().whileTrue(new RotateToAngle(90, false));
		rightJoystick.povDown().whileTrue(new RotateToAngle(180, false));

		// controller
		// TODO: check wrist positions
		xboxController.povRight().onTrue(new RotateWrist(0));
		xboxController.povUp().onTrue(new RotateWrist(0));
		xboxController.povLeft().onTrue(new RotateWrist(0));
		xboxController.povDown().onTrue(new RotateWrist(0));

		// TODO: check arm positions
		// xboxController.().onTrue(new MoveArm());
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
		return value * (throttleValue * -0.4 + 0.6);
	}

	public static double getJoystickX() {
		return -modifyJoystickAxis(rightJoystick.getX(), rightJoystick.getThrottle()) * MAX_VELOCITY_METERS_PER_SECOND;
	}

	public static double getJoystickY() {
		return -modifyJoystickAxis(rightJoystick.getY(), rightJoystick.getThrottle()) * MAX_VELOCITY_METERS_PER_SECOND;
	}

	public static double getJoystickTwist() {
		return -modifyJoystickAxis(rightJoystick.getTwist(), rightJoystick.getThrottle())
				* MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
	}

}
