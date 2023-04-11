// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Claw;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Floortake;
import org.mort11.subsystems.Pneumatic;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.Wrist;
import org.mort11.util.Auto;
import org.mort11.util.Control;

public class RobotContainer {
	private Arm arm;
	private Claw claw;
	private Drivetrain drivetrain;
	private Elevator elevator;
	private Floortake floortake;
	private Pneumatic pneumatic;
	private Wrist wrist;
	private Vision vision;

	public RobotContainer() {
		// initialize subsystems
		arm = Arm.getInstance();
		claw = Claw.getInstance();
		drivetrain = Drivetrain.getInstance();
		elevator = Elevator.getInstance();
		floortake = Floortake.getInstance();
		pneumatic = Pneumatic.getInstance();
		wrist = Wrist.getInstance();
		vision = Vision.getInstance();

		Control.init();
		Control.configure();

		Auto.init();

		// Shuffleboard.getTab("subsytems").add(arm);
		// Shuffleboard.getTab("subsytems").add(claw);
		// Shuffleboard.getTab("subsytems").add(elevator);
		// Shuffleboard.getTab("subsytems").add(floortake);
		// Shuffleboard.getTab("subsytems").add(wrist);
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
