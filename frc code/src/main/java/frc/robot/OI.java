/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public XboxController xbox = new XboxController(1);
  
  Button clickedA = new JoystickButton( xbox, 1);
 // Button clickedB = new JoystickButton( xbox, 2);
  Button clickedY = new JoystickButton( xbox, 4);
  //Button clickedX = new JoystickButton( xbox, 3);
  Button NoidIn = new JoystickButton (xbox, 5);
  Button NoidOut = new JoystickButton( xbox, 6);

//boolean clickedA = xbox.getAButton();
boolean clickedB = xbox.getBButton();
// boolean clickedY = xbox.getYButton();
boolean clickedX = xbox.getXButton();
// 1 -> A
//2 -> B
//3 -> X
//4 -> Y
//5 -> Left Bumper
//6 -> Right Bumber
//7 -> ???
//8 -> ???
//9 -> Left Stick Button
//10 -> Right Stick Button
//11 -> ???
//12 -> ???
 
       boolean clicked7 = xbox.getBButton();
public Joystick m_rightStick = new Joystick(0);            
Double driveSpeed =  m_rightStick.getThrottle();

}
