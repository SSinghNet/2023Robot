package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Elevator;
import org.mort11.util.Constants;

public class UpperNode extends InstantCommand {
	private Elevator elevator;
	private Arm arm;

	public UpperNode() {
		elevator = Elevator.getInstance();
		arm = Arm.getInstance();
		addRequirements(elevator, arm);
	}

	@Override
	public void initialize() {
		elevator.setSetpoint(Constants.Elevator.UPPER_NODE_POSITION);
		arm.setSetpoint(Constants.Arm.SCORING_POSITION);
	}
}
