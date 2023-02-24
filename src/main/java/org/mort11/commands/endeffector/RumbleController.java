// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.endeffector;

import org.mort11.util.Control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RumbleController extends CommandBase {
	private double rumbleValue;
	private boolean condition;

	public RumbleController(double rumbleValue) {
		this.rumbleValue = rumbleValue;
		this.condition = SmartDashboard.getBoolean("FastSpeed", false);
	}

	@Override
	public void initialize() {
		Control.setControllerRumble(rumbleValue);
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Control.setControllerRumble(0);
	}

	@Override
	public boolean isFinished() {
		return !SmartDashboard.getBoolean("FastSpeed", false);
	}
}
