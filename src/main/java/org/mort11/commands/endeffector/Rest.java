package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Elevator;
import org.mort11.util.Constants;

public class Rest extends SequentialCommandGroup {
	private Elevator elevator;
	private Arm arm;

	public Rest() {
		elevator = Elevator.getInstance();
		arm = Arm.getInstance();
		addCommands(
			new InstantCommand(() -> arm.setSetpoint(Constants.Arm.REST_POSITION)),
			new WaitCommand(.5),
			new InstantCommand(() -> elevator.setSetpoint(Constants.Elevator.FLOOR_POSITION))
		);
	}
}
