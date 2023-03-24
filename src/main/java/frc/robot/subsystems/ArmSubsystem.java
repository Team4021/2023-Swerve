package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants.DriveConstants;



public class ArmSubsystem extends SubsystemBase {
    //arm Limit switch
    DigitalInput m_armPointZero = new DigitalInput(7);
    DigitalInput m_wristPointZero = new DigitalInput(9);

    //arm Motor
    WPI_TalonFX m_armMotor = new WPI_TalonFX(DriveConstants.kArmMotorCanId);
    WPI_TalonFX m_wristMotor = new WPI_TalonFX(DriveConstants.kWristMotorCanID);
    
    public void configArmMotor(){
        m_armMotor.configFactoryDefault();
        m_armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_armMotor.setNeutralMode(NeutralMode.Brake);
        m_armMotor.configFeedbackNotContinuous(false, 10);
        m_armMotor.config_kP(0, 0.0035);
        m_armMotor.config_kI(0, 0);
        m_armMotor.config_kD(0, 0.1);
        m_armMotor.config_kF(0, 0);
        m_armMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        m_armMotor.configPeakOutputForward(.3);
        m_armMotor.configPeakOutputReverse(.3);


        m_wristMotor.configFactoryDefault();
        m_wristMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_wristMotor.setNeutralMode(NeutralMode.Brake);
        m_wristMotor.configFeedbackNotContinuous(false, 10);
        m_wristMotor.config_kP(0, 0.1);
        m_wristMotor.config_kI(0, 0);
        m_wristMotor.config_kD(0, 0);
        m_wristMotor.config_kF(0, 0);
        m_wristMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        m_wristMotor.configPeakOutputForward(.3);
        m_wristMotor.configPeakOutputReverse(.3);
    }
    //checks for arm resting limit switch, defaults everything to the override if it is being used
    public void isRestingPosition (double lTSecondary, double rTSecondary, double armOverride, double deadband) {
        // if (m_armPointZero.get() == false){
        //     m_armMotor.setSelectedSensorPosition(0);
        // }
        // if (m_wristPointZero.get() == true){
        //     m_wristMotor.setSelectedSensorPosition(0);
        // }
        m_armMotor.set(TalonFXControlMode.PercentOutput, MathUtil.applyDeadband(armOverride, deadband));
        m_wristMotor.set(TalonFXControlMode.PercentOutput, (rTSecondary - lTSecondary));
    }
    //B on controller 1, arm to rest
    public void setRestingPosition(){
        m_armMotor.set(TalonFXControlMode.Position, 50000);
        m_wristMotor.set(TalonFXControlMode.Position, -70000);
    }
    //A on controller 2, Arm to low score/pickup position
    public void armPositionOne(){
        m_armMotor.set(TalonFXControlMode.Position, -40000);
        m_wristMotor.set(TalonFXControlMode.Position, -70000);
    }
    //B on controller 2, arm to mid scoring position
    public void armPositionTwo(){
        m_armMotor.set(TalonFXControlMode.Position, -180000);
        m_wristMotor.set(TalonFXControlMode.Position, -70000);
    }
    //X on controller 2, arm to slide pickup position
    public void armPositionFour(){
        m_armMotor.set(TalonFXControlMode.Position, -180000);
        m_wristMotor.set(TalonFXControlMode.Position, -70000);
    }
    //Y on controller 2, arm to high scoring position
    public void armPositionThree(){
        m_armMotor.set(TalonFXControlMode.Position, -200000);
        m_wristMotor.set(TalonFXControlMode.Position, -40000);
    }

    public void wristPositionDownPickup(){
        m_wristMotor.set(TalonFXControlMode.Position, 0);
    }
    
    public void wristPositionPickup(){
        m_wristMotor.set(TalonFXControlMode.Position, -40000);
    }
}
