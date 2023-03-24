package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase{
    WPI_TalonFX m_clawMotor = new WPI_TalonFX(DriveConstants.kClawMotorCanID);
    DigitalInput m_clawLimitSwitch = new DigitalInput(8);

    public void configClawMotor(){
        m_clawMotor.configFactoryDefault();
        m_clawMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_clawMotor.setNeutralMode(NeutralMode.Brake);
        m_clawMotor.configFeedbackNotContinuous(false, 10);
        m_clawMotor.config_kP(0, 0.1);
        m_clawMotor.config_kI(0, 0);
        m_clawMotor.config_kD(0, 0);
        m_clawMotor.config_kF(0, 0);
        m_clawMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
    }
    public void isOpen(double rXJoystickInput, double deadband){
        // if(m_clawLimitSwitch.get() == true){
        //     m_clawMotor.setSelectedSensorPosition(0);
        // }
    

        m_clawMotor.set(TalonFXControlMode.PercentOutput, (MathUtil.applyDeadband(rXJoystickInput, deadband) / 4));
    }
    public void cone(){
        double clawEncoder = m_clawMotor.getSelectedSensorPosition();
        if(clawEncoder >= 34000 && clawEncoder <= 36000){
            while(clawEncoder <= 36000 && clawEncoder >= -34000){
            clawEncoder = m_clawMotor.getSelectedSensorPosition();
            m_clawMotor.set(TalonFXControlMode.Position, -35000);
        }
        } else {
            m_clawMotor.set(TalonFXControlMode.Position, 35000);
        }
    }
    public void cube(){
        double clawEncoder = m_clawMotor.getSelectedSensorPosition();
        if(clawEncoder >= 34000 && clawEncoder <= 36000){
            while(clawEncoder <= 36000 && clawEncoder >= 6250){
            clawEncoder = m_clawMotor.getSelectedSensorPosition();
            m_clawMotor.set(TalonFXControlMode.Position, 5250);
        } 
        } else {
            m_clawMotor.set(TalonFXControlMode.Position, 35000);
        }
    
    }
}
