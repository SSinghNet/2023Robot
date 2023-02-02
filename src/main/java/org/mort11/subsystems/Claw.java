package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Claw.*;

import org.mort11.util.Constants;

public class Claw extends SubsystemBase {
	private static Claw claw;

	private CANSparkMax intakeNeoMaster;
	private CANSparkMax intakeNeoFollower;

	private DigitalInput irSensor;

	private DoubleSolenoid piston;

	private CANSparkMax wristNeo;

	/** {@link https://store.ctr-electronics.com/srx-mag-encoder/} */
	private DigitalInput wristSRXEncoder;

	private Claw() {
		intakeNeoMaster = new CANSparkMax(INTAKE_MASTER, MotorType.kBrushless);
		intakeNeoFollower = new CANSparkMax(INTAKE_FOLLOWER, MotorType.kBrushless);

		intakeNeoFollower.follow(intakeNeoMaster);

		irSensor = new DigitalInput(IR_SENSOR);

		piston = new DoubleSolenoid(Constants.PCM, PneumaticsModuleType.REVPH, PISTON_FORWARD, PISTON_BACKWARD);

		wristNeo = new CANSparkMax(WRIST, MotorType.kBrushless);

		wristSRXEncoder = new DigitalInput(WRIST_ENCODER);
	}

	@Override
	public void periodic() {

	}

	/**
	 * Get the claw object
	 */
	public static Claw getInstance() {
		if (claw == null) {
			claw = new Claw();
		}
		return claw;
	}

}
