package ogame.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;

/**
 * Created by Nikolaos Kouroumalos on 03/08/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class BehaviourManager {

    private ArrayList<Target> targetArrayList = new ArrayList<>();

    public void setTargetArrayList(ArrayList<Target> targetArrayList) {
        this.targetArrayList = targetArrayList;
    }

    public void start() {
        for (int i = 0; i < targetArrayList.size(); i++)
        {
            if (targetArrayList.get(i).getName().equals("win-hi.png") || targetArrayList.get(i).getName().equals("win-lo.png"))
            {
                try
                {
                    Robot robot = new Robot();
                    robot.mouseMove(719, 469); //min button
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

/*
                    robot.mouseMove(685,471);// 2x button
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
*/

                    try
                    {
                        Thread.sleep(100); //This seems to be working. wait() was causing issues!

                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    if (targetArrayList.get(i).getName().equals("win-hi.png"))
                    {
                        robot.mouseMove(1023, 458);//Lo button
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    else
                    {
                        robot.mouseMove(878, 458); //Hi button
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                } catch (AWTException e)
                {
                    e.printStackTrace();
                }

            }

            if (targetArrayList.get(i).getName().equals("lose-hi.png") || targetArrayList.get(i).getName().equals("lose-lo.png"))
            {
                try
                {
                    Robot robot = new Robot();
                    robot.mouseMove(685,471);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);


                    try
                    {
                        Thread.sleep(100); //This seems to be working. wait() was causing issues!

                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }


                    if (targetArrayList.get(i).getName().equals("lose-hi.png"))
                    {
                        robot.mouseMove(878, 458); //Hi button
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    else
                    {
                        robot.mouseMove(1023, 458);//Lo button
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }


                }
                catch (AWTException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }
}
