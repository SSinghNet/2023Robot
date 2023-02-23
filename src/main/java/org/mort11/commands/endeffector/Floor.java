package org.mort11.commands.endeffector;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Elevator;
import org.mort11.util.Constants;

public class Floor extends SequentialCommandGroup {
	private Elevator elevator;
	private Arm arm;

	public Floor() {
		elevator = Elevator.getInstance();
		arm = Arm.getInstance();
		addCommands(
				elevator.getPosition() > 2 ? new Rest() : new InstantCommand(),
				new InstantCommand(() -> elevator.setSetpoint(Constants.Elevator.FLOOR_POSITION)),
				new WaitCommand(0.3),
				new InstantCommand(() -> arm.setSetpoint(Constants.Arm.FLOOR_POSITION))
				);
	}
}
