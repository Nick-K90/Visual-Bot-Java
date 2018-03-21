package ogame.bot;

import java.awt.image.BufferedImage;

/**
 * Created by Nikolaos Kouroumalos on 02/08/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class TargetImage  {

    private BufferedImage image;
    private String name;

    public TargetImage(BufferedImage image)
    {
        this.image = image;
        name = "NO NAME";
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
