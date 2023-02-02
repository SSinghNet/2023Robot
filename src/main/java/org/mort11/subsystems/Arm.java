package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Arm.*;

public class Arm extends SubsystemBase {
	private static Arm arm;

	private CANSparkMax driveNeo;

	/** {@link https://www.revrobotics.com/rev-11-1271/} */
	private Encoder boreEncoder;

	private Arm() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);

		boreEncoder = new Encoder(ENCODER_CHANNEL_A, ENCODER_CHANNEL_B);
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
}
