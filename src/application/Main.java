package application;
	
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main Class
 * @author Zack/Optimum
 *
 */
public class Main extends Application {
	
	static Group root = new Group();
	static AnchorPane dropTable = new AnchorPane();
	static Button btnGetNpcList = new Button();
	static Button btnSimulate = new Button();
	static Label[] lblItemAmount= new Label[999];
	static ImageView[] newImage = new ImageView[999];
	static int[] item = new int[999];
	static Label lblSimulateId = new Label();
	static Label lblAmount = new Label();
	static Label clearBank = new Label();
	static TextArea txtNpcId = new TextArea();
	static TextArea txtAmountToKill = new TextArea();
	static NpcDrops npc = new NpcDrops();
	static ItemList itemList = new ItemList();
	static NpcList npcList = new NpcList();
	static int lootCount = 0;
	static Slider sldSpeed = new Slider();
	private final static int HEIGHT = 500;
	@SuppressWarnings("unused")
	private final static int WIDTH = 800;

	
	/**
	 * Variables for loots
	 */
	static int lastLabelPositionX = 0;
	static int labelCount = 0;
	static boolean firstLabel = true;
	static int lastLabelPositionY = 10;
	static int lastPicPositionX = 0;
	static int lastPicPositionY = 32;
	static int pictureCount;
	static boolean firstPicture = true;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(root,800,535);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Runescape Drop Simulator");
			primaryStage.setResizable(false);
			styleDropTable();
			styleBtnGetNpcList();
			styleLabelNpcToSim();
			styleTxtNpcId();
			styleLabelAmount();
			styeTxtAmount();
			styleButtonSimulate();
			styleSlider();
			styleBankButton();
			addChildren();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Styles the label for the item count
	 * 
	 * @param index - The labels index id
	 * @param itemAmount - The items amount
	 */
	public static void styleLabel(int index, int itemAmount){
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(0);
		dropShadow.setOffsetX(2);
		dropShadow.setOffsetY(2);
		dropShadow.setColor(Color.BLACK);
		lblItemAmount[index].setEffect(dropShadow);
		lblItemAmount[index].setText(formatAmount(itemAmount));
		lblItemAmount[index].setTextFill(setColor(index));
		lblItemAmount[index].getStyleClass().add("lblItemAmount");
		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
		lblItemAmount[index].setTooltip(new Tooltip(nf.format(itemAmount) + ""));
	}
	
	/**
	 * Generates a new loot and displays the image and label
	 * 
	 * @param index - The loot's id
	 * @param itemId - The item id
	 * @param amount - The amount of item
	 */
	public static void newLoot(int index, int itemId, int amount){
		if(lootCount == 63)
			return;
		for(int i = 0; i < item.length; i++){
			if(itemId == item[i]){
				styleLabel(i, Integer.parseInt(lblItemAmount[i].getTooltip().getText().replaceAll(",", "")) + amount);
				return;
			}
		}
		addPicture(index, itemId);
		addLabel(index);
		styleLabel(index, amount);
		lootCount++;
	}
	
	/**
	 * Styles the slider
	 */
	public static void styleSlider(){
		sldSpeed.setLayoutX(23);
		sldSpeed.setLayoutY(300);
		sldSpeed.setPrefWidth(200);
		sldSpeed.getStyleClass().add("slider");
	}
	
