/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.imgproc.Imgproc;
import frc.robot.commands.PullyCommand;
import frc.robot.commands.DriveCommand;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.*;
import frc.robot.subsystems.DriveTrainSubSystem;
import frc.robot.subsystems.PullySubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final DriveTrainSubSystem DriveTrain = new DriveTrainSubSystem();
    
  public static final PullySubsystem Pully = new PullySubsystem();
	public static OI refOI = new OI();
	public static RobotMap robotMap = new RobotMap();
	
	

	public Preferences prefs;
	
	public UsbCamera topCamera;
	public UsbCamera intakeCamera;
	public VideoSink cameraServer;
	public CvSink cvsink1;
	public CvSink cvsink2;
	
	
	Command driveTrainCommand = new DriveCommand();

public static GripPipeline Pipe = new GripPipeline();
private static final int IMG_WIDTH = 320;
private static final int IMG_HEIGHT = 240;
private double centerX = 0.0;
Thread m_visionThread;
 private VisionThread visionThread;
private final Object imgLock = new Object();
public static GripPipeline pipe = new GripPipeline();


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

    // THIS IS A TEST
    
   

     

    //VISION AQUISITION
    topCamera = CameraServer.getInstance().startAutomaticCapture(0);
    intakeCamera = CameraServer.getInstance().startAutomaticCapture(1);
    cameraServer = CameraServer.getInstance().getServer();
    CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
    cvsink1 = new CvSink("blur");
		cvsink1.setSource(intakeCamera);
		cvsink1.setEnabled(true);
		cvsink2 = new CvSink("TopCameraCV");
		cvsink2.setSource(topCamera);
    cvsink2.setEnabled(true);
    intakeCamera.setFPS(15);
    intakeCamera.setExposureManual(20);
    intakeCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    topCamera.setFPS(60);
    topCamera.setResolution(360, 360);
    
    
   // VISION CODE PROCESSING

    visionThread = new VisionThread(intakeCamera, new GripPipeline(), pipeline -> {
			if (pipeline.filterContoursOutput().size()>=2) {
				Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
				Mat output = pipeline.resizeImageOutput();
				Imgproc.rectangle(output, new Point(r.x, r.y), new Point(r.x+r.width, r.y+r.height), new Scalar(255, 0, 0), 5);
				Imgproc.rectangle(output, new Point(r2.x, r2.y), new Point(r2.x+r2.width, r2.y+r2.height), new Scalar(0, 255, 0), 5);
       
				synchronized (imgLock) {
					centerX = (r.x + r2.x +(r2.width / 2)+(r.width/2))/2;
					
				}
			Imgproc.circle (
					output,                 //Matrix obj of the image
					new Point(centerX, 50),    //Center of the circle
					5,                    //Radius
					new Scalar(0, 0, 255),  //Scalar object for color
					10
				 );
				 outputStream.putFrame(output);
			}
		});
    visionThread.start();
    
  //THIS IS WHERE LIFT CODE IS
    refOI.clickedY.whileHeld(new PullyCommand("raise"));
    refOI.clickedA.whileHeld(new PullyCommand("lower"));
    
    
    // light code ++ SOLENOID
    if(refOI.clickedB)
    {robotMap.Cam.set(true);} 
    else{robotMap.Cam.set(false);}

    if (refOI.NoidIn){
      robotMap.exampleDouble.set(Value.kForward);
   }
   else if (!refOI.NoidIn && refOI.NoidOut){
    robotMap.exampleDouble.set(Value.kReverse);
   }

   else           {    
    robotMap.exampleDouble.set(Value.kOff);
   }


    // DRIVE MAP
    RobotMap.robotDriveMain = new DifferentialDrive(RobotMap.m_left, RobotMap.m_right);
   


		}
	
  
  

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {

	}

	
	@Override
	public void autonomousInit() {
		teleopInit();
		
		
	
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		teleopPeriodic();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
    // this line or comment it out.
    synchronized (imgLock) {
      centerX = this.centerX;
  }
double turn = centerX - (IMG_WIDTH / 2);

System.out.println(turn);
double sideTilt = RobotMap.accelerometer.getX();
double frontTilt = RobotMap.accelerometer.getY();
    if (refOI.clickedX && refOI.clickedB)
    {RobotMap.robotDriveMain.curvatureDrive(0.3, turn * 0.008,true);} 
    else if (frontTilt >  2.0) 
    {new PullyCommand("lower");
     RobotMap.robotDriveMain.curvatureDrive(frontTilt*0.4,0,true);}  
    else
			driveTrainCommand.start();
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
   


    //this is a test of robot capabilities 
  robotMap.c.setClosedLoopControl(true);
    if (refOI.clickedB){
			SmartDashboard.putString("Info", "Setting Intake Camera");
		    cameraServer.setSource(intakeCamera);
		} else{
			SmartDashboard.putString("Info", "Setting Top Camera");
		    cameraServer.setSource(topCamera);
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
}