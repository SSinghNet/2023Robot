package org.mort11.commands.endeffector;

import org.mort11.subsystems.Arm;
import org.mort11.subsystems.Elevator;
import org.mort11.util.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClearArm extends CommandBase {
	private Arm arm;
	private Elevator elevator;
	private double elevatorSetpoint;
	private boolean skipClear = false;

	public ClearArm(double elevatorSetpoint) {
		this.elevatorSetpoint = elevatorSetpoint;
		arm = Arm.getInstance();
		elevator = Elevator.getInstance();
		addRequirements(arm); // this command must NOT move the elevator
	}

	@Override
	public void initialize() {
		if (elevatorSetpoint < 1 && elevator.getPosition() < 1) {
			skipClear = true;
		} else if (elevatorSetpoint > (Constants.Elevator.SHELF_POSITION - 1)
				&& elevator.getPosition() > (Constants.Elevator.SHELF_POSITION - 1)) {
			skipClear = true;
		} else {
			skipClear = false;
		}

		if (!skipClear) {
			arm.setSetpoint(Constants.Arm.TOP_CLEAR - 4.5);
		}
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		if (skipClear) {
			return true;
		} else {
			return arm.isClear();
		}
	}
}
