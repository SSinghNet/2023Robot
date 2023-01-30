// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11;

import org.mort11.commands.control.DriveControl;
import org.mort11.subsystems.Drivetrain;
import org.mort11.util.Control;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

	private Drivetrain drivetrain;

	public RobotContainer() {
		// initalize controllers
		Control.init();

		// Subsystems
		drivetrain = Drivetrain.getInstance();

		// default commands
		drivetrain.setDefaultCommand(new DriveControl(() -> Control.getJoystickX(), () -> Control.getJoystickY(),
				() -> Control.getJoystickTwist(), false));

		Control.configureBindings();
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return null;
	}
}
