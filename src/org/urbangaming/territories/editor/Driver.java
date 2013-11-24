package org.urbangaming.territories.editor;

import java.awt.*;
import java.io.*;
import java.util.*;
import org.urbangaming.territories.core.*;

/**
 * This is the temporary class that will manually write all of the given map data to a file.
 * @author Andrew Lopreiato
 * @version 1.0 11/14/13
 */
public class Driver {

	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		// initialize map
		TerritoriesMap territoriesFallMap = new TerritoriesMap();
		
		// populate territory list
		PopulateTerritoryList(territoriesFallMap.getTerritories());
		
		// populate team list
		PopulateTeamList(territoriesFallMap.getTeams());
		
		// populate connection list
		PopulateConnectionList(territoriesFallMap.getConnections());
		
		
		for (int i = 0; i < territoriesFallMap.getAmountOfTerritories(); i++) {
			SetTerritoryOwner(territoriesFallMap.getTerritory(i), territoriesFallMap.getTeams());
		}
		
		// Serialize the map
		try {
			System.out.println("Saving map...");
			territoriesFallMap.Serialize("Fall2013.trmap");
			System.out.println("Map Saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // END main
	
	/**
	 * Will prompt the user which team they would like to set the given territory to, and then adds that territory to
	 * the team's territory list.
	 * @param input Scanner to read the input.
	 * @param territory Territory to prompt the user about.
	 * @param teamsList The available teams.
	 */
	private static void SetTerritoryOwner(Territory territory, ArrayList<Team> teamsList) {	
		teamsList.get(0).OwnedTerritories.add(territory);
	} // END GetTerritoryOwner

	/**
	 * Hard coded method to populate the territories.
	 * @param list List to populate to.
	 */
	private static void PopulateTerritoryList(ArrayList<Territory> list) {
		Polygon tempPoly = null;
		
		// MILLENNIUM SCIENCE COMPLEX
		tempPoly = new Polygon();
		tempPoly.addPoint(668, 360);
		tempPoly.addPoint(731, 350);
		tempPoly.addPoint(734, 346);
		tempPoly.addPoint(730, 337);
		tempPoly.addPoint(721, 311);
		tempPoly.addPoint(663, 319);
		list.add(new Territory("Millennium Science Complex", tempPoly));
		
		
		// WEST POLLOCK
		tempPoly = new Polygon();
		tempPoly.addPoint(644, 379);
		tempPoly.addPoint(655, 453);
		tempPoly.addPoint(668, 450);
		tempPoly.addPoint(669, 443);
		tempPoly.addPoint(683, 438);
		tempPoly.addPoint(683, 430);
		tempPoly.addPoint(690, 393);
		tempPoly.addPoint(680, 373);
		list.add(new Territory("West Pollock", tempPoly));
		
		// EAST POLLOCK
		tempPoly = new Polygon();
		tempPoly.addPoint(694, 371);
		tempPoly.addPoint(690, 392);
		tempPoly.addPoint(702, 409);
		tempPoly.addPoint(707, 410);
		tempPoly.addPoint(708, 425);
		tempPoly.addPoint(746, 418);
		tempPoly.addPoint(741, 380);
		tempPoly.addPoint(723, 382);
		tempPoly.addPoint(721, 367);
		list.add(new Territory("East Pollock", tempPoly));
		
		// LOWER OLD MAIN
		tempPoly = new Polygon();
		tempPoly.addPoint(379, 480);
		tempPoly.addPoint(379, 524);
		tempPoly.addPoint(448, 523);
		tempPoly.addPoint(448, 480);
		list.add(new Territory("Lower Old Main", tempPoly));
		
		// UPPER OLD MAIN
		tempPoly = new Polygon();
		tempPoly.addPoint(406, 442);
		tempPoly.addPoint(428, 437);
		tempPoly.addPoint(433, 415);
		tempPoly.addPoint(444, 395);
		tempPoly.addPoint(448, 395);
		tempPoly.addPoint(447, 467);
		tempPoly.addPoint(379, 471);
		list.add(new Territory("Upper Old Main", tempPoly));
		
		// BOUKE
		tempPoly = new Polygon();
		tempPoly.addPoint(518, 382);
		tempPoly.addPoint(587, 371);
		tempPoly.addPoint(581, 337);
		tempPoly.addPoint(563, 337);
		tempPoly.addPoint(560, 325);
		tempPoly.addPoint(537, 327);
		tempPoly.addPoint(539, 339);
		tempPoly.addPoint(511, 343);
		tempPoly.addPoint(511, 349);
		tempPoly.addPoint(535, 344);
		tempPoly.addPoint(538, 374);
		tempPoly.addPoint(517, 379);
		list.add(new Territory("Bouke", tempPoly));
		
		// EASTVIEW TERRACE
		tempPoly = new Polygon();
		tempPoly.addPoint(778, 456);
		tempPoly.addPoint(815, 453);
		tempPoly.addPoint(832, 457);
		tempPoly.addPoint(824, 460);
		tempPoly.addPoint(822, 484);
		tempPoly.addPoint(832, 484);
		tempPoly.addPoint(833, 494);
		tempPoly.addPoint(795, 493);
		tempPoly.addPoint(795, 486);
		tempPoly.addPoint(778, 486);
		list.add(new Territory("Eastview Terrace", tempPoly));
		
		// FOREST RESOURCES
		tempPoly = new Polygon();
		tempPoly.addPoint(631, 116);
		tempPoly.addPoint(638, 115);
		tempPoly.addPoint(648, 105);
		tempPoly.addPoint(653, 90);
		tempPoly.addPoint(649, 76);
		tempPoly.addPoint(673, 70);
		tempPoly.addPoint(686, 126);
		tempPoly.addPoint(634, 135);
		list.add(new Territory("Forest Resources", tempPoly));
		
		// BUSINESS
		tempPoly = new Polygon();
		tempPoly.addPoint(550, 99);
		tempPoly.addPoint(591, 90);
		tempPoly.addPoint(598, 105);
		tempPoly.addPoint(610, 116);
		tempPoly.addPoint(624, 118);
		tempPoly.addPoint(627, 136);
		tempPoly.addPoint(560, 152);
		list.add(new Territory("Business Building", tempPoly));
		
		// AG SCI
		tempPoly = new Polygon();
		tempPoly.addPoint(560, 156);
		tempPoly.addPoint(616, 140);
		tempPoly.addPoint(626, 145);
		tempPoly.addPoint(634, 152);
		tempPoly.addPoint(643, 204);
		tempPoly.addPoint(571, 217);
		list.add(new Territory("Agricultural Sciences", tempPoly));
		
		// THOMAS
		tempPoly = new Polygon();
		tempPoly.addPoint(588, 334);
		tempPoly.addPoint(605, 327);
		tempPoly.addPoint(604, 318);
		tempPoly.addPoint(627, 312);
		tempPoly.addPoint(627, 308);
		tempPoly.addPoint(637, 308);
		tempPoly.addPoint(630, 317);
		tempPoly.addPoint(635, 364);
		tempPoly.addPoint(594, 371);
		list.add(new Territory("Thomas", tempPoly));
		
		// FERGUSON
		tempPoly = new Polygon();
		tempPoly.addPoint(496, 282);
		tempPoly.addPoint(506, 274);
		tempPoly.addPoint(505, 260);
		tempPoly.addPoint(516, 257);
		tempPoly.addPoint(524, 251);
		tempPoly.addPoint(521, 233);
		tempPoly.addPoint(552, 227);
		tempPoly.addPoint(556, 262);
		tempPoly.addPoint(546, 265);
		tempPoly.addPoint(529, 280);
		tempPoly.addPoint(498, 290);
		list.add(new Territory("Ferguson", tempPoly));
		
		// MEULLER
		tempPoly = new Polygon();
		tempPoly.addPoint(438, 290);
		tempPoly.addPoint(449, 267);
		tempPoly.addPoint(457, 267);
		tempPoly.addPoint(461, 285);
		tempPoly.addPoint(472, 284);
		tempPoly.addPoint(472, 273);
		tempPoly.addPoint(485, 269);
		tempPoly.addPoint(493, 272);
		tempPoly.addPoint(496, 281);
		tempPoly.addPoint(486, 291);
		tempPoly.addPoint(471, 295);
		tempPoly.addPoint(468, 313);
		tempPoly.addPoint(466, 318);
		tempPoly.addPoint(442, 317);
		list.add(new Territory("Mueller", tempPoly));
		
		// FORUM
		tempPoly = new Polygon();
		tempPoly.addPoint(404, 179);
		tempPoly.addPoint(421, 177);
		tempPoly.addPoint(428, 182);
		tempPoly.addPoint(428, 209);
		tempPoly.addPoint(446, 192);
		tempPoly.addPoint(454, 191);
		tempPoly.addPoint(452, 234);
		tempPoly.addPoint(406, 232);
		list.add(new Territory("Forum", tempPoly));
		
		// OSWALD
		tempPoly = new Polygon();
		tempPoly.addPoint(378, 322);
		tempPoly.addPoint(390, 312);
		tempPoly.addPoint(429, 312);
		tempPoly.addPoint(444, 317);
		tempPoly.addPoint(447, 356);
		tempPoly.addPoint(434, 360);
		tempPoly.addPoint(387, 361);
		tempPoly.addPoint(378, 355);
		list.add(new Territory("Oswald Tower", tempPoly));
		
		// CHAMBERS
		tempPoly = new Polygon();
		tempPoly.addPoint(307, 255);
		tempPoly.addPoint(324, 238);
		tempPoly.addPoint(336, 215);
		tempPoly.addPoint(368, 214);
		tempPoly.addPoint(368, 257);
		tempPoly.addPoint(358, 263);
		tempPoly.addPoint(306, 263);
		list.add(new Territory("Chambers", tempPoly));
		
		// LION SHIRE
		tempPoly = new Polygon();
		tempPoly.addPoint(188, 242);
		tempPoly.addPoint(206, 233);
		tempPoly.addPoint(249, 227);
		tempPoly.addPoint(275, 231);
		tempPoly.addPoint(274, 243);
		tempPoly.addPoint(249, 242);
		tempPoly.addPoint(250, 265);
		tempPoly.addPoint(239, 261);
		tempPoly.addPoint(191, 259);
		list.add(new Territory("Lion Shrine", tempPoly));
		
		// EAST QUAD
		tempPoly = new Polygon();
		tempPoly.addPoint(716, 145);
		tempPoly.addPoint(745, 141);
		tempPoly.addPoint(749, 147);
		tempPoly.addPoint(763, 144);
		tempPoly.addPoint(768, 139);
		tempPoly.addPoint(773, 138);
		tempPoly.addPoint(776, 163);
		tempPoly.addPoint(726, 172);
		tempPoly.addPoint(723, 158);
		tempPoly.addPoint(718, 158);
		tempPoly.addPoint(715, 155);
		tempPoly.addPoint(718, 153);
		list.add(new Territory("East Quad", tempPoly));
		
		// NORTH QUAD
		tempPoly = new Polygon();
		tempPoly.addPoint(517, 116);
		tempPoly.addPoint(532, 111);
		tempPoly.addPoint(534, 117);
		tempPoly.addPoint(526, 118);
		tempPoly.addPoint(532, 145);
		tempPoly.addPoint(540, 143);
		tempPoly.addPoint(541, 147);
		tempPoly.addPoint(524, 152);
		list.add(new Territory("North Quad", tempPoly));
		
		// NORTH FOREST
		tempPoly = new Polygon();
		tempPoly.addPoint(389, 134);
		tempPoly.addPoint(394, 176);
		tempPoly.addPoint(420, 175);
		tempPoly.addPoint(429, 171);
		tempPoly.addPoint(448, 148);
		tempPoly.addPoint(442, 121);
		list.add(new Territory("North Forest", tempPoly));
		
		// IST
		tempPoly = new Polygon();
		tempPoly.addPoint(188, 374);
		tempPoly.addPoint(227, 376);
		tempPoly.addPoint(251, 379);
		tempPoly.addPoint(250, 396);
		tempPoly.addPoint(195, 394);
		tempPoly.addPoint(182, 388);
		tempPoly.addPoint(184, 379);
		list.add(new Territory("IST", tempPoly));
		
		// LEONHARD
		tempPoly = new Polygon();
		tempPoly.addPoint(48, 393);
		tempPoly.addPoint(48, 418);
		tempPoly.addPoint(87, 419);
		tempPoly.addPoint(88, 384);
		tempPoly.addPoint(76, 393);
		list.add(new Territory("Leonhard", tempPoly));
		
		// MCELWAIN
		tempPoly = new Polygon();
		tempPoly.addPoint(595, 376);
		tempPoly.addPoint(636, 370);
		tempPoly.addPoint(643, 417);
		tempPoly.addPoint(603, 421);
		list.add(new Territory("McElwain", tempPoly));
		
		// WEST QUAD
		tempPoly = new Polygon();
		tempPoly.addPoint(292, 329);
		tempPoly.addPoint(322, 328);
		tempPoly.addPoint(322, 338);
		tempPoly.addPoint(333, 339);
		tempPoly.addPoint(332, 372);
		tempPoly.addPoint(307, 372);
		tempPoly.addPoint(307, 357);
		tempPoly.addPoint(301, 356);
		tempPoly.addPoint(301, 349);
		tempPoly.addPoint(292, 349);
		list.add(new Territory("West Quad", tempPoly));
		
		// HAMMOND
		tempPoly = new Polygon();
		tempPoly.addPoint(277, 508);
		tempPoly.addPoint(298, 508);
		tempPoly.addPoint(299, 498);
		tempPoly.addPoint(318, 498);
		tempPoly.addPoint(318, 525);
		tempPoly.addPoint(262, 525);
		list.add(new Territory("Hammond", tempPoly));
		
		// CARNEGIE
		tempPoly = new Polygon();
		tempPoly.addPoint(308, 375);
		tempPoly.addPoint(338, 373);
		tempPoly.addPoint(338, 365);
		tempPoly.addPoint(368, 365);
		tempPoly.addPoint(368, 391);
		tempPoly.addPoint(308, 391);
		list.add(new Territory("Carnegie", tempPoly));
		
		// ELECTRICAL ENGINEERING
		tempPoly = new Polygon();
		tempPoly.addPoint(293, 426);
		tempPoly.addPoint(301, 426);
		tempPoly.addPoint(301, 429);
		tempPoly.addPoint(313, 429);
		tempPoly.addPoint(314, 426);
		tempPoly.addPoint(329, 427);
		tempPoly.addPoint(330, 432);
		tempPoly.addPoint(339, 432);
		tempPoly.addPoint(349, 439);
		tempPoly.addPoint(347, 456);
		tempPoly.addPoint(340, 461);
		tempPoly.addPoint(291, 462);
		tempPoly.addPoint(291, 432);
		tempPoly.addPoint(288, 432);
		list.add(new Territory("Electrical Engineering", tempPoly));
		
		// WALKER
		tempPoly = new Polygon();
		tempPoly.addPoint(197, 420);
		tempPoly.addPoint(212, 420);
		tempPoly.addPoint(225, 432);
		tempPoly.addPoint(225, 458);
		tempPoly.addPoint(199, 453);
		tempPoly.addPoint(191, 449);
		tempPoly.addPoint(185, 449);
		tempPoly.addPoint(183, 431);
		list.add(new Territory("Walker", tempPoly));
		
	} // END PopulateTerritoryList
	
	/**
	 * Hard coded method to populate the teams.
	 * @param list List to populate to.
	 */
	private static void PopulateTeamList(ArrayList<Team> list) {
		int transparency = 180;
		list.add(new Team("Unowned"));
		list.add(new Team("Norb's Team", new Color(0, 0, 255, transparency)));
		list.add(new Team("Dave's Team", new Color(255, 51, 153, transparency)));
		list.add(new Team("Sarah/John's Team", new Color(255, 0, 0, transparency)));
		list.add(new Team("Clay's Team", new Color(255, 255, 0, transparency)));
		list.add(new Team("Nimda", new Color(0, 0, 0, transparency)));
	} // END PopulateTeamList
	
	/**
	 * Hard coded method to populate the connections.
	 * @param list List to populate to.
	 */
	private static void PopulateConnectionList(ArrayList<ConnectionLine> list) {
		// Business to Forest Resources
		list.add(new ConnectionLine(624, 127, 634, 125));
		// Business to Ag Sci
		list.add(new ConnectionLine(587, 143, 590, 150));
		// Business to North Quad
		list.add(new ConnectionLine(553, 106, 531, 115));
		// Forest Resources to East Quad
		list.add(new ConnectionLine(683, 124, 719, 147));
		// Forest Resources to Mil Sci
		list.add(new ConnectionLine(665, 126, 694, 318));
		// East Quad to Mil Sci
		list.add(new ConnectionLine(735, 168, 714, 314));
		// Ag Sci to Mil Sci
		list.add(new ConnectionLine(636, 202, 676, 320));
		// Ag Sci to Thomas
		list.add(new ConnectionLine(601, 209, 616, 316));
		// Ag Sci to Ferguson
		list.add(new ConnectionLine(572, 214, 550, 231));
		// Mil Sci to Thomas
		list.add(new ConnectionLine(631, 346, 669, 342));
		// Mil Sci to East Pollock
		list.add(new ConnectionLine(706, 352, 708, 372));
		// Mil Sci to West Pollock
		list.add(new ConnectionLine(677, 356, 675, 375));
		// East Pollock to West Pollock
		list.add(new ConnectionLine(685, 393, 696, 391));
		// East Pollock to Eastview
		list.add(new ConnectionLine(743, 416, 782, 459));
		// West Pollock to East View
		list.add(new ConnectionLine(673, 439, 781, 473));
		// West Pollock to McElwain
		list.add(new ConnectionLine(651, 392, 637, 396));
		// McElwain to South Old Main
		list.add(new ConnectionLine(605, 418, 446, 482));
		// McElwain to Thomas
		list.add(new ConnectionLine(617, 375, 615, 365));
		// McElwain to North Old Main
		list.add(new ConnectionLine(602, 397, 445, 438));
		// McElwain to Bouke
		list.add(new ConnectionLine(598, 389, 564, 373));
		// Thomas to Bouke
		list.add(new ConnectionLine(593, 350, 581, 353));
		// Bouke to North Old Main
		list.add(new ConnectionLine(538, 351, 445, 412));
		// Bouke to Fergeson
		list.add(new ConnectionLine(550, 329, 536, 270));
		// Ferguson to North Quad
		list.add(new ConnectionLine(537, 234, 527, 149));
		// Ferguson to Mueller
		list.add(new ConnectionLine(500, 282, 492, 280));
		// North Quad to North Forest
		list.add(new ConnectionLine(520, 124, 442, 135));
		// North Quad to Forum
		list.add(new ConnectionLine(451, 200, 524, 142));
		// North Forest to Forum
		list.add(new ConnectionLine(412, 170, 414, 180));
		// North Forest to Chambers
		list.add(new ConnectionLine(395, 165, 359, 216));
		// Forum to Mueller
		list.add(new ConnectionLine(441, 230, 448, 273));
		// Forum to Chambers
		list.add(new ConnectionLine(409, 220, 364, 228));
		// Meuller to Oswald
		list.add(new ConnectionLine(446, 313, 439, 320));
		// Oswald to Chambers
		list.add(new ConnectionLine(392, 317, 360, 257));
		// Oswald to Carnegie
		list.add(new ConnectionLine(384, 354, 365, 368));
		// Oswald to North Old Main
		list.add(new ConnectionLine(415, 358, 437, 411));
		// North Old Main to EE
		list.add(new ConnectionLine(394, 454, 346, 448));
		// North Old Main to South Old Main
		list.add(new ConnectionLine(415, 467, 416, 482));
		// South Old Main to Hammond
		list.add(new ConnectionLine(382, 514, 315, 514));
		// Hammond to EE
		list.add(new ConnectionLine(309, 499, 308, 460));
		// Hammond to Walker
		list.add(new ConnectionLine(272, 516, 224, 456));
		// EE to Walker
		list.add(new ConnectionLine(292, 444, 223, 444));
		// EE to Carnegie
		list.add(new ConnectionLine(323, 428, 326, 389));
		// Carnegie to West Quad
		list.add(new ConnectionLine(320, 376, 320, 368));
		// West Halls to Chambers
		list.add(new ConnectionLine(313, 330, 316, 261));
		// West Halls to Lion Shrine
		list.add(new ConnectionLine(294, 330, 246, 261));
		// West Halls to IST
		list.add(new ConnectionLine(294, 346, 248, 380));
		// Chambers to Lion Shrine
		list.add(new ConnectionLine(327, 234, 272, 236));
		// IST to Walker
		list.add(new ConnectionLine(204, 392, 204, 421));
		// IST to Lion Shrine
		list.add(new ConnectionLine(202, 376, 204, 257));
		// IST to Leonhard
		list.add(new ConnectionLine(185, 382, 85, 392));
		// Lion Shrine to Leonhard
		list.add(new ConnectionLine(192, 256, 86, 387));
		// Walker to Leonhard
		list.add(new ConnectionLine(188, 446, 84, 416));

		// East Quad WRAPS Lion Shrine
		list.add(new ConnectionLine(191, 244, 0, 202));
		list.add(new ConnectionLine(773, 160, 857, 202));
		
		// Eastview WRAPS Leonhard
		list.add(new ConnectionLine(827, 457, 857, 432));
		list.add(new ConnectionLine(50, 407, 0, 432));
		
		// North Forest WRAPS South Old Main
		list.add(new ConnectionLine(420, 131, 420, 0));
		list.add(new ConnectionLine(420, 532, 420, 523));
		
	} // END PopulateConnectionList
} // END MainDriver
