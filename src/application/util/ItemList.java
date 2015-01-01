package application.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This will class will load all the item data.
 * @author Zack/Optimum
 *
 */
public class ItemList {

	/**
	 * A list of all the item names
	 */
	public static String[] itemNames = new String[12500];
	
	/**
	 * A list of all the item id's
	 */
	public static int[] itemIds = new int[12500];
	
	/**
	 * Constructor for ItemList
	 */
	public ItemList(){
		loadItemList();
	}
	
	/**
	 * Loads the .txt file that contains
	 * all the item data and places it into it's correct object
	 */
	private void loadItemList(){
		BufferedReader file = null;
		String line = "";
		int counter = 0;
		boolean endOfFile = false;
		try {
			file = new BufferedReader(new FileReader("data/itemlist.txt"));
		} catch (FileNotFoundException fileex) {
			fileex.printStackTrace();
		}
		try {
			line = file.readLine();
		} catch (IOException ioexception1) {
			endOfFile = true;
		}
		while(!endOfFile && line != null && counter != 12000){
			String[] args = line.split("\t");
			itemNames[counter] = args[1];
			itemIds[counter] = Integer.parseInt(args[0]);
			counter++;
			try {
				line = file.readLine();
			} catch (IOException ioexception1) {
				endOfFile = true;
			}
		}
	}
	
	/**
	 * Get the items name by id
	 * @param id - The id of the time
	 * @return
	 */
	public String getItemName(int id){
		return itemNames[id];
	}
	
	/**
	 * Gets the items id by using {@link name}
	 * @param name - The name of the item
	 * @return The items id
	 */
	public int getItemIdByName(String name){
		for(int i = 0; i < itemNames.length; i++){
			if(name.equalsIgnoreCase(itemNames[i])){
				return i;
			}
		}
		return 0;
	}
}
