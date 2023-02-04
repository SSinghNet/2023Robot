package org.mort11.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

import org.mort11.util.Constants;

public class Arm extends SubsystemBase {
	private static Arm arm;
	private PIDController armController;

	private static CANSparkMax driveNeo;

	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		armController = new PIDController(Constants.Arm.KP, Constants.Arm.KI, Constants.Arm.KD);
		armController.setTolerance(Constants.Arm.TOLERANCE);
	}

	public PIDController getArmController() {
		return armController;
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

	public static CANSparkMax getNeoMotor() {
		return driveNeo;
	}

	public void setArmPosition(double targetPosition) {
		driveNeo.setVoltage(armController.calculate(driveNeo.getEncoder().getPosition(), targetPosition));
	}

}
