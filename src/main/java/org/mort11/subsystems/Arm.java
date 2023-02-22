package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase {
	private static Arm arm;

	private CANSparkMax driveNeo;

	private PIDController armController;
	private SimpleMotorFeedforward feedforward;

	/** target arm position in encoder units */
	private double setpoint;

	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);

		driveNeo.setSoftLimit(SoftLimitDirection.kReverse, TOP_LIMIT);
		driveNeo.setSoftLimit(SoftLimitDirection.kForward, BOTTOM_LIMIT);

		driveNeo.burnFlash();

		armController = new PIDController(KP, KI, KD);
		armController.setTolerance(TOLERANCE);

		feedforward = new SimpleMotorFeedforward(KS, KV, KA);

		setpoint = REST_POSITION;
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
		driveNeo.setVoltage(feedforward.calculate(0) + armController.calculate(driveNeo.getEncoder().getPosition(), setpoint));
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
