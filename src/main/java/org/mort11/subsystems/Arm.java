package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase {
	private static Arm arm;
	private PIDController armController;

	private static CANSparkMax driveNeo;

	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		armController = new PIDController(KP, KI, KD);
		armController.setTolerance(TOLERANCE);
	}

	public PIDController getArmController() {
		return armController;
	}

	public static CANSparkMax getNeoMotor() {
		return driveNeo;
	}

	/**
	 * Uses close loop controller to set the voltage of of the motor that controls
	 * the position of the arm, based on a given target position
	 *
	 * @param targetPosition
	 *            Value of the position we are targeting.
	 */
	public void setPosition(double targetPosition) {
		driveNeo.setVoltage(armController.calculate(driveNeo.getEncoder().getPosition(), targetPosition));
	}

	public void setSpeed(double speed) {
		driveNeo.set(speed);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Arm Built-In Encoder", driveNeo.getEncoder().getPosition());
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
