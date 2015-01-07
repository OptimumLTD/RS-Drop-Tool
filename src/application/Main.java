package application;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import application.npc.NpcDrops;
import application.npc.SimulateDrops;
import application.util.ItemList;
import application.util.NpcList;

/**
 * Main Class
 * 
 * @author Zack/Optimum
 *
 */
public class Main extends Application {

	static Group root = new Group();
	static AnchorPane dropTable = new AnchorPane();
	static Button btnGetNpcList = new Button();
	static Button btnSimulate = new Button();
	static Label[] lblItemAmount = new Label[999];
	static ImageView[] newImage = new ImageView[999];
	static AnchorPane sidePanel = new AnchorPane();
	static AnchorPane tabArea = new AnchorPane();
	static int[] item = new int[999];
	static Label lblSimulateId = new Label();
	static Label lblAmount = new Label();
	static Label clearBank = new Label();
	static TextArea txtNpcId = new TextArea();
	static TextArea txtAmountToKill = new TextArea();
	static NpcDrops npc = new NpcDrops();
	static ItemList itemList = new ItemList();
	static NpcList npcList = new NpcList();
	static ScrollPane scrollPane = new ScrollPane();
	public static int lootCount = 0;
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
	static int lastPicPositionY = 0;
	static int pictureCount;
	static boolean firstPicture = true;
	
