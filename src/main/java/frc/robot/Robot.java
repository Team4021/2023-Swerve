// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //this is the arm motor, device number probaly will have to change, it is also the only motor used in this test
WPI_TalonSRX arm = new WPI_TalonSRX(11);
  //Controllers, current plan is to have two drivers, one solely for drive and one for the arm
XboxController driveCntrl = new XboxController (1); //primary driver
XboxController armCntrl = new XboxController (2); //primary arm mover, has overrides
  //Limit Switches, resting is all the way down, and one for each height we can score at
DigitalInput armDetector = new DigitalInput(0);

int armHeight = 0; //decides where the arm is based on when the limit switch has been pressed
  
double armSpeed = 0; //speed and direction of the arm
/* This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //putting arm height on dashboard
    SmartDashboard.putNumber("Arm Position", armHeight);
    //changes arm height if limit switch is pressed
    if (armSpeed > 0 && armDetector.get() == true){
      armHeight ++;
    } else if (armSpeed<0 && armDetector.get() == true){
      armHeight --;
    }
  }


  
  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  
    //B brings the arm to rest BOTH CONTROLLERS
    if ((armCntrl.getBButton() == true || driveCntrl.getBButton() == true) && armHeight>0){
      armSpeed=-.15;
    } else {
      armSpeed=0;
    }
    //A brings the arm to lowest position for scoring and pickup BOTH CONTROLLERS
    if((armCntrl.getAButton() == true || driveCntrl.getAButton() == true) && armHeight<1){
      armSpeed=.15;
    } else if((armCntrl.getAButton() == true || driveCntrl.getAButton() == true) && armHeight>1){
      armSpeed=-.15;
    } else{
      armSpeed=0;
    }
    //X brings the arm to mid scoring position ARM ONLY
    if(armCntrl.getXButton() == true && armHeight<2){
      armSpeed=.15;
    }else if(armCntrl.getXButton()==true && armHeight>2){
      armSpeed=-.15;
    }else{
      armSpeed=0;
    }
    //Y brings the arm to highest scoring positon ARM ONLY
    if(armCntrl.getYButton() == true && armHeight<3){
      armSpeed=.15;
    }else if(armCntrl.getYButton()==true && armHeight>3){
      armSpeed=-.15;
    }else{
      armSpeed=0;
    }
  }


  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
