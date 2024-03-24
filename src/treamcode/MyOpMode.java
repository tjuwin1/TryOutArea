package treamcode;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
    public MyOpMode() {
    }

    public void init() {
    }

    public void loop() {
        ArrayList<CurvePoint> allPoints = new ArrayList();
        allPoints.add(new CurvePoint(0.0, 0.0, 1.0, 1.0, 50.0, Math.toRadians(50.0), 1.0));
        allPoints.add(new CurvePoint(180.0, 180.0, 1.0, 1.0, 50.0, Math.toRadians(50.0), 1.0));
        allPoints.add(new CurvePoint(220.0, 180.0, 1.0, 1.0, 50.0, Math.toRadians(50.0), 1.0));
        allPoints.add(new CurvePoint(280.0, 50.0, 1.0, 1.0, 50.0, Math.toRadians(50.0), 1.0));
        allPoints.add(new CurvePoint(180.0, 0.0, 1.0, 1.0, 50.0, Math.toRadians(50.0), 1.0));
        RobotMovement.followCurve(allPoints, Math.toRadians(90.0));
    }
}
