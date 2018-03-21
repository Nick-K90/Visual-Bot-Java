package ogame.bot;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by Nikolaos Kouroumalos on 03/08/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class Actor {

    public void moveMouse(Target target) {
        try
        {

            Robot robot = new Robot();
            robot.mouseMove(target.getColumnList().get(target.getWidth() / 2), target.getRowList().get(target.getHeight() / 2));
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);


        } catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
}
