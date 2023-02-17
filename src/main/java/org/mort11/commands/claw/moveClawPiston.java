package org.mort11.commands.claw;

import org.mort11.subsystems.Claw;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClawPiston extends CommandBase {

	private Claw claw;
	private DoubleSolenoid.Value value;

	public MoveClawPiston(DoubleSolenoid.Value value) {
		claw = Claw.getInstance();

		this.value = value;

		addRequirements(claw);
	}

	public MoveClawPiston() {
		claw = Claw.getInstance();

		value = claw.getPiston();
;
		addRequirements(this.claw);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		if (value == Value.kForward) {
			claw.setPiston(Value.kReverse);
		} else if (value == Value.kReverse) {
			claw.setPiston(Value.kForward);
		} else {
			claw.setPiston(Value.kOff);
		}

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
	}
}
