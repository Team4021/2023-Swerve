package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants.DriveConstants;



public class ArmSubsystem extends SubsystemBase {
    //arm Limit switch
    DigitalInput m_armPointZero = new DigitalInput (0);
    DigitalInput m_wristPointZero = new DigitalInput(1);

    //arm Motor
    WPI_TalonFX m_armMotor = new WPI_TalonFX(DriveConstants.kArmMotorCanId);
    WPI_TalonFX m_wristMotor = new WPI_TalonFX(DriveConstants.kWristMotorCanID);
    
    public void configArmMotor(){
        m_armMotor.configFactoryDefault();
        m_armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_armMotor.setNeutralMode(NeutralMode.Brake);
        m_armMotor.configFeedbackNotContinuous(false, 10);
        m_wristMotor.configFactoryDefault();
        m_wristMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_wristMotor.setNeutralMode(NeutralMode.Brake);
        m_wristMotor.configFeedbackNotContinuous(false, 10);
    }
    //checks for arm resting limit switch
    public void isRestingPosition () {
        if (m_armPointZero.get() == true){
            m_armMotor.setSelectedSensorPosition(0);
        }
        if (m_wristPointZero.get() == true){
            m_wristMotor.setSelectedSensorPosition(0);
        }
    }
    //B on controller 1, arm to rest
    public void setRestingPosition(){
        m_armMotor.set(TalonFXControlMode.Position, 0);
        m_wristMotor.set(TalonFXControlMode.Position, 0);
    }
    //A on controller 2, Arm to low score/pickup position
    public void armPositionOne(){
        m_armMotor.set(TalonFXControlMode.Position, 1);
        m_wristMotor.set(TalonFXControlMode.Position, -1);
    }
    //B on controller 2, arm to mid scoring position
    public void armPositionTwo(){
        m_armMotor.set(TalonFXControlMode.Position, 2);
        m_wristMotor.set(TalonFXControlMode.Position, 0);
    }
    //X on controller 2, arm to slide pickup position
    public void armPositionFour(){
        m_armMotor.set(TalonFXControlMode.Position, 4);
        m_wristMotor.set(TalonFXControlMode.Position, 0);
    }
    //Y on controller 2, arm to high scoring position
    public void armPositionThree(){
        m_armMotor.set(TalonFXControlMode.Position, 3);
        m_wristMotor.set(TalonFXControlMode.Position, 0);
    }
}
