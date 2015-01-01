package application.npc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class will load all the Npc Drops
 * 
 * @author Zack/Optimum
 *
 */
public class NpcDrops {

	/**
	 * Constructor for NpcDrops
	 */
	public NpcDrops() {
		loadDrops();
	}

	/**
	 * All the Drop Types
	 */
	public static HashMap<Integer, int[][]> commonDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[][]> uncommonDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[][]> rareDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[][]> veryRareDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[][]> extremlyRareDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[][]> otherDrops = new HashMap<Integer, int[][]>();
	public static HashMap<Integer, int[]> constantDrops = new HashMap<Integer, int[]>();

	/**
	 * All the Drop Rarities
	 */
	public static HashMap<Integer, Integer> uncommonDropRarity = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> rareDropRarity = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> veryRareDropRarity = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> extremlyRareDropRarity = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> uniqueRareDropRarity = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> otherDropRarity = new HashMap<Integer, Integer>();

	/**
	 * Loads all the drops and handles the logics
	 */
	@SuppressWarnings("resource")
	public void loadDrops() {
		try {
			int[][][] commonNpcDrops = new int[9999][][];
			int[][][] uncommonNpcDrops = new int[9999][][];
			int[][][] rareNpcDrops = new int[9999][][];
			int[][][] veryRareNpcDrops = new int[9999][][];
			int[][][] extremlyRareNpcDrops = new int[9999][][];
			int[][][] otherNpcDrops = new int[9999][][];

			int[] uncommonRarity = new int[9999];
			int[] rareRarity = new int[9999];
			int[] veryRareRarity = new int[9999];
			int[] extremlyRareRarity = new int[9999];
			int[] otherRarity = new int[9999];
			BufferedReader f = new BufferedReader(new FileReader("data/NPCDrops.TSM"));
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {

				String line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer commonTok = new StringTokenizer(line, "\t");

				line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer uncommonTok = new StringTokenizer(line, "\t");

				line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer rareTok = new StringTokenizer(line, "\t");

				line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer veryRareTok = new StringTokenizer(line, "\t");

				line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer extremlyRareTok = new StringTokenizer(line,
						"\t");

				line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer otherTok = new StringTokenizer(line, "\t");

				/**
				 * This splits up the positioning or rarity's
				 */
				String[] information = commonTok.nextToken().split("-");
				int npcId = Integer.parseInt(information[0]);

				/**
				 * Sets the postiion of :
				 */
				uncommonRarity[npcId] = Integer.parseInt(information[1]) - 1;
				rareRarity[npcId] = Integer.parseInt(information[2]) - 1;
				veryRareRarity[npcId] = Integer.parseInt(information[3]) - 1;
				extremlyRareRarity[npcId] = Integer.parseInt(information[4]) - 1;
				otherRarity[npcId] = Integer.parseInt(information[5]) - 1;

				/**
				 * Counts the tokens: /tabs
				 */
				commonNpcDrops[npcId] = new int[commonTok.countTokens()][2];
				uncommonNpcDrops[npcId] = new int[uncommonTok.countTokens()][2];
				rareNpcDrops[npcId] = new int[rareTok.countTokens()][2];
				veryRareNpcDrops[npcId] = new int[veryRareTok.countTokens()][2];
				extremlyRareNpcDrops[npcId] = new int[extremlyRareTok
						.countTokens()][2];
				otherNpcDrops[npcId] = new int[otherTok.countTokens()][2];

				/**
				 * Checks to see if there are more items
				 */
				int count = 0;
				while (commonTok.hasMoreTokens()) {
					String[] temp = commonTok.nextToken().split(":");
					commonNpcDrops[npcId][count][0] = Integer.parseInt(temp[0]);
					commonNpcDrops[npcId][count][1] = Integer.parseInt(temp[1]);
					count++;
				}
				count = 0;
				while (uncommonTok.hasMoreTokens()) {
					String[] temp = uncommonTok.nextToken().split(":");
					uncommonNpcDrops[npcId][count][0] = Integer
							.parseInt(temp[0]);
					uncommonNpcDrops[npcId][count][1] = Integer
							.parseInt(temp[1]);
					count++;
				}
				count = 0;
				while (rareTok.hasMoreTokens()) {
					String[] temp = rareTok.nextToken().split(":");
					rareNpcDrops[npcId][count][0] = Integer.parseInt(temp[0]);
					rareNpcDrops[npcId][count][1] = Integer.parseInt(temp[1]);
					count++;
				}
				count = 0;
				while (veryRareTok.hasMoreTokens()) {
					String[] temp = veryRareTok.nextToken().split(":");
					veryRareNpcDrops[npcId][count][0] = Integer
							.parseInt(temp[0]);
					veryRareNpcDrops[npcId][count][1] = Integer
							.parseInt(temp[1]);
					count++;
				}
				count = 0;
				while (extremlyRareTok.hasMoreTokens()) {
					String[] temp = extremlyRareTok.nextToken().split(":");
					extremlyRareNpcDrops[npcId][count][0] = Integer
							.parseInt(temp[0]);
					extremlyRareNpcDrops[npcId][count][1] = Integer
							.parseInt(temp[1]);
					count++;
				}
				count = 0;
				while (otherTok.hasMoreTokens()) {
					String[] temp = otherTok.nextToken().split(":");
					otherNpcDrops[npcId][count][0] = Integer.parseInt(temp[0]);
					otherNpcDrops[npcId][count][1] = Integer.parseInt(temp[1]);
					count++;
				}
				/**
				 * Drops
				 */
				commonDrops.put(npcId, commonNpcDrops[npcId]);
				uncommonDrops.put(npcId, uncommonNpcDrops[npcId]);
				rareDrops.put(npcId, rareNpcDrops[npcId]);
				veryRareDrops.put(npcId, veryRareNpcDrops[npcId]);
				extremlyRareDrops.put(npcId, extremlyRareNpcDrops[npcId]);
				otherDrops.put(npcId, otherNpcDrops[npcId]);
				/**
				 * Rarity
				 */
				uncommonDropRarity.put(npcId, uncommonRarity[npcId]);
				rareDropRarity.put(npcId, rareRarity[npcId]);
				veryRareDropRarity.put(npcId, veryRareRarity[npcId]);
				extremlyRareDropRarity.put(npcId, extremlyRareRarity[npcId]);
				otherDropRarity.put(npcId, otherRarity[npcId]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadConstants();
	}

	/**
	 * Loads the constant drop table
	 */
	@SuppressWarnings("resource")
	public void loadConstants() {
		try {
			BufferedReader f = new BufferedReader(new FileReader("data/NpcConstants.TSM"));
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (line.startsWith("#"))
					continue;
				StringTokenizer constantTok = new StringTokenizer(line, "\t");
				int npcId = Integer.parseInt(constantTok.nextToken());
				int count = 0;
				int[] temp = new int[constantTok.countTokens()];
				while (constantTok.hasMoreTokens()) {
					temp[count] = Integer.parseInt(constantTok.nextToken());
					count++;
				}
				constantDrops.put(npcId, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
