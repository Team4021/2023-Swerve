package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase{
    WPI_TalonFX m_clawMotor = new WPI_TalonFX(12);
    DigitalInput m_clawLimitSwitch = new DigitalInput(2);

    public void configClawMotor(){

    }
    public void isOpen(){
        if(m_clawLimitSwitch.get() == true){
            m_clawMotor.setSelectedSensorPosition(0);
        }
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