	static Label goDownPage = new Label();
	static Label goUpPage = new Label();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(root, 800, 535);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Runescape Drop Simulator");
			primaryStage.setResizable(false);
			styleTabArea();
			styleBtnGetNpcList();
			styleLabelNpcToSim();
			styleTxtNpcId();
			styleLabelAmount();
			styeTxtAmount();
			styleButtonSimulate();
			styleSlider();
			styleDropTable();
			styleBankButton();
			goDownPage();
			goUpPage();
			setScrollPane();
			styleSidePanel();
			addChildren();
			primaryStage.setScene(scene);
			primaryStage.show();
			root.getChildren().add(clearBank);
			root.getChildren().add(goUpPage);
			root.getChildren().add(goDownPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void goDownPage(){
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 25);
		goDownPage = new Label();
		goDownPage.setText("Move Down");
		goDownPage.setLayoutX(255);
		goDownPage.setPrefWidth(185);
		goDownPage.setFont(n);
		goDownPage.setTextFill(Color.WHITE);
		goDownPage.setLayoutY(515);
		goDownPage.getStyleClass().add("clearBank");
		goDownPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for(int i = 0; i < 999; i++){
					if(newImage[i] == null) continue;
					if(lblItemAmount[i] == null) continue;
					newImage[i].setLayoutY(newImage[i].getLayoutY() - 64);
					lblItemAmount[i].setLayoutY(lblItemAmount[i].getLayoutY() - 64);
				}
				lastLabelPositionY -= 64;
				lastPicPositionY -= 64;
			}
		});
	}
	
	public static void goUpPage(){
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 25);
		goUpPage = new Label();
		goUpPage.setText("Move Up");
		goUpPage.setLayoutX(625);
		goUpPage.setFont(n);
		goUpPage.setTextFill(Color.WHITE);
		goUpPage.setLayoutY(515);
		goUpPage.setPrefWidth(185);
		goUpPage.getStyleClass().add("clearBank");
		goUpPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for(int i = 0; i < 999; i++){
					if(newImage[i] == null) continue;
					if(lblItemAmount[i] == null) continue;
					newImage[i].setLayoutY(newImage[i].getLayoutY() + 64);
					lblItemAmount[i].setLayoutY(lblItemAmount[i].getLayoutY() + 64);
				}
				lastLabelPositionY += 64;
				lastPicPositionY += 64;
			}
		});
	}
	
	public static void styleSidePanel(){
		DropShadow innerShadow = new DropShadow();
		innerShadow.setOffsetX(-5);
		innerShadow.setOffsetY(-5);
		innerShadow.setColor(Color.web("0x222"));
		sidePanel.setLayoutX(228);
		sidePanel.setLayoutY(0);
		sidePanel.setPrefWidth(28);
		sidePanel.setPrefHeight(800);
		sidePanel.setEffect(innerShadow);
		sidePanel.getStyleClass().add("sidePanel");
	}
	
	public static void styleTabArea(){
		tabArea.setLayoutX(0);
		tabArea.setLayoutY(0);
		tabArea.setPrefWidth(280);
		tabArea.setPrefHeight(800);
		tabArea.getStyleClass().add("tabArea");
	}

	/**
	 * Styles the label for the item count
	 * 
	 * @param index
	 *            - The labels index id
	 * @param itemAmount
	 *            - The items amount
	 */
	public static void styleLabel(int index, int itemAmount) {
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
		lblItemAmount[index]
				.setTooltip(new Tooltip(nf.format(itemAmount) + ""));
	}

	/**
	 * Generates a new loot and displays the image and label
	 * 
	 * @param index - The loot's id
	 * @param itemId  - The item id
	 * @param amount - The amount of item
	 */
	public static void newLoot(int index, int itemId, int amount) {
		root.getChildren().remove(clearBank);
		root.getChildren().remove(goUpPage);
		root.getChildren().remove(goDownPage);
		for (int i = 0; i < item.length; i++) {
			if (itemId == item[i]) {
				styleLabel(i, Integer.parseInt(lblItemAmount[i].getTooltip() .getText().replaceAll(",", "")) + amount);
				root.getChildren().add(clearBank);
				root.getChildren().add(goUpPage);
				root.getChildren().add(goDownPage);
				return;
			}
		}
		addPicture(index, itemId);
		addLabel(index);
		styleLabel(index, amount);
		lootCount++;
		root.getChildren().add(clearBank);
		root.getChildren().add(goUpPage);
		root.getChildren().add(goDownPage);
		
	}

	/**
	 * Styles the slider
	 */
	public static void styleSlider() {
		sldSpeed.setLayoutX(15);
		sldSpeed.setLayoutY(300);
		sldSpeed.setPrefWidth(200);
		sldSpeed.getStyleClass().add("slider");
	}

	/**
	 * Adds a picture to the drop table
	 * 
	 * @param index
	 *            - The items index id
	 * @param itemId
	 *            - The item id.
	 */
	public static void addPicture(int index, int itemId) {
		BufferedImage image = null;
		WritableImage wr = null;
		PixelWriter pw = null;
		System.out.println("newItem = " + itemId);
		try {
			URL url = new URL(
					"http://dropsimulator.comuv.com/Runescape%20drop%20sims/images/"
							+ itemId + ".png");
			image = ImageIO.read(url);
			try{
				wr = new WritableImage(image.getWidth(), image.getHeight());
				pw = wr.getPixelWriter();
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						pw.setArgb(x, y, image.getRGB(x, y));
					}
				}
			} catch(Exception e){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wr != null) {
			newImage[index] = new ImageView(wr);
			item[index] = itemId;
			if (firstPicture) {
				newImage[index].setLayoutX(290);
				newImage[index].setLayoutY(30);
				firstPicture = false;
			} else {
				newImage[index].setLayoutX(lastPicPositionX + 64);
				newImage[index].setLayoutY(lastPicPositionY);
			}
			if (pictureCount == 8) {
				newImage[index].setLayoutX(290);
				newImage[index].setLayoutY(lastPicPositionY + 64);
				pictureCount = 0;
			}
			pictureCount++;
			lastPicPositionX = (int) newImage[index].getLayoutX();
			lastPicPositionY = (int) newImage[index].getLayoutY();
			root.getChildren().add(newImage[index]);
			Tooltip t = 
					new Tooltip(ItemList.getItemName(itemId) + " : "
					+ ItemList.itemIds[itemId]);
			Tooltip.install(newImage[index], t);
			t.getStyleClass().add("tooltip");
		} else {
			Label l = new Label();
			item[index] = itemId;
			if (firstPicture) {
				l.setLayoutX(290);
				l.setLayoutY(32);
				firstPicture = false;
			} else {
				l.setLayoutX(lastPicPositionX + 64);
				l.setLayoutY(lastPicPositionY);
			}
			if (pictureCount == 8) {
				l.setLayoutX(290);
				l.setLayoutY(lastPicPositionY + 64);
				pictureCount = 0;
			}
			pictureCount++;
			l.setText("-Error Img- \n" + ItemList.itemNames[itemId] + "\n" + itemId);
			l.setTextFill(Color.RED);
			lastPicPositionX = (int) l.getLayoutX();
			lastPicPositionY = (int) l.getLayoutY();
			root.getChildren().add(l);
		}
	}

	/**
	 * Add's a label to the drop table
	 * 
	 * @param index
	 *            - The label's index id
	 */
	public static void addLabel(int index) {
		lblItemAmount[index] = new Label();
		root.getChildren().add(lblItemAmount[index]);
		Font n = Font.loadFont(
				Main.class.getResourceAsStream("runescape_uf.ttf"), 16);
		lblItemAmount[index].setFont(n);
		lblItemAmount[index].setStyle("-fx-font-weight: 100;");
		lblItemAmount[index].setPrefHeight(20);
		lblItemAmount[index].setPrefWidth(40);
		if (firstLabel) {
			lblItemAmount[index].setLayoutX(290);
			firstLabel = false;
		} else
			lblItemAmount[index].setLayoutX(lastLabelPositionX + 64);

		if (labelCount == 8) {
			lblItemAmount[index].setLayoutY(lastLabelPositionY + 64);
			lblItemAmount[index].setLayoutX(290);
			labelCount = 0;
		}
		else {
			lblItemAmount[index].setLayoutY(lastLabelPositionY);
		}
		labelCount++;
		lastLabelPositionX = (int) lblItemAmount[index].getLayoutX();
		lastLabelPositionY = (int) lblItemAmount[index].getLayoutY();
	}

	/**
	 * Sets the colour for a specific label index
	 * 
	 * @param index
	 *            - The targeted label index
	 * @return The new colour
	 */
	public static Color setColor(int index) {
		int newValue = 0;
		if (lblItemAmount[index].getText().contains("M"))
			newValue = 2;
		if (lblItemAmount[index].getText().contains("K"))
			newValue = 1;
		if (newValue == 1) {
			return Color.WHITE;
		} else if (newValue == 2) {
			return Color.LIME;
		}
		return Color.YELLOW;
	}

	/**
	 * Formats {@link j} to have an "K", "M" if required
	 * 
	 * @param j
	 *            - The item amount
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
	public static void addChildren() {
		root.getChildren().add(tabArea);
		root.getChildren().add(btnGetNpcList);
		root.getChildren().add(dropTable);
		root.getChildren().add(sidePanel);
		root.getChildren().add(btnSimulate);
		root.getChildren().add(lblSimulateId);
		root.getChildren().add(txtNpcId);
		root.getChildren().add(lblAmount);
		root.getChildren().add(txtAmountToKill);
		root.getChildren().add(scrollPane);

	}

	/**
	 * Styles and places the clear bank button
	 */
	public static void styleBankButton() {
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 25);
		clearBank.getStyleClass().add("clearBank");
		clearBank.setFont(n);
		clearBank.setPrefWidth(185);
		clearBank.setLayoutX(440);
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
				for (int i = 0; i < 999; i++) {
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
	public static void styleButtonSimulate() {
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 18);
		btnSimulate.setTextFill(Color.YELLOW);
		btnSimulate.setPrefHeight(40);
		btnSimulate.setPrefWidth(200);
		btnSimulate.setLayoutX(15);
		btnSimulate.setFont(n);
		btnSimulate.setLayoutY(245);
		btnSimulate.setText("Start Simulation");
		btnSimulate.getStyleClass().add("button");
		btnSimulate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SimulateDrops.SimulateDrop(Integer.parseInt(txtNpcId.getText()), Integer.parseInt(txtAmountToKill.getText()));
			}
		});
	}

	/**
	 * Styles the input box for the npc id
	 */
	public static void styleTxtNpcId() {
		txtNpcId.setPrefHeight(40);
		txtNpcId.setPrefWidth(200);
		txtNpcId.setLayoutX(15);
		txtNpcId.setLayoutY(45);
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 20);
		txtNpcId.setFont(n);
		txtNpcId.setText("0");
		txtNpcId.getStyleClass().add("txtBoxes");
	}

	/**
	 * Styles the amount of kills input box
	 */
	public static void styeTxtAmount() {
		txtAmountToKill.setPrefHeight(40);
		txtAmountToKill.setPrefWidth(200);
		txtAmountToKill.setLayoutX(15);
		txtAmountToKill.setLayoutY(190);
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 20);
		txtAmountToKill.setFont(n);
		txtAmountToKill.getStyleClass().add("txtBoxes");
		txtAmountToKill.setText("0");
	}

	/**
	 * Styles the drop table
	 */
	public static void styleDropTable() {
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setOffsetX(4);
		innerShadow.setOffsetY(4);
		innerShadow.setColor(Color.web("0x111"));
		dropTable.setPrefHeight(HEIGHT + 190);
		dropTable.setPrefWidth(565);
		dropTable.setLayoutX(250);
		dropTable.setEffect(innerShadow);
	}
	
	public static void setScrollPane(){
		scrollPane.setContent(dropTable);
		scrollPane.setLayoutY(-1);
		scrollPane.setLayoutX(250);
		scrollPane.setPrefHeight(548);
		scrollPane.setPrefWidth(580);
		scrollPane.hbarPolicyProperty().set(ScrollBarPolicy.NEVER);
		scrollPane.vbarPolicyProperty().set(ScrollBarPolicy.NEVER);
		scrollPane.getStyleClass().add("nScroll");
	}
	

	/**
	 * Styles the npc id label
	 */
	public static void styleLabelNpcToSim() {
		lblSimulateId.setPrefHeight(40);
		lblSimulateId.setPrefWidth(200);
		lblSimulateId.setLayoutX(15);
		lblSimulateId.setLayoutY(5);
		lblSimulateId.setText("Npc Name To Simulate");
		lblSimulateId.getStyleClass().add("lblSimulateId");
	}

	/**
	 * Styles the amount to kill label
	 */
	public static void styleLabelAmount() {
		lblAmount.setPrefHeight(40);
		lblAmount.setPrefWidth(200);
		lblAmount.setLayoutX(15);
		lblAmount.setLayoutY(150);
		lblAmount.setText("Amount to kill");
		lblAmount.getStyleClass().add("lblSimulateId");
	}

	/**
	 * Styles the get Npc list id. Method will soon be redundant
	 */
	public static void styleBtnGetNpcList() {
		Font n = Font.loadFont(Main.class.getResourceAsStream("runescape_uf.ttf"), 18);
		btnGetNpcList.setPrefHeight(40);
		btnGetNpcList.setPrefWidth(200);
		btnGetNpcList.setLayoutX(15);
		btnGetNpcList.setLayoutY(100);
		btnGetNpcList.setFont(n);
		btnGetNpcList.setTextFill(Color.YELLOW);
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
