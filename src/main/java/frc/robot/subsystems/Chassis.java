/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FeedForward;

public class Chassis extends SubsystemBase {

  private TalonFX talonSRX0;
  private TalonFX talonSRX1;
  private TalonFX talonSRX2;
  private TalonFX talonSRX3;

  //KV/KS = 0.04314 0.22404 Error: 0.001892

  public Chassis(int TalonSRXRight1, int TalonSRXRight2, int TalonSRXLeft1, int TalonSRXLeft2) {
    talonSRX0 = new TalonFX(TalonSRXRight1);
    talonSRX1 = new TalonFX(TalonSRXRight2);
    talonSRX2 = new TalonFX(TalonSRXLeft1);
    talonSRX3 = new TalonFX(TalonSRXLeft2);
    talonSRX1.follow(talonSRX0);
    talonSRX3.follow(talonSRX2);
    talonSRX0.setInverted(true);
    talonSRX1.setInverted(true);
    configTalon(talonSRX0);
    configTalon(talonSRX1);
    configTalon(talonSRX2);
    configTalon(talonSRX3);
    SmartDashboard.putNumber("Last Right Speed", 0);
    SmartDashboard.putNumber("Last Left Speed", 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setpower(double rSpeed, double lSpeed) {
    double lff = FeedForward.feedForwardLeftPower(lSpeed, rSpeed);
    double rff = FeedForward.feedForwardRightPower(lSpeed, rSpeed);
    System.out.print("Left FF: " + lff);
    System.out.print("Right FF: " + rff);
    talonSRX0.set(ControlMode.PercentOutput, rff);
    talonSRX2.set(ControlMode.PercentOutput, lff);
  }

  public int getSensorRight() {
    return (int)talonSRX0.getSelectedSensorPosition();
  }

  public int getSensorLeft() {
    return (int)talonSRX2.getSelectedSensorPosition();
  }

  public double getVelocityRight() {
    return talonSRX0.getSelectedSensorVelocity();
  }

  public double getVelocityLeft() {
    return talonSRX2.getSelectedSensorVelocity();
  }

  public void configTalon(TalonFX talon) {
    talon.config_kD(0, 0.00193);
    talon.config_kI(0, 0.00193*0.1);
    talon.config_kP(0, 0.000708);
  }

}
