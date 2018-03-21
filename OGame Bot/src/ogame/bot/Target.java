package ogame.bot;

import java.util.ArrayList;

/**
 * Created by Nikolaos Kouroumalos on 02/08/2017.
 * <br>Holds all the information about the target image. Name, row/column of where the image was found in the screenshot,
 * width/height of the image and has a boolean to that is set to true if a target does exist and to false if not
 * (being checked at MainSceneController before added to the targetArrayList).
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class Target {

    private ArrayList<Integer> rowList = new ArrayList<>();
    private ArrayList<Integer> columnList = new ArrayList<>();
    private int width, height;
    private String name;
    private Boolean found;

    public Target (ArrayList<Integer> rowList, ArrayList<Integer> columnList, String name, int width, int height, Boolean found)
    {
        this.rowList = rowList;
        this.columnList = columnList;
        this.name = name;
        this.width = width;
        this.height = height;
        this.found = found;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getRowList() {
        return rowList;
    }

    public ArrayList<Integer> getColumnList() {
        return columnList;
    }

    public Boolean getFound() {
        return found;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
