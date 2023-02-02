package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Elevator.*;

public class Elevator extends SubsystemBase {
	private static Elevator elevator;

	private CANSparkMax driveNeoMaster;
	private CANSparkMax driveNeoFollower;

	/** {@link https://www.revrobotics.com/rev-31-1462/} */
	private DigitalInput limitSwitch;

	private Elevator() {
		driveNeoMaster = new CANSparkMax(DRIVE_MASTER, MotorType.kBrushless);
		driveNeoFollower = new CANSparkMax(DRIVE_FOLLOWER, MotorType.kBrushless);

		driveNeoFollower.follow(driveNeoMaster, false); // todo: check invert

		limitSwitch = new DigitalInput(LIMIT_SWITCH);
	}

	@Override
	public void periodic() {

	}

	/**
	 * Get the elevator object
	 */
	public static Elevator getInstance() {
		if (elevator == null) {
			elevator = new Elevator();
		}
		return elevator;
	}
}
