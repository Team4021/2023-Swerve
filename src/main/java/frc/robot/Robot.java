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
XboxController driveCntrl = new XboxController (1);
XboxController armCntrl = new XboxController (2);
  //Limit Switches, resting is all the way down, and one for each height we can score at
DigitalInput restingLimit = new DigitalInput(0);
DigitalInput lowLimit = new DigitalInput (1);
DigitalInput midLimit = new DigitalInput (2);
DigitalInput highLimit = new DigitalInput (3);
int armHeight = 0; //based on what limit switch was last pressed, we know where the arm is and what direction it needs to go to get to its next spot.
  
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
  public void robotPeriodic() {}

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
    //sets the arm height to the last activated limit switch so we know which direction it needs to move
    if (restingLimit.get()==true){
      armHeight = 0;
    } else if (lowLimit.get()==true){
      armHeight = 1;
    } else if (midLimit.get()==true){
      armHeight = 2;
    } else if (highLimit.get()==true){
      armHeight = 3;
    }
    //Quick adjust for arm speeds up and down
    double armSpeedUp = .15;
    double armSpeedDown = -.15;
   //pressing B brings the motor back down to where it starts
    if(armCntrl.getBButton()==true && restingLimit.get() == false){
      arm.set(armSpeedDown);
    } else {
      arm.set(0);
    }
    //pressing A brings the motor to lowest scoring position
    if (armCntrl.getAButton()==true && lowLimit.get()==false && armHeight<1){
      arm.set(armSpeedUp);
    }else if (armCntrl.getAButton()==true && lowLimit.get()==false && armHeight>1){
      arm.set(armSpeedDown);
    }else {arm.set (0);
    }
    //pressing X brings motor to middle scoring position
    if(armCntrl.getXButton()==true && midLimit.get()==false && armHeight<2){
      arm.set(armSpeedUp);
    }else if (armCntrl.getXButton()==true && midLimit.get()==false && armHeight>2){
      arm.set(armSpeedDown);
    }else {
      arm.set(0);
    }
    //pressing Y brings motor to highest scoring position
    if(armCntrl.getYButton()==true && highLimit.get()==false){
      arm.set(armSpeedUp);
    }else {arm.set(0);
    }
    //Right bumper moves arm up regardless of position as an override
    if(armCntrl.getRightBumper()==true){
      arm.set(armSpeedUp);
    }else {
      arm.set(0);
    }
    //left bumper moves arm down regardless of position as an override
    if(armCntrl.getLeftBumper()==true) {
      arm.set(armSpeedDown);
    } else{
      arm.set(0);
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
