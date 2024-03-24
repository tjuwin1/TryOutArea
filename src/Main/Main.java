//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Robot;
import treamcode.MyOpMode;
import treamcode.OpMode;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        (new Main()).run();
    }

    public void run() {
        new ComputerDebugging();
        Robot robot = new Robot();
        OpMode opMode = new MyOpMode();
        opMode.init();
        ComputerDebugging.clearLogPoints();
        long startTime = System.currentTimeMillis();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var8) {
            var8.printStackTrace();
        }

        while(true) {
            opMode.loop();

            try {
                Thread.sleep(30L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }

            robot.update();
            ComputerDebugging.sendRobotLocation(robot);
            ComputerDebugging.sendLogPoint(new FloatPoint(Robot.worldXPosition, Robot.worldYPosition));
            ComputerDebugging.markEndOfUpdate();
        }
    }
}
