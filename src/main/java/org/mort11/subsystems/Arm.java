package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase {
	private static Arm arm;

	private CANSparkMax driveNeo;

	private PIDController armController;

	/** target arm position in encoder units */
	private double setpoint;

	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		armController = new PIDController(KP, KI, KD);
		armController.setTolerance(TOLERANCE);
	}

	public PIDController getArmController() {
		return armController;
	}

	public CANSparkMax getNeoMotor() {
		return driveNeo;
	}

	/**
	 * Uses close loop controller to set the voltage of of the motor that controls
	 * the position of the arm, based on a given target position
	 *
	 * @param setpoint
	 *            Value of the position we are targeting.
	 */
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	private void setPosition(double setpoint) {
		driveNeo.setVoltage(armController.calculate(driveNeo.getEncoder().getPosition(), setpoint));
	}

	private void setSpeed(double speed) {
		driveNeo.set(speed);
	}

	public boolean atSetpoint() {
		return armController.atSetpoint();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Arm Built-In Encoder", driveNeo.getEncoder().getPosition());
		SmartDashboard.putNumber("arm setpoint", setpoint);

		setPosition(setpoint);
	}

	/**
	 * Get the arm object
	 */
	public static Arm getInstance() {
		if (arm == null) {
			arm = new Arm();
		}
		return arm;
	}

}
