package ogame.bot;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nikolaos Kouroumalos on 26/07/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class ScreenCapture {

    private BufferedImage screenCapture;

    public BufferedImage screenCap() {

        try
        {
            Robot robot = new Robot();
            // capture the whole screen
            screenCapture = robot.createScreenCapture(
                    new Rectangle(635,433,468,100));
            //Code below is to take screenshot of the whole screen.
            //Toolkit.getDefaultToolkit().getScreenSize()

/*            try {
                // Save as JPEG
                //File file = new File("screencapture.jpg");
                //ImageIO.write(screencapture, "jpg", file);

                // Save as PNG
                File file = new File("screencapture.png");
                ImageIO.write(screenCapture, "png", file);
            } catch (IOException e) {
                //TODO Custom pop up error message with the ability to save the error in a txt(?).
            }*/
        } catch (AWTException e)
        {

        }
        return screenCapture;
    }

}
