// package org.mort11.commands.drivetrain;

// import org.mort11.subsystems.Drivetrain;
// import org.mort11.subsystems.Vision;
// import org.mort11.util.Constants.Vision.Pipeline;

// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// public class ConeNode extends CommandBase {
//     private Drivetrain drivetrain;
//     private Vision vision;
//     private boolean left;
//     private int ATID = 7;

//     public ConeNode(boolean left) {
//         this.left = left;
//         drivetrain = Drivetrain.getInstance();
//         vision = Vision.getInstance();
//         addRequirements(drivetrain, vision);
//     }

//     @Override
//     public void initialize() {
//         //TODO set ATID to whatever is closest
//         vision.setPipeline(Pipeline.APRIL_TAG, ATID);

// 		drivetrain.getAprilTagXController().reset();
// 		drivetrain.getAprilTagYController().reset();
//         drivetrain.getAprilTagOmegaController().reset();

//         if (left) {
//             if (ATID == 6 || ATID == 1) {
//                 drivetrain.getAprilTagXController().setSetpoint(-1.3);
//                 drivetrain.getAprilTagYController().setSetpoint(0);
//                 drivetrain.getAprilTagOmegaController().setSetpoint(0);
//             } else {
//                 drivetrain.getAprilTagXController().setSetpoint(-1.3);
//                 drivetrain.getAprilTagYController().setSetpoint(0);
//                 drivetrain.getAprilTagOmegaController().setSetpoint(0);
//             }
//         } else {
//             if (ATID == 8 || ATID == 3) {
//                 drivetrain.getAprilTagXController().setSetpoint(-1.3);
//                 drivetrain.getAprilTagYController().setSetpoint(0);
//                 drivetrain.getAprilTagOmegaController().setSetpoint(0);
//             } else {
//                 drivetrain.getAprilTagXController().setSetpoint(-1.3);
//                 drivetrain.getAprilTagYController().setSetpoint(0);
//                 drivetrain.getAprilTagOmegaController().setSetpoint(0);
//             }
//         }
//     }

//     @Override
//     public void execute() {
// 		double x = -drivetrain.getAprilTagXController().calculate(vision.getCamTranZ());
// 		double y = 
// 		// vision.getCamTranZ() > -1.5 ? 
// 				-drivetrain.getAprilTagYController().calculate(vision.getCamTranX());
// 				// : 0;
// 		double omega = 
// 		// vision.getCamTranZ() > -1.5 ? 
// 				-drivetrain.getAprilTagOmegaController().calculate(vision.getCamTranYaw());
// 				//  : 0;

// 		drivetrain.drive(new ChassisSpeeds(x, y, omega));
//     }

//     @Override
//     public void end(boolean interrupted) {
// 		drivetrain.drive(new ChassisSpeeds(0, 0, 0));
//     }

//     @Override
//     public boolean isFinished() {
// 		return !vision.hasTarget() 
//         || (drivetrain.getAprilTagXController().atSetpoint() && drivetrain.getAprilTagYController().atSetpoint()
//                 && drivetrain.getAprilTagOmegaController().atSetpoint());
//     }
// }
