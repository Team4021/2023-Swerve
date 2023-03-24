// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;

import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.ClawSubsytem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  public final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ArmSubsystem m_armMovement = new ArmSubsystem();
  private final ClawSubsytem m_clawControl = new ClawSubsytem();

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

  // Arm Controller
  XboxController m_secondaryController = new XboxController(OIConstants.kSecondaryControllerPort);
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_clawControl.configClawMotor();
    m_armMovement.configArmMotor();
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands

    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                 MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            m_robotDrive));
    
    m_clawControl.setDefaultCommand( 
        // Defaults to checking if limit switch is triggered and sets encoder to 0 when true.
        new RunCommand(
            () -> m_clawControl.isOpen(m_secondaryController.getRightX(), OIConstants.kSecondaryDeadband),
             m_clawControl));

    m_armMovement.setDefaultCommand(
      new RunCommand(
      () -> m_armMovement.isRestingPosition(m_secondaryController.getLeftTriggerAxis(), m_secondaryController.getRightTriggerAxis(), m_secondaryController.getLeftY(), OIConstants.kSecondaryDeadband),
      m_armMovement));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    
    new JoystickButton(m_driverController, Button.kBack.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));
   //B on Driver controller brings arm to rest
    new JoystickButton(m_driverController, Button.kB.value)
        .whileTrue(new RunCommand(
            () -> m_armMovement.setRestingPosition(), 
            m_armMovement));
    //A on both controllers brings arm to pickup/low score position
    new JoystickButton(m_driverController, Button.kA.value)
    .whileTrue(new RunCommand(
      () -> m_armMovement.armPositionOne(), m_armMovement));

    new JoystickButton(m_secondaryController, Button.kA.value)
      .whileTrue(new RunCommand(
        () -> m_armMovement.armPositionOne(), m_armMovement));
    //B on secondary controller brings arm to mid score position
    new JoystickButton(m_secondaryController, Button.kB.value)
      .whileTrue(new RunCommand(
        () -> m_armMovement.armPositionTwo(), m_armMovement));
  
    //X on Secondary controller brings arm to slide pickup position
    new JoystickButton(m_secondaryController, Button.kX.value)
      .whileTrue(new RunCommand(
        () -> m_armMovement.armPositionFour(), m_armMovement));
  
    //Y on Secondary controller brings arm to high score position
    new JoystickButton(m_secondaryController, Button.kY.value)
    .whileTrue(new RunCommand(
      () -> m_armMovement.armPositionThree(), m_armMovement));

    // X Button on controller 1, pickup cone or open
    new JoystickButton(m_driverController, Button.kX.value)
        .whileTrue(new RunCommand(
            () -> m_clawControl.cone(),
            m_clawControl));

    // Y Button on controller 1, pickup cube or open
    new JoystickButton(m_driverController, Button.kY.value)
        .whileTrue(new RunCommand(
            () -> m_clawControl.cube(), 
            m_clawControl));

    // Select Button on controller 1, lowers max speed of robot.
    new JoystickButton(m_driverController, Button.kStart.value)
        .onTrue(new RunCommand(
            () -> m_robotDrive.toggleTransmission(),
            m_robotDrive));
    
    new JoystickButton(m_driverController, Button.kLeftBumper.value)
        .onTrue(new RunCommand(
          () -> m_armMovement.wristPositionDownPickup(), 
          m_armMovement));

    new JoystickButton(m_driverController, Button.kRightBumper.value)
        .onTrue(new RunCommand(
          () -> m_armMovement.wristPositionPickup(),
          m_armMovement));
  }
}



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
