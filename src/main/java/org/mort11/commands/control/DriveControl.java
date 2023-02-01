package org.mort11.commands.control;

import java.util.function.DoubleSupplier;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveControl extends CommandBase {
	private Drivetrain drivetrain;

	private final DoubleSupplier translationXSupplier;
	private final DoubleSupplier translationYSupplier;
	private final DoubleSupplier rotationSupplier;

	private boolean fieldOriented;

	/**
	 * @param translationXSupplier
	 *            Supplier for x-axis movement
	 * @param translationYSupplier
	 *            Supplier for y-axis movement
	 * @param rotationSupplier
	 *            Supplier for rotational movement
	 * @param fieldOriented
	 *            Whether field-oriented drive is used
	 */
	public DriveControl(DoubleSupplier translationXSupplier, DoubleSupplier translationYSupplier,
			DoubleSupplier rotationSupplier, boolean fieldOriented) {
		drivetrain = Drivetrain.getInstance();

		this.translationXSupplier = translationXSupplier;
		this.translationYSupplier = translationYSupplier;
		this.rotationSupplier = rotationSupplier;

		this.fieldOriented = fieldOriented;

		addRequirements(drivetrain);
	}

	/**
	 * Assumes robot-oriented drive
	 *
	 * @param translationXSupplier
	 *            Supplier for x-axis movement
	 * @param translationYSupplier
	 *            Supplier for y-axis movement
	 * @param rotationSupplier
	 *            Supplier for rotational movement
	 */
	public DriveControl(DoubleSupplier translationXSupplier, DoubleSupplier translationYSupplier,
			DoubleSupplier rotationSupplier) {
		this(translationXSupplier, translationYSupplier, rotationSupplier, false);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		if (fieldOriented) {
			drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(translationXSupplier.getAsDouble(),
					translationYSupplier.getAsDouble(), rotationSupplier.getAsDouble(),
					drivetrain.getGyroscopeRotation()));
		} else {
			drivetrain.drive(new ChassisSpeeds(translationXSupplier.getAsDouble(), translationYSupplier.getAsDouble(),
					rotationSupplier.getAsDouble()));
		}

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
	}
}
