// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cameraserver.CameraServerShared;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Pneumatic;
import org.mort11.subsystems.Wrist;
import org.mort11.util.Auto;
import org.mort11.util.Control;

public class RobotContainer {
	private Arm arm;
	private Claw claw;
	private Drivetrain drivetrain;
	private Elevator elevator;
	private Pneumatic pneumatic;
	private Wrist wrist;

	public RobotContainer() {
		// initialize subsystems
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		drivetrain = Drivetrain.getInstance();
		elevator = Elevator.getInstance();
		pneumatic = Pneumatic.getInstance();
		wrist = Wrist.getInstance();

		// initalize controllers
		Control.init();

		// configure controls
		Control.configureBindings();

		// create autonomous commands and chooser
		Auto.init();

		UsbCamera camera = new UsbCamera("camera", 0);
		MjpegServer mjpegServer = new MjpegServer("Usb Camera", 1181);
		mjpegServer.setSource(camera);
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
