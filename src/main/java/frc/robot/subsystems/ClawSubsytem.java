package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase{
    WPI_TalonFX m_clawMotor = new WPI_TalonFX(DriveConstants.kClawMotorCanID);
    DigitalInput m_clawLimitSwitch = new DigitalInput(2);

    public void configClawMotor(){
        m_clawMotor.configFactoryDefault();
        m_clawMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_clawMotor.setNeutralMode(NeutralMode.Brake);
        m_clawMotor.configFeedbackNotContinuous(false, 10);
        m_clawMotor.config_kP(0, 0);
        m_clawMotor.config_kI(0, 0);
        m_clawMotor.config_kD(0, 0);
        m_clawMotor.config_kF(0, 0);
    }
    public void isOpen(double rXJoystickInput, double deadband){
        // if(m_clawLimitSwitch.get() == true){
        //     m_clawMotor.setSelectedSensorPosition(0);
        // }

        m_clawMotor.set(TalonFXControlMode.PercentOutput, MathUtil.applyDeadband(rXJoystickInput, deadband));
    }
    public void cone(){
        if(m_clawMotor.getSelectedSensorPosition() == 0){
            m_clawMotor.set(TalonFXControlMode.Position, 1);
        } else {
            m_clawMotor.set(TalonFXControlMode.Position, 0);
        }
    }
    public void cube(){
        if(m_clawMotor.getSelectedSensorPosition() == 0){
            m_clawMotor.set(TalonFXControlMode.Position, 2);
        } else {
            m_clawMotor.set(TalonFXControlMode.Position, 0);
        }
    }
}
