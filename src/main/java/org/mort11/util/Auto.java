package org.mort11.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class Auto {
    private static SendableChooser<Command> autoChooser;

    public static void init() {
        autoChooser = new SendableChooser<Command>();

        // By default, the nothing option is selected
        autoChooser.setDefaultOption("nothing", null);

        // put the auto chooser onto SmartDashboard
        SmartDashboard.putData(autoChooser);
    }

    /**
     * @return selected auto from auto chooser
     */
    public static Command getSelected(){
        return autoChooser.getSelected();
    }
}
