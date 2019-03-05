/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import frc.robot.Robot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
/**
 * An example command.  You can replace me with your own command.
 */
public class DriveCommand extends Command {
private Double speed;
private Double rotation;
private Boolean value = true;
Preferences prefs = Preferences.getInstance();
public DriveCommand(Double timeIn, Double speedIn, Double rotationIn) {
    // Use requires() here to declare subsystem dependencies
        requires(Robot.DriveTrain);
        setTimeout(timeIn);
       double speed = speedIn;
       double rotation = rotationIn;
}


public DriveCommand(){
    // Use requires() here to declare subsystem dependencies
requires(Robot.DriveTrain);
}
  // Called just before this Command runs the first time

protected void initialize(){}

    // Called repeatedly when this Command is scheduled to run
protected void execute() {
    if(speed != null) {
        Robot.DriveTrain.drive(speed, rotation, value);
    } else {
        Robot.DriveTrain.drive(Robot.refOI.m_rightStick.getY(), Robot.refOI.m_rightStick.getX(), true);
    }
}

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return isTimedOut();
}

// Called once after isFinished returns true
protected void end() {
        Robot.DriveTrain.stop(); // stop robot
}

// Called when another command which requires one or more of the same
// subsystems is scheduled to run
protected void interrupted() {
        Robot.DriveTrain.stop();
}
}







