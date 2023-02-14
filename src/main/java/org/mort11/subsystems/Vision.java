package org.mort11.subsystems;

import org.mort11.util.Constants.Vision.Pipeline;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
	private static Vision vision;

	private NetworkTable llTable;

	private Vision() {
		llTable = NetworkTableInstance.getDefault().getTable("limelight");
	}

	public boolean hasTarget() {
		return llTable.getEntry("tv").getInteger(0) == 1;
	}

	public double getTargetArea() {
		return llTable.getEntry("ta").getDouble(0);
	}

	public void setPipeline(int id) {
		llTable.getEntry("pipeline").setNumber(id);
	}

	public void setPipeline(Pipeline pipe) {
		setPipeline(pipe.getId());
	}

	public void setPipeline(Pipeline pipe, int ATID) {
		setPipeline(pipe.getId(ATID));
	}

	public Pipeline getPipeline() {
		return Pipeline.getPipeline((int) llTable.getEntry("pipeline").getInteger(0));
	}

	// Translation (x, y, z) Rotation(pitch, yaw, roll)
	public Number[] getCamTran() {
		return llTable.getEntry("camtran").getNumberArray(new Number[0]);
	}

	public double getCamTranX() {
		return (double) getCamTran()[0];
	}

	public double getCamTranY() {
		return (double) getCamTran()[1];
	}

	public double getCamTranZ() {
		return (double) getCamTran()[2];
	}

	public double getCamTranPitch() {
		return (double) getCamTran()[3];
	}

	public double getCamTranYaw() {
		return (double) getCamTran()[4];
	}

	public double getCamTranRoll() {
		return (double) getCamTran()[5];
	}

	public Number[] getPose() {
		return llTable.getEntry("botpose").getNumberArray(null);
	}

	public int getATId() {
		return (int) llTable.getEntry("tid").getInteger(0);
	}

	public static Vision getInstance() {
		if (vision == null) {
			vision = new Vision();
		}
		return vision;
	}
}
