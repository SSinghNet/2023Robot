package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
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
	// private DigitalInput limitSwitch;

	private PIDController positionController;
	private ElevatorFeedforward feedforward;

	/** target elevator position in encoder units */
	private double setpoint;

	private Elevator() {
		driveNeoMaster = new CANSparkMax(ELEVATOR_MASTER, MotorType.kBrushless);
		driveNeoFollower = new CANSparkMax(ELEVATOR_FOLLOWER, MotorType.kBrushless);
		driveNeoFollower.follow(driveNeoMaster, true);

		driveNeoMaster.setSoftLimit(SoftLimitDirection.kReverse, TOP_LIMIT);
		driveNeoMaster.setSoftLimit(SoftLimitDirection.kForward, BOTTOM_LIMIT);

		driveNeoMaster.setSmartCurrentLimit(20);

		driveNeoMaster.burnFlash();

		positionController = new PIDController(KP, KI, KD);
		feedforward = new ElevatorFeedforward(KS, KG, KV, KA);

		setpoint = BOTTOM_LIMIT;
		// limitSwitch = new DigitalInput(LIMIT_SWITCH);
	}

	/**
	 * Moves the elevator to a specific point based on value passed.
	 *
	 * @param setpoint
	 *            The encoder position to move to.
	 */
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public double getSetpoint() {
		return setpoint;
	}

	public boolean atSetpoint() {
		return positionController.atSetpoint();
	}

	public boolean nearSetpoint() {
		return Math.abs(driveNeoMaster.getEncoder().getPosition() - setpoint) < 5;
	}

	private void setPosition(double setpoint) {
		driveNeoMaster.setVoltage(feedforward.calculate(0)
				+ positionController.calculate(driveNeoMaster.getEncoder().getPosition(), setpoint));
	}

	public void setSpeed(double speed) {
		driveNeoMaster.set(speed);
	}

	public double getPosition() {
		return driveNeoMaster.getEncoder().getPosition();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Elevator Encoder", getPosition());
		SmartDashboard.putNumber("elevator setpoint", setpoint);

		setPosition(setpoint);
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
