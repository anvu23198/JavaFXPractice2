package project3;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown;
    private Timeline timeline;


    private final int TIMER = 15;
    private int count = 0;
    private int score = 0;
    private final ColorsEnum []colorsString = ColorsEnum.values();
    private final ColorsEnum []colors = ColorsEnum.values();
    private Color trapColor;
    private boolean truePrevious;
    private boolean started = false;
    private BorderPane secondBorder;
    private Timeline timeline1;
    private int colorIndex = 0;
    
    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();

    }
    
    public void endOfGame()
    {
        //TODO complete this method as required.
    	borderPane.getChildren().clear();
    	VBox vbox = new VBox(20);
    	Text yourScore = new Text("Your Scores: " + score);
    	yourScore.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 40));
    	Button playAgain = new Button("Play again");
    	vbox.getChildren().addAll(yourScore,playAgain);
    	vbox.setAlignment(Pos.CENTER);
    	borderPane.setCenter(vbox);
    	
    	playAgain.setOnAction(e ->{
    		score = 0;
    		count = 0;
    		started = false;
        	startPlay();
    	});
    	
    }


    public void checkChoice(Text choice)
    {
        //TODO complete this method as required.
		Color choiceColor = Color.valueOf(choice.getText());
    	if(choiceColor == trapColor) {
    		truePrevious = true;
    		score++;
    	}
    	else
    		truePrevious = false;
        //Do NOT add any code after this comment
        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        //TODO complete this method as required.

    	int randomNumber = (int)(Math.random() * 7) + 0;
    	int randomColor = (int)(Math.random() * 7) + 0;
    	Text trap = new Text(String.valueOf(colorsString[randomNumber] + ""));
    	trap.setFont(Font.font("Times New Roman", FontWeight.BOLD, 60));
    	trap.setFill(Color.valueOf(colors[randomColor] + ""));
    	BorderPane.setAlignment(trap, Pos.CENTER);
    	borderPane.setTop(trap);    
    	trapColor = Color.valueOf(colors[randomColor]+"");


    }
    
    public void colorNameOptions()
    {
        //TODO complete this method as required.
    	
    	//random colors string
    	Random rand = new Random();
    	for (int i = 0; i < colorsString.length; i++) {
	    	int index = rand.nextInt(colorsString.length - i) + i;
	    	ColorsEnum temp   = colorsString[i];
	    	colorsString[i]     = colorsString[index];
	    	colorsString[index] = temp;
    	}
    	
    	//random colors
    	Random randColor = new Random();
    	for (int i = 0; i < colors.length; i++) {
	    	int index = randColor.nextInt(colors.length - i) + i;
	    	ColorsEnum temp   = colors[i];
	    	colors[i]     = colors[index];
	    	colors[index] = temp;
    	}
    	

    	FlowPane flowPane = new FlowPane();
    	flowPane.setHgap(20); 
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(20, 35, 2, 35));
    	for (int i = 0; i < colorsString.length; i++) {
	    	Text textChoice = new Text(String.valueOf(colorsString[i] + ""));
	    	textChoice.setFill(Color.valueOf(colors[i] + ""));
	    	textChoice.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
	        flowPane.getChildren().add(textChoice);
	        textChoice.setOnMouseClicked(e ->{
	        	checkChoice(textChoice);
	        });
    	}

    	borderPane.setCenter(flowPane);

    	secondBorder = new BorderPane();
    	Text scoreText = new Text("Score: " + score);
    	scoreText.setFont(Font.font("Times New Roman", 20));
    	BorderPane.setAlignment(scoreText, Pos.BOTTOM_LEFT);
    	secondBorder.setLeft(scoreText);    
    	
    	HBox hbox = new HBox();
    	Text timeText = new Text("Time: ");
    	timeText.setFont(Font.font("Times New Roman", 20));

    	txtCountDown = new Text(count + "");
    	txtCountDown.setFont(Font.font("Times New Roman", 20));
    	BorderPane.setAlignment(txtCountDown, Pos.BOTTOM_RIGHT);
    	hbox.getChildren().addAll(timeText,txtCountDown);
    	if(count >= 0) //avoid -1 appear on timer
    	secondBorder.setRight(hbox);
    	
    	if(started) {
	    	Image image;
	    	if(truePrevious) 
	    		image = new Image("correct.png");
	    	else
	    		image = new Image("wrong.png");
	    	ImageView imageView = new ImageView(image);
	    	imageView.setFitHeight(20);
	    	imageView.setFitWidth(20);
	    	BorderPane.setAlignment(imageView, Pos.BOTTOM_CENTER);
	    	secondBorder.setCenter(imageView);
    	}
    	started = true;
    	
    	borderPane.setBottom(secondBorder);
    	


    }

    public void initializeGame()
    {
        //TODO complete this method as required.
		borderPane.setPadding(new Insets(20, 10, 2, 10));
		String[] colorsBackground = {"PINK", "BEIGE", "BURLYWOOD", "CYAN", "GOLD", "LAVENDER"};
        timeline1 = new Timeline(new KeyFrame(
                Duration.millis(250), e -> {
                    if(count >= 0 && colorIndex < colorsBackground.length)
                    {
                		borderPane.setStyle("-fx-background-color: " + colorsBackground[colorIndex]);
                		colorIndex++;
                    }
                    else if(colorIndex == colorsBackground.length)
                    {
                    	colorIndex = 0;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();
        
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}