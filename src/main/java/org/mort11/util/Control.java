package org.mort11.util;

import static org.mort11.util.Constants.ControlPorts.*;
import static org.mort11.util.Constants.RobotSpecs.*;
import static org.mort11.util.Constants.Elevator.*;

import org.mort11.commands.arm.MoveArmPos;
import org.mort11.commands.arm.MoveArmSpeed;
import org.mort11.commands.auto.Balance;
import org.mort11.commands.claw.ClawIntake;
import org.mort11.commands.claw.ClawPiston;
import org.mort11.commands.drivetrain.RotateToAngle;
import org.mort11.commands.elevator.ElevatorSpeed;
import org.mort11.commands.wrist.WristPos;
import org.mort11.commands.wrist.WristSpeed;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Wrist;
import org.mort11.subsystems.Arm;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	}

	/**
	 * Configure secondary button bindings
	 */
	public static void configureBindings() {
		// joystick
		joystick.button(1).onTrue(new InstantCommand(drivetrain::zeroGyroscope));

		joystick.button(2).whileTrue(new Balance());
		// joystick.povRight().whileTrue(new RotateToAngle(-90, false));
		// joystick.povUp().whileTrue(new RotateToAngle(0, false));
		// joystick.povLeft().whileTrue(new RotateToAngle(90, false));
		// joystick.povDown().whileTrue(new RotateToAngle(180, false));

		// controller
		// TODO: check wrist positions
		// xboxController.povRight().onTrue(new WristPos(0));
		// xboxController.povUp().onTrue(new WristPos(0));
		// xboxController.povLeft().onTrue(new WristPos(0));
		// xboxController.povDown().onTrue(new WristPos(0));
		xboxController.a().toggleOnTrue(new ElevatorSpeed(-0.1));
		xboxController.b().toggleOnTrue(new ElevatorSpeed(0.1));
		xboxController.x().whileTrue(new MoveArmSpeed(-0.1));
		xboxController.rightBumper().whileTrue(new MoveArmSpeed(0.1));
		xboxController.y().whileTrue(new WristSpeed(0.1));
		xboxController.povUp().whileTrue(new WristSpeed(-0.1));
		xboxController.povDown().whileTrue(new ClawIntake(1));
		xboxController.leftTrigger().whileTrue(new ClawIntake(-1));
		xboxController.start().toggleOnTrue(new ClawPiston(Value.kForward));
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
