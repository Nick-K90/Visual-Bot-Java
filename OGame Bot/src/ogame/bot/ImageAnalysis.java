package ogame.bot;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Nikolaos Kouroumalos on 26/07/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.2
 */
public class ImageAnalysis {

    private int targetImageStartingPixel;
    //private int[][] targetImageArray;

    private ArrayList<Integer> tempColumnList = new ArrayList<>();
    private ArrayList<Integer> finalColumnList = new ArrayList<>();
    private ArrayList<Integer> tempRowList = new ArrayList<>();
    private ArrayList<Integer> finalRowList = new ArrayList<>();

    /**
     * Takes the target image, iterates through it and saves the .getRGB() values in an array. Also sets the the
     * {@link #targetImageStartingPixel}.
     *
     * @param targetImage The image to be scanned.
     */
    public void analyzeTargetImage(TargetImage targetImage) {
        targetImageStartingPixel = targetImage.getImage().getRGB(0, 0);
       /* targetImageArray = new int[targetImage.getImage().getHeight()][targetImage.getImage().getWidth()];
        //TODO merge this with the other one into one function. Also maybe swap row/column to follow (x,y) and not (y,x)
        for (int row = 0; row < targetImage.getImage().getHeight(); row++)
        {
            for (int column = 0; column < targetImage.getImage().getWidth(); column++)
            {
                if (row == 0 && column == 0)
                {
                    targetImageStartingPixel = targetImage.getImage().getRGB(column, row);
                }
                targetImageArray[row][column] = targetImage.getImage().getRGB(column, row);
            }
        }*/
    }

    /**
     * Iterates through the screenshot looking for the {@link #targetImageStartingPixel}, once found it loops using the target image's width/height
     * checking if all pixels from that part of the screenshot and the target image match. When a pixel matches it's being added to the tempColumnList,
     * and when the size of the tempColumnList equals the width * height of the target image, then everything is being transferred to the finalColumnList.
     *
     * @param screenshot  The screenshot to be scanned.
     * @param targetImage The image to be found.
     */
    public Target scanForMatch(BufferedImage screenshot, TargetImage targetImage, TextArea textArea) {
        long startMsTime = System.currentTimeMillis();
        int targetImageWidth = targetImage.getImage().getWidth();
        int targetImageHeight = targetImage.getImage().getHeight();
        finalColumnList.clear();
        tempColumnList.clear();
        tempRowList.clear();
        finalRowList.clear();


        //TODO Check the (x,y) vs (y,x) coordinate system. Currently I'm using both! Should use only 1!
        scan_loop:
        for (int row = 0; row < screenshot.getHeight(); row++)
        {
            for (int column = 0; column < screenshot.getWidth(); column++)
            {
                //TODO List with colours and check versus the list. Analyze image first.
                if (screenshot.getRGB(column, row) == targetImageStartingPixel) //Green Red: 36 Green: 225 Blue: 116. I need to be able to get targetsImage colour list.
                {
                    //TODO possible optimization here by breaking out of the loop if the condition is not met?
                    if ((row + targetImageHeight <= screenshot.getHeight() && (column + targetImageWidth) <= screenshot.getWidth()))
                    {
                        for (int targetRow = 0; targetRow <targetImageHeight; targetRow++)
                        {
                            tempRowList.add(targetRow + row);

                            for (int targetColumn = 0; targetColumn < targetImageWidth; targetColumn++)
                            {
                                if (screenshot.getRGB(targetColumn + column, targetRow + row) == targetImage.getImage().getRGB(targetColumn, targetRow))
                                {
                                    tempColumnList.add(targetColumn + column); //I need something better to store both row and column.

                                    //Checking the size to match the total of the pixels of the target image. Only then i know that i have the full correct image.
                                    if (tempColumnList.size() == targetImageWidth * targetImageHeight)
                                    {
                                        finalColumnList.addAll(tempColumnList);
                                        finalRowList.addAll(tempRowList);
                                        break scan_loop;
                                    }

                                } else
                                {
                                    tempRowList.clear();
                                    tempColumnList.clear();
                                    targetRow = targetImageHeight;
                                    //  System.out.println("NOT FOUND");
                                    break;


                                }

                            }

                        }

                    }

                }

            }
        }
        long finishMsTime = System.currentTimeMillis() - startMsTime;


        if (finalColumnList.size() > 1 && finalRowList.size() > 1)
        {
           // setTextArea(textArea, targetImageWidth, targetImageHeight, finishMsTime);
            //  System.out.println(finalColumnList.get(targetImage.getImage().getWidth() / 2) + " " + finalRowList.get(targetImage.getImage().getHeight() / 2));
            // System.out.println("Time: " + finishMsTime + "ms");
            return new Target(finalRowList, finalColumnList, targetImage.getName(), targetImageWidth, targetImageHeight, true);
        } else
        {
          //  textArea.appendText("Target Not Found." + "\nTime: " + finishMsTime + "ms\n");
            // System.out.println("END");
            return new Target(finalRowList, finalColumnList, targetImage.getName(), targetImageWidth, targetImageHeight, false);
        }
    }

    private void setTextArea(TextArea textArea, int targetImageWidth, int targetImageHeight, long finishMsTime)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textArea.appendText("X: " + finalColumnList.get(targetImageWidth / 2) + "\nY: " + finalRowList.get(targetImageHeight / 2) + "\nTime: " + finishMsTime + "ms\n");
            }
        });

    }
}
