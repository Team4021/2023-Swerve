// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// Their Imports
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Our Imports
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;  
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj2.command.PIDCommand;

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

  // motors
    // drive motors
    WPI_TalonSRX leftFront = new WPI_TalonSRX(1);
    WPI_TalonSRX rightFront = new WPI_TalonSRX(2);
    WPI_TalonSRX rightBack = new WPI_TalonSRX(3);
    WPI_TalonSRX leftBack = new WPI_TalonSRX(4);

    // turn motors
    WPI_TalonSRX leftFrontTurn = new WPI_TalonSRX(5);
    WPI_TalonSRX rightFrontTurn = new WPI_TalonSRX(6);
    WPI_TalonSRX rightBackTurn = new WPI_TalonSRX(7);
    WPI_TalonSRX leftBackTurn = new WPI_TalonSRX(8);

    // drive control group
     //MotorControllerGroup driveLeft = new MotorControllerGroup(leftFront, leftBack);
     //MotorControllerGroup driveRight = new MotorControllerGroup(rightFront, rightBack);
    //MotorControllerGroup turn = new MotorControllerGroup(leftFrontTurn, rightFrontTurn, rightBackTurn, leftBackTurn); 

  // encoders
    AnalogEncoder leftFrontEncoder = new AnalogEncoder(3);
    AnalogEncoder rightFrontEncoder = new AnalogEncoder(0);
    AnalogEncoder rightBackEncoder = new AnalogEncoder(1);
    AnalogEncoder leftBackEncoder = new AnalogEncoder(2);
  
  double negythingy;
  double degreeInput;
  double tanOutput;
  // joystick
  Joystick mover = new Joystick(1);

  double leftJoystickY;
  double leftJoystickX;
  double rightJoystickX;
  double leftJoystickYDead;
  double leftJoystickXDead;
  double rightJoystickXDead; 

  double motorChanger = 1;

  // dimmensions
  final double W = 32.5;
  final double L = 28;  
  double r = Math.sqrt ((L * L) + (W * W));


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    leftFrontEncoder.reset();
    rightFrontEncoder.reset();
    rightBackEncoder.reset();
    leftBackEncoder.reset();

    // sets distance to 288 for 1 rotation (360*.8 to account for gear ratio)
    leftFrontEncoder.setDistancePerRotation(360./1.);
    rightFrontEncoder.setDistancePerRotation(360./1.);
    rightBackEncoder.setDistancePerRotation(360./1.);
    leftBackEncoder.setDistancePerRotation(360./1.);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
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
  public void teleopInit() {
    //leftFrontEncoder.reset();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {


    double leftJoystickY = mover.getRawAxis(1);
    double leftJoystickX = mover.getRawAxis(0);
    double rightJoystickX = mover.getRawAxis(4);

    if (leftJoystickY > 0.05) {
      leftJoystickYDead = leftJoystickY;
   } else if (leftJoystickY < -0.05) {
      leftJoystickYDead = leftJoystickY;
   } else {
      leftJoystickYDead = 0;
   }
   if (leftJoystickX > 0.05) {
      leftJoystickXDead = leftJoystickX;
   } else if (leftJoystickX < -0.05) {
      leftJoystickXDead = leftJoystickX;
   } else {
      leftJoystickXDead = 0;
   }
   if (rightJoystickX > 0.05) {
      rightJoystickXDead = rightJoystickX;
      //rightJoystickXDead = 0;
   } else if (rightJoystickX <-0.05) {
      rightJoystickXDead = rightJoystickX;
      //rightJoystickXDead = 0;
   } else{
      rightJoystickXDead = 0;
   }

    double a = leftJoystickXDead + rightJoystickXDead;
    double b = leftJoystickXDead - rightJoystickXDead;
    double c = leftJoystickYDead + rightJoystickXDead;
    double d = leftJoystickYDead - rightJoystickXDead;

    double backRightSpeed = Math.sqrt ((a * a) + (d * d));
    double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
    double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
    double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));
 
    double backRightAngle = Math.atan2 (a, d) / Math.PI*180;
    double backLeftAngle = Math.atan2 (a, c) / Math.PI*180;
    double frontRightAngle = Math.atan2 (b, d) / Math.PI*180;
    double frontLeftAngle = Math.atan2 (b, c) / Math.PI*180; 



    double setpointBRA = backRightAngle;
    double setpointBLA = backLeftAngle;
    double setpointFRA = frontRightAngle;
    double setpointFLA = frontLeftAngle;
    
    double errorBRA = setpointBRA - rightBackEncoder.getDistance();
    double errorBLA = setpointBLA - leftBackEncoder.getDistance();
    double errorFRA = setpointFRA - rightFrontEncoder.getDistance();
    double errorFLA = setpointFLA - leftFrontEncoder.getDistance();

    double P = .04;
    double BRA = P*errorBRA;
    double BLA = P*errorBLA;
    double FRA = P*errorFRA;
    double FLA = P*errorFLA; 
     if (rightFrontEncoder.getDistance() < frontRightAngle-.005) {
      rightFrontTurn.set(FRA);
    } else if (rightFrontEncoder.getDistance() > frontRightAngle+.005) {
      rightFrontTurn.set(FRA);
    }  else {
      rightFrontTurn.set(0);
    }
    if (leftFrontEncoder.getDistance() < frontLeftAngle-.005) {
      leftFrontTurn.set(FLA);
    } else if (leftFrontEncoder.getDistance() > frontLeftAngle+.005) {
      leftFrontTurn.set(FLA);
    } else {
      leftFrontTurn.set(0);
    } 
     if (rightBackEncoder.getDistance() < backRightAngle-.005) {
      rightBackTurn.set(BRA);
    } else if (rightBackEncoder.getDistance() > backRightAngle+.005) {
      rightBackTurn.set(BRA);
    } else {
      rightBackTurn.set(0);
    }
    if (leftBackEncoder.getDistance() < backLeftAngle-.005) {
      leftBackTurn.set(BLA);
    } else if (leftBackEncoder.getDistance() > backLeftAngle+.005) {
      leftBackTurn.set(BLA);
    } else {
      leftBackTurn.set(0);
    } 
    SmartDashboard.putNumber("LeftFrontEncoder", leftFrontEncoder.getDistance());
    SmartDashboard.putNumber("RightFrontAngle", frontRightAngle);
    SmartDashboard.putNumber("RightFrontEncoder", rightFrontEncoder.getDistance());
    SmartDashboard.putNumber("LeftFrontAngle", frontLeftAngle);
    SmartDashboard.putNumber("LeftFrontDistance", leftFrontEncoder.getDistancePerRotation());
    SmartDashboard.putNumber("LeftFrontRotSinceRes", leftFrontEncoder.get());
    SmartDashboard.putNumber("LeftBackEncoder", leftBackEncoder.getDistance());
    SmartDashboard.putNumber("deadY", leftJoystickYDead);
    SmartDashboard.putNumber("BackRightSpeed", backRightSpeed);

    leftBack.set(backLeftSpeed*motorChanger);
    rightBack.set(-backRightSpeed*motorChanger);
    leftFront.set(frontLeftSpeed*motorChanger);
    rightFront.set(-frontRightSpeed*motorChanger);



    
    //SmartDashboard.putNumber("frontRightAngle", frontRightAngle*180);
    //SmartDashboard.putNumber("frontRightSpeed", frontRightSpeed);

    /*
    // controller axis variables
    double x = mover.getRawAxis(0); // left joystick, left right
    double y = mover.getRawAxis(1); // left joystick, up down
    // double inPlace = mover.getRawAxis(4); // right joystick, left right
   
    if (y > 0) {
     negythingy = -1;
    } else {
       negythingy = 1;
    }

    // drive
    driveLeft.set(-Math.sqrt((y)*(y)+(x)*(x))/2* negythingy);
    driveRight.set(Math.sqrt((y)*(y)+(x)*(x))/2* negythingy); 

    // turn;
      // axis to degrees
      tanOutput = (Math.atan((-mover.getRawAxis(1))/mover.getRawAxis(0))*(180/Math.PI));
      /* if (mover.getRawAxis(0)<1) {

      } *//* 
      SmartDashboard.putNumber("Degrees", tanOutput);

    if (rightFrontEncoder.getDistance() < degreeInput) {
      rightFrontTurn.set(.5);
    }  */

   /*  if (mover.getRawButton(1)) {
      rightFrontTurn.set(.3);
      SmartDashboard.putNumber("RightFrontEncoder", rightFrontEncoder.getDistance());
    } else {
      rightFrontTurn.set(0);
    }
    if (mover.getRawButton(2)) {
      if (rightFrontEncoder.getDistance() < 1.1) {
        rightFrontTurn.set(.3);
      } else {
        rightFrontTurn.set(0);
      }
    } else {
      rightFrontTurn.set(0);
    } */
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
  public void testPeriodic() {}
}
