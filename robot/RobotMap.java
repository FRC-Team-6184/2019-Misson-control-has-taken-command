/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.GripPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
 public static Spark pulleyMotor = new Spark(3);
 public static PWMTalonSRX m_frontLeft = new PWMTalonSRX(1);
 public static  Talon m_rearLeft = new Talon(2);
 public static PWMTalonSRX m_bothright = new PWMTalonSRX(0);
public static SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
public static SpeedControllerGroup m_right = new SpeedControllerGroup(m_bothright);
public static DifferentialDrive robotDriveMain;
public static DoubleSolenoid exampleDouble = new DoubleSolenoid(0, 7);
public static   Compressor c = new Compressor();
public static Solenoid Cam = new Solenoid(1);
}