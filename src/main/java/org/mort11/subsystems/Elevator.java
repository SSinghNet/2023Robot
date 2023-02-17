package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Elevator.*;

public class Elevator extends SubsystemBase {
	private static Elevator elevator;

	private CANSparkMax driveNeoMaster;
	private CANSparkMax driveNeoFollower;

	/** {@link https://www.revrobotics.com/rev-31-1462/} */
	private DigitalInput limitSwitch;
	private PIDController positionController;

	private Elevator() {
		driveNeoMaster = new CANSparkMax(ELEVATOR_MASTER, MotorType.kBrushless);
		driveNeoFollower = new CANSparkMax(ELEVATOR_FOLLOWER, MotorType.kBrushless);

		driveNeoFollower.follow(driveNeoMaster, false); // todo: check invert
		positionController = new PIDController(KP, KI, KD);
		limitSwitch = new DigitalInput(LIMIT_SWITCH);
	}

	/**
	 * Moves the elevator a preset speed
	 *
	 * @param speed
	 *            The speed to set the elevator
	 */
	public void setSpeed(double speed) {
		// todo: program limit switch check.
		driveNeoMaster.set(speed);
	}

	/**
	 * Moves the elevator to a specific point based on value passed.
	 *
	 * @param setpoint
	 *            The encoder position to move to.
	 */
	public void setPosition(double setpoint) {
		driveNeoMaster.setVoltage(positionController.calculate(driveNeoMaster.getEncoder().getPosition(), setpoint));
	}

	public boolean atSetpoint() {
		return positionController.atSetpoint();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Elevator Encoder", driveNeoMaster.getEncoder().getPosition());
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
