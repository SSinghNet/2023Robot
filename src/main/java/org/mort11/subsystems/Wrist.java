package org.mort11.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.Wrist.*;

public class Wrist extends SubsystemBase {
	private static Wrist wrist;

	private CANSparkMax driveNeo;

	/** {@link https://store.ctr-electronics.com/srx-mag-encoder/} */
	private DigitalInput SRXEncoder;

	private Wrist() {
		driveNeo = new CANSparkMax(DRIVE, MotorType.kBrushless);
		SRXEncoder = new DigitalInput(ENCODER);
	}

	@Override
	public void periodic() {

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
