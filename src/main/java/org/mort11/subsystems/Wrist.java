package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Wrist.*;

public class Wrist extends SubsystemBase {
	private static Wrist wrist;

	private CANSparkMax driveNeo;

	/** {@link https://store.ctr-electronics.com/srx-mag-encoder/} */
	// TODO: check type
	private PWM SRXEncoder;

	private PIDController wristController;

	/** target wrist position in encoder units */
	private double setpoint;

	private Wrist() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		SRXEncoder = new PWM(ENCODER);
		wristController = new PIDController(KP, KI, KD);

		SmartDashboard.putNumber("wristOffset", 0);
	}

	// public void setSetpointDegrees(double degrees) {
	// this.setpoint = degrees;
	// }

	// TODO: limits
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint + SmartDashboard.getNumber("wristOffset", 0);
	}

	private void setPosition(double setpoint) {
		driveNeo.setVoltage(wristController.calculate(driveNeo.getEncoder().getPosition(), setpoint));
	}

	public void setSpeed(double speed) {
		driveNeo.set(speed);
	}

	public boolean atSetpoint() {
		return wristController.atSetpoint();
	}

	@Override
	public void periodic() {
		// SmartDashboard.putNumber("Built-In Wrist Encoder", driveNeo.getEncoder().getPosition());
		// SmartDashboard.putNumber("SRX Wrist Encoder", SRXEncoder.getRaw());
		// SmartDashboard.putNumber("wrist setpoint", setpoint);

		setPosition(setpoint);
	}

	/**
	 * Get the Wrist object
	 */
	public static Wrist getInstance() {
		if (wrist == null) {
			wrist = new Wrist();
		}
		return wrist;
	}
}
