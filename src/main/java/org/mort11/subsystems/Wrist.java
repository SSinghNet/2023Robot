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

	private PIDController wristController;

	private CANSparkMax driveNeo;

	/** {@link https://store.ctr-electronics.com/srx-mag-encoder/} */
	//TODO: check type
	private PWM SRXEncoder;

	private Wrist() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		SRXEncoder = new PWM(ENCODER);
		wristController = new PIDController(KP, KI, KD);
	}

	public void setSpeed(double speed) {
		//TODO: limits
		driveNeo.set(speed);
	}

	public void setPosition(double setpoint) {
		//TODO: limits
		driveNeo.setVoltage(wristController.calculate(SRXEncoder.getRaw(), setpoint));
	}

	public boolean atSetpoint() {
		return wristController.atSetpoint();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Built-In Wrist Encoder", driveNeo.getEncoder().getPosition());
		SmartDashboard.putNumber("Wrist SRX Encoder", SRXEncoder.getRaw());
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
