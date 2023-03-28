package org.mort11.util;

import static org.mort11.util.Constants.ControlPorts.*;
import static org.mort11.util.Constants.RobotSpecs.*;

import org.mort11.commands.auto.PlaceConeGrabConeCommunity;
import org.mort11.commands.drivetrain.*;
import org.mort11.commands.endeffector.*;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Wrist;
import org.mort11.subsystems.Arm;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Control {
	private static CommandJoystick joystick;
	private static CommandJoystick throttle;
	private static CommandXboxController xboxController;

	private static Drivetrain drivetrain;
	private static Arm arm;
	private static Claw claw;
	private static Elevator elevator;
	private static Wrist wrist;

	public static void init() {
		joystick = new CommandJoystick(JOYSTICK);
		throttle = new CommandJoystick(THROTTLE);
		xboxController = new CommandXboxController(XBOX_CONTROLLER);

		drivetrain = Drivetrain.getInstance();
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		elevator = Elevator.getInstance();
		wrist = Wrist.getInstance();

		SmartDashboard.putBoolean("FastSpeed", false);

		configureBindings();
	}

	/**
	 * Configure secondary button bindings
	 */
	public static void configureBindings() {
		drivetrain.setDefaultCommand(
				new Drive(Control::getJoystickY, Control::getJoystickX, Control::getJoystickTwist, true));

		// driver
		joystick.button(1).onTrue(new InstantCommand(drivetrain::zeroGyroscope));

		joystick.button(2).whileTrue(
				new SequentialCommandGroup(new MoveToPos(0, Units.inchesToMeters(30), 0), new MoveToPos(-0.5, 0, 0)));

		joystick.button(5).whileTrue(new MoveToAprilTag(DriverStation.getAlliance() == Alliance.Blue ? 6 : 3
		// 6
		));
		joystick.button(3).whileTrue(new MoveToAprilTag(DriverStation.getAlliance() == Alliance.Blue ? 7 : 2
		// 7
		));
		joystick.button(4).whileTrue(new MoveToAprilTag(DriverStation.getAlliance() == Alliance.Blue ? 8 : 1
		// 8
		));

		joystick.button(6).whileTrue(
				new SequentialCommandGroup(new MoveToPos(0, Units.inchesToMeters(-31), 0), new MoveToPos(-0.5, 0, 0)));

		// joystick.button(7).whileTrue(new PlaceConeGrabCube());

		joystick.povRight().whileTrue(new RotateToAngle(-90, false));
		joystick.povUp().whileTrue(new RotateToAngle(0, false));
		joystick.povLeft().whileTrue(new RotateToAngle(90, false));
		joystick.povDown().whileTrue(new RotateToAngle(180, false));

		// endeffector
		xboxController.povRight().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.RIGHT_POSITION)));
		xboxController.povUp().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.UP_POSITION)));
		xboxController.povLeft().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.LEFT_POSITION)));
		xboxController.povDown().onTrue(new InstantCommand(() -> wrist.setSetpoint(Constants.Wrist.DOWN_POSITION)));

		// xboxController.a().onTrue(new Floor());
		// xboxController.b().onTrue(new Rest());
		// xboxController.x().onTrue(new MiddleNode());
		// xboxController.y().onTrue(new UpperNode());
		// xboxController.start().onTrue(new Shelf());

		xboxController.a().onTrue(SetArmAndElevator.floor());
		xboxController.b().onTrue(SetArmAndElevator.rest());
		xboxController.x().onTrue(SetArmAndElevator.middleNode());
		xboxController.y().onTrue(SetArmAndElevator.upperNode());
		xboxController.start().onTrue(SetArmAndElevator.shelf());
		xboxController.back().onTrue(SetArmAndElevator.zero());

		// TODO: check right trigger axis
		xboxController.axisGreaterThan(3, 0.5).onTrue(SetArmAndElevator.clamp());

		xboxController.leftBumper().toggleOnTrue(Commands.startEnd(() -> SmartDashboard.putBoolean("FastSpeed", false),
				() -> SmartDashboard.putBoolean("FastSpeed", true)));
		xboxController.leftBumper()
				.onTrue(new InstantCommand(() -> xboxController.getHID().setRumble(RumbleType.kBothRumble, 1)));
		xboxController.leftBumper()
				.onFalse(new InstantCommand(() -> xboxController.getHID().setRumble(RumbleType.kBothRumble, 0)));
		xboxController.rightBumper().onTrue(new InstantCommand(claw::togglePiston, claw));

		// xboxController.axisLessThan(1, -0.5)
		// .whileTrue(Commands.startEnd(() -> claw.setSpeed(false), () ->
		// claw.setSpeed(0), claw));
		// xboxController.axisGreaterThan(1, 0.5)
		// .whileTrue(Commands.startEnd(() -> claw.setSpeed(true), () ->
		// claw.setSpeed(0), claw));

		xboxController.axisLessThan(1, -0.5)
				.whileTrue(Commands.startEnd(() -> claw.setSpeed(false), () -> claw.setSpeed(0), claw));
		xboxController.axisGreaterThan(1, 0.5)
				.whileTrue(Commands.startEnd(() -> claw.setSpeed(true), () -> claw.setSpeed(0), claw));

		xboxController.axisLessThan(1, -0.5).whileTrue(new RumbleController(0.3));

		xboxController.axisGreaterThan(1, 0.5).whileTrue(new RumbleController(0.3));

		xboxController.axisLessThan(4, -0.5).whileTrue(new MoveArm(0.1));
		xboxController.axisGreaterThan(4, 0.5).whileTrue(new MoveArm(-0.1));

		// check axis
		xboxController.axisLessThan(5, -0.5).whileTrue(new MoveElevator(0.1));
		xboxController.axisGreaterThan(5, 0.5).whileTrue(new MoveElevator(-0.1));
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
		return -(modifyJoystickAxis(joystick.getX(), throttle.getRawAxis(2)) * MAX_VELOCITY_METERS_PER_SECOND) * 0.75;
	}

	public static double getJoystickY() {
		return -(modifyJoystickAxis(joystick.getY(), throttle.getRawAxis(2)) * MAX_VELOCITY_METERS_PER_SECOND);
	}

	public static double getJoystickTwist() {
		return -(modifyJoystickAxis(joystick.getTwist(), throttle.getRawAxis(2))
				* MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
	}

	public static void setControllerRumble(double value) {
		xboxController.getHID().setRumble(RumbleType.kBothRumble, value);
	}

}
