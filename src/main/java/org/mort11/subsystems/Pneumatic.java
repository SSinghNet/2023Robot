package org.mort11.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.util.Constants.*;

public class Pneumatic extends SubsystemBase {
	private static Pneumatic pneumatic;

	private Compressor compressor;

	private Pneumatic() {
		compressor = new Compressor(PCM, PneumaticsModuleType.REVPH);
		compressor.enableDigital();
	}

	public double getPressure() {
		return compressor.getPressure();
	}

	@Override
	public void periodic() {

	}

	public static Pneumatic getInstance() {
		if (pneumatic == null) {
			pneumatic = new Pneumatic();
		}
		return pneumatic;
	}
}
