// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11;

import org.mort11.commands.defaults.ArmDefault;
import org.mort11.commands.defaults.ClawDefault;
import org.mort11.commands.defaults.DriveDefault;
import org.mort11.commands.defaults.ElevatorDefault;
import org.mort11.commands.defaults.WristDefault;
import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Wrist;
import org.mort11.util.Auto;
import org.mort11.util.Control;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

	private Drivetrain drivetrain;
	private Arm arm;
	private Claw claw;
	private Elevator elevator;
	private Wrist wrist;

	public RobotContainer() {
		// initalize controllers
		Control.init();

		// Subsystem objects
		drivetrain = Drivetrain.getInstance();
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		elevator = Elevator.getInstance();
		wrist = Wrist.getInstance();

		// set default commands
		drivetrain.setDefaultCommand(
				new DriveDefault(Control::getJoystickX, Control::getJoystickY, Control::getJoystickTwist));
		arm.setDefaultCommand(new ArmDefault());
		claw.setDefaultCommand(new ClawDefault());
		elevator.setDefaultCommand(new ElevatorDefault());
		wrist.setDefaultCommand(new WristDefault());

		// configure secondary button bindings
		Control.configureBindings();

		// create autonomous commands and chooser
		Auto.init();
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return Auto.getSelected();
	}
}
