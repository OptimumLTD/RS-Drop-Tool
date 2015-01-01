package application;

/**
 * This class will handle the simulation of a drop
 * @author Zack/Optimum
 *
 */
public class SimulateDrops {
	
	/**
	 * THis handles the algorith of getting a random drop and rarity from the
	 * npcs drop table and will display it in the droptable
	 * 
	 * @param npcId - The targeted npc id
	 * @param amount - The amount of kills
	 */
	public static void SimulateDrop(int npcId, int amount){
		for(int i = 0; i < amount; i++){
			
			if (NpcDrops.constantDrops.get(npcId) != null) {
				for (int item : NpcDrops.constantDrops.get(npcId)) {
					Main.newLoot(Main.lootCount, item, 1);
				}	
			}
			if(NpcDrops.uncommonDropRarity.get(npcId) != null ||
					NpcDrops.rareDropRarity.get(npcId) != null ||
					NpcDrops.veryRareDropRarity.get(npcId) != null ||
					NpcDrops.extremlyRareDropRarity.get(npcId) != null ||
					NpcDrops.otherDropRarity.get(npcId) != null) {
				if (otherDrops(npcId) && otherDropCheck(npcId)) {
					int random = Misc.random(NpcDrops.otherDrops.get(npcId).length-1);
					Main.newLoot(Main.lootCount, NpcDrops.otherDrops.get(npcId)[random][0], NpcDrops.otherDrops.get(npcId)[random][1]);
			 	} else if (extremlyRareDrops(npcId) && extremlyRareDropCheck(npcId)) {
					int random = Misc.random(NpcDrops.extremlyRareDrops.get(npcId).length-1);
					Main.newLoot(Main.lootCount, NpcDrops.extremlyRareDrops.get(npcId)[random][0], NpcDrops.extremlyRareDrops.get(npcId)[random][1]);
			 	} else if (veryRareDrops(npcId) && veryRareDropCheck(npcId)) {
					int random = Misc.random(NpcDrops.veryRareDrops.get(npcId).length-1);
					Main.newLoot(Main.lootCount, NpcDrops.veryRareDrops.get(npcId)[random][0], NpcDrops.veryRareDrops.get(npcId)[random][1]);
			 	} else if (rareDrops(npcId) && rareDropCheck(npcId)) {
					int random = Misc.random(NpcDrops.rareDrops.get(npcId).length-1);
					Main.newLoot(Main.lootCount, NpcDrops.rareDrops.get(npcId)[random][0], NpcDrops.rareDrops.get(npcId)[random][1]);
				} else if (uncommonDrops(npcId)) {
					int random = Misc.random(NpcDrops.uncommonDrops.get(npcId).length-1);
					Main.newLoot(Main.lootCount, NpcDrops.uncommonDrops.get(npcId)[random][0], NpcDrops.uncommonDrops.get(npcId)[random][1]);
				} else {
					 int random = Misc.random(NpcDrops.commonDrops.get(npcId).length-1);
					 Main.newLoot(Main.lootCount, NpcDrops.commonDrops.get(npcId)[random][0], NpcDrops.commonDrops.get(npcId)[random][1]);
				}
			}
			NpcRareDropTable.process(npcId);	
		}
	}
	
	/**
	 * Generates a random number of the uncommon drop rarity
	 * 
	 * @param i - The npc
	 * @return true if random number is 0
	 */
	public static boolean uncommonDrops(int i) {
		return Misc.random(NpcDrops.uncommonDropRarity.get(i)) == 0;
	}
	
	/**
	 * Generates a random number of the rare drop rarity
	 * 
	 * @param i - The npc
	 * @return true if random number is 0
	 */
	public static boolean rareDrops(int i){
		return Misc.random(NpcDrops.rareDropRarity.get(i)) == 0;
	}
	
	/**
	 * Generates a random number of the very rare drop rarity
	 * 
	 * @param i - The npc
	 * @return true if random number is 0
	 */
	public static boolean veryRareDrops(int i){
		return Misc.random(NpcDrops.veryRareDropRarity.get(i)) == 0;
	}
	
	/**
	 * Generates a random number of the extremly rare drop rarity
	 * 
	 * @param i - The npc
	 * @return true if random number is 0
	 */
	public static boolean extremlyRareDrops(int i){
		return Misc.random(NpcDrops.extremlyRareDropRarity.get(i)) == 0;
	}
	
	/**
	 * Generates a random number of the other drop rarity
	 * 
	 * @param i - The npc
	 * @return true if random number is 0
	 */
	public static boolean otherDrops(int i){
		return Misc.random(NpcDrops.otherDropRarity.get(i)) == 0;
	}

	/**
	 * Will check if the npc has the other drop table
	 * 
	 * @param i - The npc
	 * @return {@value true} if npc's other drop rarity is above 1
	 */
	public static boolean otherDropCheck(int i){
		if(NpcDrops.otherDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}
	
	/**
	 * Will check if the npc has the extremely rare drop table
	 * 
	 * @param i - The npc
	 * @return {@value true} if npc's extremely rare drop rarity is above 1
	 */
	public static boolean extremlyRareDropCheck(int i){
		if(NpcDrops.extremlyRareDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}
	
	/**
	 * Will check if the npc has the very rare drop table
	 * 
	 * @param i - The npc
	 * @return {@value true} if npc's very rare drop rarity is above 1
	 */
	public static boolean veryRareDropCheck(int i){
		if(NpcDrops.veryRareDropRarity.get(i) < 1)
			return false;
		else 
			return true;
	}
	
	/**
	 * Will check if the npc has the rare drop table
	 * 
	 * @param i - The npc
	 * @return {@value true} if npc's rare drop rarity is above 1
	 */
	public static boolean rareDropCheck(int i){
		if(NpcDrops.rareDropRarity.get(i) < 1)
			return false;
		else
			return true;
	}
	
}
