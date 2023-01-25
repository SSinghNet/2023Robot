package org.mort11;

import static org.mort11.Constants.ControlPorts.*;
import static org.mort11.Constants.RobotSpecs.*;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Control {
	private static CommandJoystick leftJoystick;
	private static CommandJoystick rightJoystick;
	private static CommandXboxController xboxController;

	public static void init() {
		leftJoystick = new CommandJoystick(LEFT_JOYSTICK);
		rightJoystick = new CommandJoystick(RIGHT_JOYSTICK);
		xboxController = new CommandXboxController(XBOX_CONTROLLER);
	}

	public static void configureBindings() {

	}

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

	private static double modifyJoystickAxis(double value, double throttleValue) {
		// Deadband
		value = deadband(value, 0.1);

		// Square the axis
		value = Math.copySign(value * value, value);

		// takes the throttle value and takes it from [-1, 1] to [0.2, 1], and
		// multiplies it by the
		// value
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
