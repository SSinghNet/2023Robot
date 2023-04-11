package org.mort11.commands.endeffector.clawwrist;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ToggleClawPiston extends InstantCommand {
	private Claw claw;

	public ToggleClawPiston() {
		claw = Claw.getInstance();
		addRequirements(claw);
	}

	@Override
	public void initialize() {
		claw.togglePiston();
	}
}