	/**
	 * Adds a picture to the drop table
	 * 
	 * @param index - The items index id
	 * @param itemId - The item id.
	 */
	public static void addPicture(int index, int itemId){
		int newSpriteId = itemId;
		if(itemId == 995){
			newSpriteId = 1004;
		}
		BufferedImage image = null;
		WritableImage wr = null;
		try {
			System.out.println("newImage: " + itemId);
			URL url = new URL("http://dropsimulator.comuv.com/Sprite%20Cache/images/" + newSpriteId + ".png");
			image = ImageIO.read(url);
			wr = new WritableImage(image.getWidth(), image.getHeight());
			PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x <image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		try{
			if(wr == null)
				return;
			newImage[index] = new ImageView(wr);
			item[index] = itemId;
			if(firstPicture){
				newImage[index].setLayoutX(290);
				newImage[index].setLayoutY(32);
				firstPicture = false;
			} else {
				newImage[index].setLayoutX(lastPicPositionX + 64);
				newImage[index].setLayoutY(lastPicPositionY);
			}
			if(pictureCount == 8){
				newImage[index].setLayoutX(290);
				newImage[index].setLayoutY(lastPicPositionY + 64);
				pictureCount = 0;
			}
			pictureCount++;
			lastPicPositionX = (int) newImage[index].getLayoutX();
			lastPicPositionY = (int) newImage[index].getLayoutY();
			root.getChildren().add(newImage[index]);
	        Tooltip t = new Tooltip(itemList.getItemName(itemId) + " : " + ItemList.itemIds[itemId]);
	        Tooltip.install(newImage[index], t);
	        t.getStyleClass().add("tooltip");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Add's a label to the drop table
	 * 
	 * @param index - The label's index id
	 */
	public static void addLabel(int index){
		lblItemAmount[index] = new Label();
		root.getChildren().add(lblItemAmount[index]);
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 16);
		lblItemAmount[index].setFont(n);
		lblItemAmount[index].setStyle("-fx-font-weight: 100;");
		lblItemAmount[index].setPrefHeight(20);
		lblItemAmount[index].setPrefWidth(40);
		if(firstLabel){
			lblItemAmount[index].setLayoutX(290);
			firstLabel = false;
		}
		else 
			lblItemAmount[index].setLayoutX(lastLabelPositionX + 64);
		
		if(labelCount == 8) {
			lblItemAmount[index].setLayoutY(lastLabelPositionY + 64);
			lblItemAmount[index].setLayoutX(290);
			labelCount = 0;
		} else {
			lblItemAmount[index].setLayoutY(lastLabelPositionY);
		}
		labelCount++;
		lastLabelPositionX = (int) lblItemAmount[index].getLayoutX();
		lastLabelPositionY = (int) lblItemAmount[index].getLayoutY();
	}
	
	
	/**
	 * Sets the colour for a specific label index
	 * 
	 * @param index - The targeted label index
	 * @return The new colour
	 */
	public static Color setColor(int index){
		int newValue = 0;
		if(lblItemAmount[index].getText().contains("M"))
			newValue = 2;
		if(lblItemAmount[index].getText().contains("K"))
			newValue = 1;
		if(newValue == 1){
			return Color.WHITE;
		} else if(newValue == 2) {
			return Color.LIME;
		}
		return Color.YELLOW;
	}
	
	/**
	 * Formats {@link j} to have an "K", "M" if required
	 * 
	 * @param j - The item amount
	 * @return the new format
	 */
	public final static String formatAmount(int j) {
		if (j >= 0 && j < 100000)
			return String.valueOf(j);
		if (j >= 100000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j <= Integer.MAX_VALUE)
			return j / 1000000 + "M";
		if (j > Integer.MAX_VALUE)
			return "*";
		else
			return "???";
	}
	
	/**
	 * Adds all the children to the root
	 */
	public static void addChildren(){
		root.getChildren().add(btnGetNpcList);
		root.getChildren().add(dropTable);
		root.getChildren().add(btnSimulate);
		root.getChildren().add(lblSimulateId);
		root.getChildren().add(txtNpcId);
		root.getChildren().add(lblAmount);
		root.getChildren().add(txtAmountToKill);
		root.getChildren().add(sldSpeed);
		root.getChildren().add(clearBank);
	}
	
	/**
	 * Styles and places the clear bank button
	 */
	public static void styleBankButton(){
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(0);
		dropShadow.setOffsetX(2);
		dropShadow.setOffsetY(2);
		dropShadow.setColor(Color.BLACK);
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 25);
		clearBank.getStyleClass().add("clearBank");
		clearBank.setFont(n);
		clearBank.setEffect(dropShadow);
		clearBank.setPrefWidth(100);
		clearBank.setLayoutX(500);
		clearBank.setLayoutY(515);
		clearBank.setText("Clear Bank");
		clearBank.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				lastLabelPositionX = 0;
				labelCount = 0;
				firstLabel = true;
				lastLabelPositionY = 10;
				lastPicPositionX = 0;
				lastPicPositionY = 32;
				pictureCount = 0;
				lootCount = 0;
				firstPicture = true;
				for(int i = 0; i < 999; i++){
					root.getChildren().remove(newImage[i]);
					newImage[i] = null;
					root.getChildren().remove(lblItemAmount[i]);
					lblItemAmount[i] = null;
					item[i] = -1;
				}
			}
		});
	}
	
	/**
	 * Styles the simulate drops button
	 */
	public static void styleButtonSimulate(){
		btnSimulate.setPrefHeight(40);
		btnSimulate.setPrefWidth(200);
		btnSimulate.setLayoutX(23);
		btnSimulate.setLayoutY(245);
		btnSimulate.setText("Simulate Drops");
		btnSimulate.getStyleClass().add("button");
		btnSimulate.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				for(int i = 0; i < Integer.parseInt(txtAmountToKill.getText()); i++){
					SimulateDrops.SimulateDrop(Integer.parseInt(txtNpcId.getText()), 1);
				}
		    }
		});
	}
	
	/**
	 * Styles the input box for the npc id
	 */
	public static void styleTxtNpcId(){
		txtNpcId.setPrefHeight(40);
		txtNpcId.setPrefWidth(200);
		txtNpcId.setLayoutX(23);
		txtNpcId.setLayoutY(45);
		txtNpcId.setText("0");
		txtNpcId.getStyleClass().add("txtBoxes");
	}
	
	/**
	 * Styles the amount of kills input box
	 */
	public static void styeTxtAmount(){
		txtAmountToKill.setPrefHeight(40);
		txtAmountToKill.setPrefWidth(200);
		txtAmountToKill.setLayoutX(23);
		txtAmountToKill.setLayoutY(190);
		txtAmountToKill.getStyleClass().add("txtBoxes");	
		txtAmountToKill.setText("0");
	}
	
	/**
	 * Styles the drop table
	 */
	public static void styleDropTable(){
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setOffsetX(4);
		innerShadow.setOffsetY(4);
		innerShadow.setColor(Color.web("0x111"));
		dropTable.setPrefHeight(HEIGHT + 70);
		dropTable.setPrefWidth(560);
		dropTable.setLayoutX(250);
		dropTable.setEffect(innerShadow);
	}
	
	/**
	 * Styles the npc id label
	 */
	public static void styleLabelNpcToSim(){
		lblSimulateId.setPrefHeight(40);
		lblSimulateId.setPrefWidth(200);
		lblSimulateId.setLayoutX(23);
		lblSimulateId.setLayoutY(5);
		lblSimulateId.setText("Npc id to simulate");
		lblSimulateId.getStyleClass().add("lblSimulateId");
	}
	
	/**
	 * Styles the amount to kill label
	 */
	public static void styleLabelAmount(){
		lblAmount.setPrefHeight(40);
		lblAmount.setPrefWidth(200);
		lblAmount.setLayoutX(23);
		lblAmount.setLayoutY(150);
		lblAmount.setText("Amount to kill");
		lblAmount.getStyleClass().add("lblSimulateId");
	}
	
	/**
	 * Styles the get Npc list id.
	 * Method will soon be redundant
	 */
	public static void styleBtnGetNpcList(){
		btnGetNpcList.setPrefHeight(40);
		btnGetNpcList.setPrefWidth(200);
		btnGetNpcList.setLayoutX(23);
		btnGetNpcList.setLayoutY(100);
		btnGetNpcList.setText("Get Npc List");
		btnGetNpcList.getStyleClass().add("button");
	}
	
	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
