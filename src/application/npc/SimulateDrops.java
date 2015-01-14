package application.npc;

import java.security.SecureRandom;

import application.Main;
import application.util.ItemList;

/**
 * This class will handle the simulation of a drop
 * @author Zack/Optimum
 *
 */
public class SimulateDrops {
	
	/**
	 * Generates a secure random number for drops
	 */
	private static SecureRandom rnd = new SecureRandom();
	
	public static void SimulateDrop(int npcId, int amount) {
		for (int i = 0; i < amount; i++) {
			if (NpcDrops.constantDrops.get(npcId) != null) {
				for (int item : NpcDrops.constantDrops.get(npcId)) {
					Main.newLoot(Main.lootCount, item, 1);
				}
			}
			if (NpcDrops.uncommonDropRarity.get(npcId) != null
					|| NpcDrops.rareDropRarity.get(npcId) != null
					|| NpcDrops.veryRareDropRarity.get(npcId) != null
					|| NpcDrops.extremlyRareDropRarity.get(npcId) != null
					|| NpcDrops.otherDropRarity.get(npcId) != null) {
				
				/**
				 * Other drops
				 */
				if ( otherDropCheck(npcId) && otherDrops(npcId)) {
					int random = rnd.nextInt(NpcDrops.otherDrops.get(npcId).length);
							Main.newLoot(Main.lootCount, NpcDrops.otherDrops.get(npcId)[random][0],
							NpcDrops.otherDrops.get(npcId)[random][1]);
					System.out.println(ItemList.getItemName(NpcDrops.otherDrops.get(npcId)[random][0]) + " drop on : " + i + "/" + amount);
					
				/**
				* Extreme drops
				*/
				} else if (extremlyRareDropCheck(npcId) && extremlyRareDrops(npcId)) {
					int random = rnd.nextInt(NpcDrops.extremlyRareDrops.get(npcId).length);
							Main.newLoot(Main.lootCount,
							NpcDrops.extremlyRareDrops.get(npcId)[random][0],
							NpcDrops.extremlyRareDrops.get(npcId)[random][1]);
					System.out.println(ItemList.getItemName(NpcDrops.extremlyRareDrops.get(npcId)[random][0]) + " drop on : " + i + "/" + amount);
					
				/**
				 * Very rare drops
				 */
				} else if (veryRareDropCheck(npcId) && veryRareDrops(npcId)) {
					int random = rnd.nextInt(NpcDrops.veryRareDrops.get(npcId).length);
					Main.newLoot(Main.lootCount, NpcDrops.veryRareDrops.get(npcId)[random][0],
							NpcDrops.veryRareDrops.get(npcId)[random][1]);
					System.out.println(ItemList.getItemName(NpcDrops.veryRareDrops.get(npcId)[random][0]) + " drop on : " + i + "/" + amount);
					
				/**
				 * Rare Drops	
				 */
				} else if (rareDropCheck(npcId) && rareDrops(npcId)) {
					int random = rnd.nextInt(NpcDrops.rareDrops.get(npcId).length);
					Main.newLoot(Main.lootCount,
							NpcDrops.rareDrops.get(npcId)[random][0],
							NpcDrops.rareDrops.get(npcId)[random][1]);
					
				/**
				 * Uncommon drops
				 */
				} else if (uncommonDrops(npcId)) {
					int random = rnd.nextInt(NpcDrops.uncommonDrops.get(npcId).length);
					Main.newLoot(Main.lootCount,
							NpcDrops.uncommonDrops.get(npcId)[random][0],
							NpcDrops.uncommonDrops.get(npcId)[random][1]);
				} else {
					int random = rnd.nextInt(NpcDrops.commonDrops.get(npcId).length);
					Main.newLoot(Main.lootCount,
							NpcDrops.commonDrops.get(npcId)[random][0],
							NpcDrops.commonDrops.get(npcId)[random][1]);
				}
			}
			NpcRareDropTable.process(npcId);
		}
	}

	/**
	 * Generates a random number of the uncommon drop rarity
	 * 
	 * @param i
	 *            - The npc
	 * @return true if random number is 0
	 */
	public static boolean uncommonDrops(int i) {
		return rnd.nextInt(NpcDrops.uncommonDropRarity.get(i)) == 0;
	}

	/**
	 * Generates a random number of the rare drop rarity
	 * 
	 * @param i
	 *            - The npc
	 * @return true if random number is 0
	 */
	public static boolean rareDrops(int i) {
		return rnd.nextInt(NpcDrops.rareDropRarity.get(i)) == 0;
	}

	/**
	 * Generates a random number of the very rare drop rarity
	 * 
	 * @param i
	 *            - The npc
	 * @return true if random number is 0
	 */
	public static boolean veryRareDrops(int i) {
		return rnd.nextInt(NpcDrops.veryRareDropRarity.get(i)) == 0;
	}

	/**
	 * Generates a random number of the extremly rare drop rarity
	 * 
	 * @param i
	 *            - The npc
	 * @return true if random number is 0
	 */
	public static boolean extremlyRareDrops(int i) {
		return rnd.nextInt(NpcDrops.extremlyRareDropRarity.get(i)) == 0;
	}

	/**
	 * Generates a random number of the other drop rarity
	 * 
	 * @param i
	 *            - The npc
	 * @return true if random number is 0
	 */
	public static boolean otherDrops(int i) {
		return rnd.nextInt(NpcDrops.otherDropRarity.get(i)) == 0;
	}

	/**
	 * Will check if the npc has the other drop table
	 * 
	 * @param i
	 *            - The npc
	 * @return true} if npc's other drop rarity is above 1
	 */
	public static boolean otherDropCheck(int i) {
		if (NpcDrops.otherDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}

	/**
	 * Will check if the npc has the extremely rare drop table
	 * 
	 * @param i
	 *            - The npc
	 * @return true} if npc's extremely rare drop rarity is above 1
	 */
	public static boolean extremlyRareDropCheck(int i) {
		if (NpcDrops.extremlyRareDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}

	/**
	 * Will check if the npc has the very rare drop table
	 * 
	 * @param i
	 *            - The npc
	 * @return true} if npc's very rare drop rarity is above 1
	 */
	public static boolean veryRareDropCheck(int i) {
		if (NpcDrops.veryRareDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}

	/**
	 * Will check if the npc has the rare drop table
	 * 
	 * @param i
	 *            - The npc
	 * @return true} if npc's rare drop rarity is above 1
	 */
	public static boolean rareDropCheck(int i) {
		if (NpcDrops.rareDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}
	
}
