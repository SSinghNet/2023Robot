package org.mort11.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase {
	private static Arm arm;
	public PIDController armController;

	public CANSparkMax driveNeo;


	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		armController = new PIDController(DRIVE, DRIVE, DRIVE);
	}

	@Override
	public void periodic() {

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

public void setArmPosition(double targetPosition) {
	driveNeo.setVoltage(
		armController.calculate(driveNeo.getEncoder().getPosition(), targetPosition)
		);
}

}
