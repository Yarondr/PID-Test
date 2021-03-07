/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chassis extends SubsystemBase {

  private TalonFX talonSRX0;
  private TalonFX talonSRX1;
  private TalonFX talonSRX2;
  private TalonFX talonSRX3;

  //KV/KS = 0.04314 0.22404 Error: 0.001892
  private double kS = 0.22404;
  private double kV = 0.04314;
  private double kA = 0.149;
  private double kP = 0.00193;
  private double kD = 0.000708;

  public double power = 0.3;
  public Chassis(int TalonSRXRight1, int TalonSRXRight2, int TalonSRXLeft1, int TalonSRXLeft2) {
    talonSRX0 = new TalonFX(TalonSRXRight1);
    talonSRX1 = new TalonFX(TalonSRXRight2);
    talonSRX2 = new TalonFX(TalonSRXLeft1);
    talonSRX3 = new TalonFX(TalonSRXLeft2);
    configTalon(talonSRX0);
    configTalon(talonSRX1);
    configTalon(talonSRX2);
    configTalon(talonSRX3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setpower(int pulses, double rSpeed, double lSpeed) {
    double lff = kV+kS*lSpeed+kA*0;
    double rff = kV+kS*rSpeed+kA*0;
    talonSRX0.set(ControlMode.Velocity, pulses, DemandType.ArbitraryFeedForward, rff);
    talonSRX1.set(ControlMode.Velocity, pulses, DemandType.ArbitraryFeedForward, rff);
    talonSRX2.set(ControlMode.Velocity, pulses, DemandType.ArbitraryFeedForward, lff);
    talonSRX3.set(ControlMode.Velocity, pulses, DemandType.ArbitraryFeedForward, lff);
  }

  public int getSensorRight() {
    return (int)talonSRX0.getSelectedSensorPosition();
  }

  public int getSensorLeft() {
    return (int)talonSRX2.getSelectedSensorPosition();
  }

  public void configTalon(TalonFX talon) {
    talon.config_kD(0, 0.00193);
    talon.config_kI(0, 0.00193*0.1);
    talon.config_kP(0, 0.000708);
  }

}
