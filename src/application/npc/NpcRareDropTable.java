package application.npc;

import java.security.SecureRandom;

import application.Main;
import application.util.Misc;
import application.util.NpcList;

/**
 * The Rare drop table, this class basically is a new drop table for npc that
 * access it
 * 
 * @author Zack/Optimum
 *
 */

public class NpcRareDropTable {
	
	private static SecureRandom rnd = new SecureRandom();

	/**
	 * This array contains all of the Common drop data
	 */
	static int[][] COMMON = { { 1623, 1 }, { 1621, 1 }, { 1619, 1 },
			{ 1452, 1 } };

	/**
	 * This array contains all of the Uncommon drop data
	 */
	static int[][] UNCOMMON = { { 1617, 1 }, { 1462, 1 }, { 985, 1 },
			{ 987, 1 }, { 830, 5 } };

	/**
	 * This array contains all of the Rare drop data
	 */
	static int[][] RARE = { { 563, 43 }, { 560, 45 }, { 561, 67 },
			{ 886, 150 }, { 892, 42 }, { 1615, 1 }, { 443, 100 },
			{ 995, 3000 }, { 1247, 1 }, { 1319, 1 }, { 1373, 1 }, { 1149, 1 } };

	/**
	 * This array contains all of the Very Rare drop data
	 */
	static int[][] VERY_RARE = { { 1201, 1 }, { 2366, 1 }, { 1249, 1 } };

	/**
	 * All the npc id's that can access the Rare drop table
	 */
	static int[] npcs = {  6260, 50, 3068, 1604, 1605, 1606, 1607, 12, 17, 2130, 2131,
			2132, 2133, 1618, 1619, 1600, 1601, 1602, 1603, 181, 2423, 1648,
			1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657, 6270, 1615,
			1616, 202, 55, 193, 3200, 2263, 1612, 84, 178, 179, 1590, 1021, 54,
			221, 1831, 1338, 1339, 1340, 1341, 1342, 1343, 1344, 1345, 1346,
			1347, 2881, 2882, 2883, 2783, 192, 1624, 118, 120, 1020, 124, 1183,
			1184, 1019, 1582, 1583, 1584, 1585, 1586, 1052, 1053, 1973, 6218,
			83, 941, 195, 196, 1710, 1711, 1712, 117, 122, 123, 111, 1560,
			1561, 1562, 1563, 1564, 1565, 1566, 125, 795, 1591, 1637, 1638,
			1639, 1640, 1641, 1642, 1312, 113, 1524, 1153, 1154, 1155, 1156,
			1157, 1158, 1159, 1160, 2642, 6258, 1098, 1608, 1609, 1308, 82,
			200, 112, 2373, 1106, 1107, 1108, 1109, 1110, 1111, 1112, 1138,
			1960, 1961, 1962, 1963, 1964, 1965, 1966, 1967, 1968, 1613, 194,
			126, 365, 1097, 182, 183, 184, 185, 1633, 1634, 1635, 1636, 53,
			237, 391, 392, 393, 394, 395, 396, 1095, 1265, 1267, 413, 414, 415,
			416, 417, 418, 1622, 1623, 205, 158, 459, 750, 1471, 90, 91, 92,
			93, 6257, 6278, 2558, 6277, 6229, 6239, 6222, 6265, 6263,
			6261, 6223, 6225, 6227, 6204, 6206, 6208, 6203, 6247, 6248, 6250,
			6252, 1592, 1096, 1101, 1102, 1103, 1104, 1105, 186, 438, 439, 440,
			441, 442, 443, 2496, 2497, 1115, 1116, 1117, 1118, 1119, 1120,
			1121, 1122, 1123, 1124, 1626, 1627, 1628, 1629, 1630, 1631, 1632,
			1200, 2598, 2599, 2600, 2601, 2602, 2603, 2610, 2611, 2612, 2613,
			2614, 2615, 2616, 2591, 2592, 2593, 2594, 2595, 2596, 2597, 2604,
			2605, 2606, 2607, 2608, 2609, 2457, 1022, 6212, 19, 1092, 1007,
			2044, 2045, 2046, 2047, 2048, 2049, 73, 74, 75, 76, 419, 420, 421,
			422, 423, 424 };

	/**
	 * This method process' all of the information and generates the drops
	 * 
	 * @param i
	 *            - The targeted npc id
	 */
	public static void process(int i) {
		int newChance = rnd.nextInt(1500 / NpcList.npcHealth[NpcList.getNpcHealth(i)]);
		if (newChance == 0) {
			for (int i2 = 0; i2 < npcs.length; i2++) {
				if (i == npcs[i2]) {
					if (rnd.nextInt(16) == 0) {
						dropVeryRare();
						return;
					} else if (rnd.nextInt(10) == 0) {
						dropRare();
						return;
					} else if (rnd.nextInt(5) == 0) {
						dropUncommon();
						return;
					} else {
						dropCommon();
					}
					return;
				}
			}
			return;
		}
	}

	/**
	 * This method grabs a random item from the common data and makes it spawn
	 * on the ground
	 */
	private static void dropCommon() {
		int index = rnd.nextInt(COMMON.length - 1);
		int itemId = COMMON[index][0];
		int itemAmount = COMMON[index][1];
		Main.newLoot(Main.lootCount, itemId, itemAmount);
	}

	/**
	 * This method grabs a random item from the Uncommon data and makes it spawn
	 * on the ground
	 */
	private static void dropUncommon() {
		int index = rnd.nextInt(UNCOMMON.length - 1);
		int itemId = UNCOMMON[index][0];
		int itemAmount = UNCOMMON[index][1];
		Main.newLoot(Main.lootCount, itemId, itemAmount);
	}

	/**
	 * This method grabs a random item from the rare data and makes it spawn on
	 * the ground
	 */
	public static void dropRare() {
		int index = rnd.nextInt(RARE.length - 1);
		int itemId = RARE[index][0];
		int itemAmount = RARE[index][1];
		Main.newLoot(Main.lootCount, itemId, itemAmount);
	}

	/**
	 * This method grabs a random item from the Very rare data and makes it
	 * spawn on the ground
	 */
	public static void dropVeryRare() {
		int index = rnd.nextInt(VERY_RARE.length - 1);
		int itemId = VERY_RARE[index][0];
		int itemAmount = VERY_RARE[index][1];
		Main.newLoot(Main.lootCount, itemId, itemAmount);
	}
}
