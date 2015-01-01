package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This will load all the npc's information
 * @author Zack/Optimum
 *
 */
public class NpcList {
	
	/**
	 * The Npc's Id
	 */
	public static int[] npcId = new int[6800];
	
	/**
	 * The Npc's name
	 */
	public static String[] npcName = new String[6800];
	
	/**
	 * The Npc's health
	 */
	public static int[] npcHealth = new int[6800];
	
	/**
	 * Constructor for the NpcList
	 */
	public NpcList(){
		loadNpcs();
	}
	
	/**
	 * Loads all the npc's from npclist.txt and places all the 
	 * information into the correct objects
	 */
	private void loadNpcs(){
		BufferedReader file = null;
		String line = "";
		int counter = 0;
		boolean endOfFile = false;
		try {
			file = new BufferedReader(new FileReader("src/application/npclist.txt"));
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
			npcId[counter] = Integer.parseInt(args[0]);
			npcName[counter] = args[1];
			npcHealth[counter] = Integer.parseInt(args[2]);
			counter++;
			try {
				line = file.readLine();
			} catch (IOException ioexception1) {
				endOfFile = true;
			}
		}
	}
}
