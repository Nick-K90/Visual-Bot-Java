package ogame.bot;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * A lot of confusion between FX and AWT components! Some are FX and some AWT and most share the exact same name. Extra care needed.
 */
public class MainSceneController implements Initializable {

    private BufferedImage screenShot;
    private TargetImage targetImage;
    private ArrayList<TargetImage> targetImageArray = new ArrayList<>();
    private ArrayList<Target> targetArrayList = new ArrayList<>();
    private Boolean running;

    @FXML
    private Button screenshotButton, resultButton, startButton, stopButton, analyzeButton, clearTextButton, refreshListButton;
    @FXML
    private TextArea textArea;
    @FXML
    private Label mouseXPosLabel, mouseYPosLabel;
    @FXML
    private ImageView screenshotImageView, targetImageView;
    @FXML
    private ListView<TargetImage> targetImageListView = new ListView<>();

    private Task task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            //Keep them outside the loops as there is no need to constantly create new objects.
            ImageAnalysis imageAnalysis = new ImageAnalysis();
            TargetImageLoader targetImageLoader = new TargetImageLoader();
            ScreenCapture screenCapture = new ScreenCapture();
            //Need function to "refresh" the Array while the program is running. Better that than having it inside the While loop
            targetImageArray.clear();
            targetImageArray = targetImageLoader.loadTargetImages();
            setTargetImageListView(targetImageArray);
            //noinspection InfiniteLoopStatement
            while (true)
            {
                try
                {
                    while (running)
                    {
                        //if stopped doesn't start again.
                        //setMousePosLabel();
                        screenShot = screenCapture.screenCap();
                        Image image = SwingFXUtils.toFXImage(screenShot, null); //Image is FX not AWT in order to work.
                        screenshotImageView.setImage(image); //TODO Function to set the image(s) to ImageView(s).
                        textArea.clear();
                        for (int i = 0; i < targetImageArray.size(); i++)
                        {
                            if (isCancelled())
                            {
                                updateMessage("Cancelled");
                                break;
                            }

                            imageAnalysis.analyzeTargetImage(targetImageArray.get(i));

                            Target target = imageAnalysis.scanForMatch(screenShot, targetImageArray.get(i), textArea);

                            if (target.getFound())
                            {
                                targetArrayList.add(target);
                            }
                        }

                        BehaviourManager behaviourManager = new BehaviourManager();
                        behaviourManager.setTargetArrayList(targetArrayList);
                        behaviourManager.start();
                        targetArrayList.clear();
                        try
                        {
                            Thread.sleep(2000);
                        } catch (InterruptedException e)
                        {
                            if (isCancelled())
                            {
                                updateMessage("Cancelled");
                                break;
                            }
                            e.printStackTrace();
                        }

                    }
                    Thread.sleep(500);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            //  return null;

        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        screenshotButton.setOnAction(event -> {

            ScreenCapture screenCapture = new ScreenCapture();
            screenShot = screenCapture.screenCap();
            Image image = SwingFXUtils.toFXImage(screenShot, null); //Image is FX not AWT in order to work.
            screenshotImageView.setImage(image); //TODO Function to set the image(s) to ImageView(s).
        });

        resultButton.setOnAction(event -> {
            int money = 1000;
            boolean betOn = true; //True = 5 or above, False = 4 or less
            int bet = 1;
            String lost = "";
            Random r = new Random();
            int x = 1;
            int cost = 1;

            for (int i = 1; i <= 100; i++)
            {

                int rand = r.nextInt(9999);

                if (rand >= 5250)
                {
                    if (betOn == true)
                    {
                        money += bet + bet;
                        lost = "(won)";
                        System.out.println("Round: " + i + "  Bet: " + bet + lost + "  Money: " + money);
                        bet = 1;
                        money += -bet;
                        betOn = false;
                    }else
                    {

                        bet = bet * 2;
                        lost = "(lost)";
                        money += -bet;
                        System.out.println("Round: " + i + "  Bet: " + bet + lost + "  Money: " + money);
                    }

                }

                if (rand <= 4750){
                    if (betOn == false)
                    {
                        money += bet + bet;

                        lost = "(won)";
                        System.out.println("Round: " + i + "  Bet: " + bet + lost + "  Money: " + money);
                        bet = 1;
                        money += -bet;
                        betOn = true;
                    }else
                    {

                        bet = bet * 2;
                        lost = "(lost)";
                        money += -bet;
                        System.out.println("Round: " + i + "  Bet: " + bet + lost + "  Money: " + money);
                    }
                }

                if (rand > 4750 && rand < 5250)
                {
                    bet = bet * 2;
                    lost = "(lost)";
                    money += -bet;
                    System.out.println("Round: " + i + "  Bet: " + bet + lost + "  Money: " + money);
                }


                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

               /* System.out.println("Round: " + i + " Bet is: " + x + " Total Loss: " + cost);
                x = x * 2;
                cost = cost + x;*/

               if (money <= 0)
               {
                   break;
               }

            }

            System.out.println("Total number of bets: 100" + " Money: " + money);
        });

        analyzeButton.setOnAction(event -> {
            GlobalKeyListener globalKeyListener = new GlobalKeyListener();
          /*  try
            {
                //File input = new File("src/ogame-screenshot.png");
                File inputTarget = new File("src/ogame_targets/AV_crystal_mine.png");

                //screenShot = ImageIO.read(input);
                targetImage = new TargetImage(ImageIO.read(inputTarget));
                //TODO A list to hold all the X/Y coords from the image analysis to be used by the Decision Maker.
                //System.out.print(targetImage.getRaster());
                ImageAnalysis imageAnalysis = new ImageAnalysis();
                imageAnalysis.analyzeTargetImage(targetImage);
                imageAnalysis.scanForMatch(screenShot, targetImage, textArea);


            } catch (IOException e)
            {
                e.printStackTrace();
            }*/

        });


        startButton.setOnAction(event -> {

            //moveMouse();
            running = true;
            //TODO Need to check again fucking synchronized and how to possible use it by instantiating a different class
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            //  analysis();

        });

        stopButton.setOnAction(event -> {

            if (running)
            {
                running = false;

            } else
            {
                running = true;
            }
        });

        clearTextButton.setOnAction(event -> {
            textArea.clear();
        });

        refreshListButton.setOnAction(event -> {
            refreshList();
        });

    }


    private void analysis() {

        Platform.runLater(() -> {
            while (running)
            {
                ImageAnalysis imageAnalysis = new ImageAnalysis();
                TargetImageLoader targetImageLoader = new TargetImageLoader();
                targetImageArray = targetImageLoader.loadTargetImages();

                ScreenCapture screenCapture = new ScreenCapture();
                screenShot = screenCapture.screenCap();
                Image image = SwingFXUtils.toFXImage(screenShot, null); //Image is FX not AWT in order to work.
                screenshotImageView.setImage(image); //TODO Function to set the image(s) to ImageView(s).

                for (int i = 0; i < targetImageArray.size(); i++)
                {
                    imageAnalysis.analyzeTargetImage(targetImageArray.get(i));
                    imageAnalysis.scanForMatch(screenShot, targetImageArray.get(i), textArea);
                }
/*                try{
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }*/

            }
        });

    }

    /**
     * Populates the TargetImageListView by iterating through the targetImageArray.
     *
     * @param targetImageArray
     */
    private void setTargetImageListView(ArrayList<TargetImage> targetImageArray) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                targetImageListView.getItems().clear();
                for (TargetImage image : targetImageArray)
                {
                    targetImageListView.getItems().add(image);


                }
                //Sets the name of the items in the list. This is more or less the only way you can do it.
                targetImageListView.setCellFactory(param -> new ListCell<TargetImage>() {
                    @Override
                    protected void updateItem(TargetImage item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getName() == null)
                        {
                            setText(null);
                        } else
                        {
                            setText(item.getName());
                        }
                    }
                });

                targetImageListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    Image image = SwingFXUtils.toFXImage(newValue.getImage(), null);
                    targetImageView.setImage(image);
                });

                //TODO there is some bug with the Refresh List button.

            }
        });
    }

    private void setMousePosLabel() {
        Platform.runLater(() -> {
            mouseXPosLabel.setText("Mouse X: " + Integer.toString(MouseInfo.getPointerInfo().getLocation().x));
            mouseYPosLabel.setText("Mouse Y: " + Integer.toString(MouseInfo.getPointerInfo().getLocation().y));
        });
    }

    /**
     * Refreshes the list and the array with new images.
     */
    private void refreshList() {
        TargetImageLoader targetImageLoader = new TargetImageLoader();
        targetImageArray.clear();
        targetImageArray = targetImageLoader.loadTargetImages();
        setTargetImageListView(targetImageArray);
    }
}
