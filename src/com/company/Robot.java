//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import RobotUtilities.MovementVars;

public class Robot {
    public static boolean usingComputer = true;
    public static double xSpeed = 0.0;
    public static double ySpeed = 0.0;
    public static double turnSpeed = 0.0;
    public static double worldXPosition;
    public static double worldYPosition;
    public static double worldAngle_rad;
    private long lastUpdateTime = 0L;

    public Robot() {
        worldXPosition = 50.0;
        worldYPosition = 50.0;
        worldAngle_rad = Math.toRadians(45.0);
    }

    public double getXPos() {
        return worldXPosition;
    }

    public double getYPos() {
        return worldYPosition;
    }

    public double getWorldAngle_rad() {
        return worldAngle_rad;
    }

    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        double elapsedTime = (double)(currentTimeMillis - this.lastUpdateTime) / 1000.0;
        this.lastUpdateTime = currentTimeMillis;
        if (!(elapsedTime > 1.0)) {
            double totalSpeed = Math.hypot(xSpeed, ySpeed);
            double angle = Math.atan2(ySpeed, xSpeed) - Math.toRadians(90.0);
            double outputAngle = worldAngle_rad + angle;
            worldXPosition += totalSpeed * Math.cos(outputAngle) * elapsedTime * 1000.0 * 0.2;
            worldYPosition += totalSpeed * Math.sin(outputAngle) * elapsedTime * 1000.0 * 0.2;
            worldAngle_rad += MovementVars.movement_turn * elapsedTime * 20.0 / 6.283185307179586;
            xSpeed += Range.clip((MovementVars.movement_x - xSpeed) / 0.2, -1.0, 1.0) * elapsedTime;
            ySpeed += Range.clip((MovementVars.movement_y - ySpeed) / 0.2, -1.0, 1.0) * elapsedTime;
            turnSpeed += Range.clip((MovementVars.movement_turn - turnSpeed) / 0.2, -1.0, 1.0) * elapsedTime;
            xSpeed *= 1.0 - elapsedTime;
            ySpeed *= 1.0 - elapsedTime;
            turnSpeed *= 1.0 - elapsedTime;
        }
    }
}
