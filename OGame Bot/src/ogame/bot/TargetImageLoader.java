package ogame.bot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolaos Kouroumalos on 26/07/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class TargetImageLoader
{

    private ArrayList<TargetImage> targetImageArray = new ArrayList<>();

    //TODO A canvas that allows the user to mark the areas he wants to check. Will need a new class for that.

    /**
     * Loads all the images in a folder and returns a BufferImage ArrayList.
     * @return A BufferImage Arraylist
     */
    public ArrayList<TargetImage> loadTargetImages()
    {
        // A try-with-resources implementation, it auto-closes the resource (Stream) even if everything goes wrong.
        try (Stream<Path> paths = Files.walk(Paths.get("src/freebitco"))) // To read from folder in the same directory -> ./Targets
        {

            List<Path> pathList = paths.filter(path -> Files.isRegularFile(path)).collect(Collectors.toList());
            for (int i = 0; i < pathList.size(); i++)
            {
                File input = new File(pathList.get(i).toString());

                TargetImage targetImage = new TargetImage(ImageIO.read(input));
                targetImage.setName(input.getName());
                targetImageArray.add(targetImage);

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return targetImageArray;
    }
}
