package treamcode;

import RobotUtilities.MovementVars;
import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Range;
import com.company.Robot;
import java.util.ArrayList;
import java.util.Iterator;
import org.opencv.core.Point;

public class RobotMovement {
    public RobotMovement() {
    }

    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle) {
        for(int i = 0; i < allPoints.size() - 1; ++i) {
            ComputerDebugging.sendLine(new FloatPoint(((CurvePoint)allPoints.get(i)).x, ((CurvePoint)allPoints.get(i)).y), new FloatPoint(((CurvePoint)allPoints.get(i + 1)).x, ((CurvePoint)allPoints.get(i + 1)).y));
        }

        CurvePoint followMe = getFollowPointPath(allPoints, new Point(Robot.worldXPosition, Robot.worldYPosition), ((CurvePoint)allPoints.get(0)).followDistance);
        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x, followMe.y));
        goToPosition(followMe.x, followMe.y, followMe.moveSpeed, followAngle, followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double followRadius) {
        CurvePoint followMe = new CurvePoint((CurvePoint)pathPoints.get(0));

        for(int i = 0; i < pathPoints.size() - 1; ++i) {
            CurvePoint startLine = (CurvePoint)pathPoints.get(i);
            CurvePoint endLine = (CurvePoint)pathPoints.get(i + 1);
            ArrayList<Point> intersections = MathFunctions.lineCircleIntersection(robotLocation, followRadius, startLine.toPoint(), endLine.toPoint());
            double closestAngle = 1.0E7;
            Iterator var11 = intersections.iterator();

            while(var11.hasNext()) {
                Point thisIntersection = (Point)var11.next();
                double angle = Math.atan2(thisIntersection.y - Robot.worldYPosition, thisIntersection.x - Robot.worldXPosition);
                double deltaAngle = Math.abs(MathFunctions.AngleWrap(angle - Robot.worldAngle_rad));
                if (deltaAngle < closestAngle) {
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }

        return followMe;
    }

    public static void goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed) {
        double distanceToTarget = Math.hypot(x - Robot.worldXPosition, y - Robot.worldYPosition);
        double absoluteAngleToTarget = Math.atan2(y - Robot.worldYPosition, x - Robot.worldXPosition);
        double relativeAngleToPoint = MathFunctions.AngleWrap(absoluteAngleToTarget - (Robot.worldAngle_rad - Math.toRadians(90.0)));
        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;
        double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        MovementVars.movement_x = movementXPower * movementSpeed;
        MovementVars.movement_y = movementYPower * movementSpeed;
        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180.0) + preferredAngle;
        MovementVars.movement_turn = Range.clip(relativeTurnAngle / Math.toRadians(30.0), -1.0, 1.0) * turnSpeed;
        if (distanceToTarget < 10.0) {
            MovementVars.movement_turn = 0.0;
        }

    }
}
