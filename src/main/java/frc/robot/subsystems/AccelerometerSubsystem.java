// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AccelerometerSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static final double Gravity = 9.8;
  private final BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

  private double VelX = 0.0;
  private double lasttime = Timer.getFPGATimestamp();

  public AccelerometerSubsystem() {
    ShuffleboardTab tab = Shuffleboard.getTab("Accelerometer");

    tab.addDouble("X:", accelerometer::getX)
        .withWidget(BuiltInWidgets.kAccelerometer);
    tab.addDouble("Y:", accelerometer::getY)
        .withWidget(BuiltInWidgets.kAccelerometer);
    tab.addDouble("Z:", accelerometer::getZ)
        .withWidget(BuiltInWidgets.kAccelerometer);

    tab.addDouble("Speed:", () -> VelX)
        .withWidget(BuiltInWidgets.kTextView);
  }
  public double getX() { return accelerometer.getX(); }
  public double getY() { return accelerometer.getY(); }
  public double getZ() { return accelerometer.getZ(); }
  public double getEstimatedVelocityX() { return VelX; }

  public void resetVelocity() { VelX = 0.0; }

  @Override
  public void periodic() {
    double now = Timer.getFPGATimestamp();
    double dt = now - lasttime;
    lasttime = now;

    // Crude integration. Noise and any robot tilt will accumulate error.
    VelX += accelerometer.getX() * Gravity * dt;
  }
}
