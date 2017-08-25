package com.softwaremagico.tm.export.pdf;

/*-
 * #%L
 * The Thinking Machine (Core)
 * %%
 * Copyright (C) 2017 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.CostCalculator;
import com.softwaremagico.tm.character.FreeStyleCharacterCreation;
import com.softwaremagico.tm.character.Gender;
import com.softwaremagico.tm.character.OccultismType;
import com.softwaremagico.tm.character.characteristics.CharacteristicImprovement;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.combat.CombatAction;
import com.softwaremagico.tm.character.combat.CombatStyle;
import com.softwaremagico.tm.character.cybernetics.Device;
import com.softwaremagico.tm.character.equipment.Armour;
import com.softwaremagico.tm.character.equipment.Shield;
import com.softwaremagico.tm.character.equipment.Size;
import com.softwaremagico.tm.character.equipment.Weapon;
import com.softwaremagico.tm.character.occultism.OccultismPower;
import com.softwaremagico.tm.character.race.Race;
import com.softwaremagico.tm.character.race.RaceFactory;
import com.softwaremagico.tm.character.skills.AvailableSkillsFactory;
import com.softwaremagico.tm.character.skills.CyberneticSkill;
import com.softwaremagico.tm.character.traits.Benefit;
import com.softwaremagico.tm.character.traits.Blessing;
import com.softwaremagico.tm.language.LanguagePool;
import com.softwaremagico.tm.pdf.complete.CharacterSheet;
import com.softwaremagico.tm.pdf.small.SmallCharacterSheet;

@Test(groups = { "customCharacterGeneration" })
public class CustomCharacters {

	@Test
	public void createPaolaCharacter() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException {
		CharacterPlayer player = new CharacterPlayer("es");
		player.getInfo().setName("#5");
		player.getInfo().setPlayer("Paola");
		player.getInfo().setGender(Gender.FEMALE);
		player.getInfo().setAge(25);
		player.setRace(RaceFactory.getInstance().getElement("Humano", "es"));
		player.getInfo().setPlanet("Ligaheim");
		player.getInfo().setFaction("Ingeniero");
		player.getInfo().setRank("Aprendiz");

		player.getCharacteristics().getCharacteristic(CharacteristicName.STRENGTH).setValue(3);
		player.getCharacteristics().getCharacteristic(CharacteristicName.DEXTERITY).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WITS).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.TECH).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PRESENCE).setValue(3);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WILL).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.FAITH).setValue(3);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Influenciar", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Observar", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Sigilo", "es"), 6);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Vigor", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Abrir Cerraduras", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Armas de Energía", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Ciencia Aplicada", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Ciencia de la Física", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Nave Espacial", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Vehíc. Terrestre", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Cuerpo a Cuerpo", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Empatía", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Guerra", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Investigar", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Mantenimiento de Naves", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Máquina Pensante", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Recuperación Tecnológic.", "es"), 7);

		//player.addBlessing(BlessingFactory.getInstance().getElement("Innovador", "es"));
		//player.addBlessing(BlessingFactory.getInstance().getElement("Enervado", "es"));
		player.addBlessing(new Blessing("Innovador", 2, 2, "Tecnología", "Inventar"));
		player.addBlessing(new Blessing("Enervado", -2, -2, "Presencia", "Entre sirvientes"));
		player.addBlessing(new Blessing("Hacker", 2, 2, "Máquina Pensante", "--"));
		player.addBlessing(new Blessing("Mecánico", 2, 2, "Recuperación Tecnológic", "Reparando"));
		player.addBlessing(new Blessing("Enemigo Animales", -2, -2, "Con Animales", "Excepto combate"));
		player.addBlessing(new Blessing("Marca Horrible", -2, -2, "Influencia", "Si es visible"));

		player.addBenefit(new Benefit("Idioma Turing", 2));
		player.addBenefit(new Benefit("Asociado", 4));
		player.addBenefit(new Benefit("1000 fénix", 4));
		player.addBenefit(new Benefit("Red de Información", 3));

		Device ingeneerEye = new Device("Ojo de Ingeniero", 8, 6, "Normal", "Normal", "Automático", "Oculto", "Autoalimentado");
		ingeneerEye.addCharacteristicImprovement(new CharacteristicImprovement(CharacteristicName.PERCEPTION, 1, false));
		player.getCybernetics().addElement(ingeneerEye);

		Device secondBrain = new Device("Segundo Cerebro", 11, 10, "Normal", "Normal", "Automático", "Oculto", "Autoalimentado");
		secondBrain.addCharacteristicImprovement(new CharacteristicImprovement(CharacteristicName.WITS, 2, true));
		secondBrain.addSkillImprovement(new CyberneticSkill(AvailableSkillsFactory.getInstance().getElement("Saber [Red de Salto]", "es"), 4, true));
		secondBrain.addSkillImprovement(new CyberneticSkill(AvailableSkillsFactory.getInstance().getElement("Saber [Pistola Energía]", "es"), 4, true));
		secondBrain.addSkillImprovement(new CyberneticSkill(AvailableSkillsFactory.getInstance().getElement("Saber [Máquina Pensante]", "es"), 4, true));
		player.getCybernetics().addElement(secondBrain);

		player.setShield(new Shield("De Duelo", 5, 10, 15));

		LanguagePool.clearCache();
		CharacterSheet sheet = new CharacterSheet(player);
		sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Paola.pdf");

		LanguagePool.clearCache();
		SmallCharacterSheet smallSheet = new SmallCharacterSheet(player);
		smallSheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Paola_Small.pdf");

		Assert.assertEquals(CostCalculator.getCost(player), FreeStyleCharacterCreation.FREE_AVAILABLE_POINTS);
	}

	@Test
	public void characterAnaCharacter() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException {
		CharacterPlayer player = new CharacterPlayer("es");
		player.getInfo().setName("Ana");
		player.getInfo().setPlayer("Ana");
		player.getInfo().setGender(Gender.FEMALE);
		player.getInfo().setAge(22);
		player.setRace(RaceFactory.getInstance().getElement("Humano", "es"));
		player.getInfo().setPlanet("Leminkainen");
		player.getInfo().setFaction("Hawkwood");
		player.getInfo().setRank("Caballero");

		player.getCharacteristics().getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.DEXTERITY).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WITS).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.TECH).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WILL).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.FAITH).setValue(4);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Influenciar", "es"), 7);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Observar", "es"), 8);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Sigilo", "es"), 8);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Vigor", "es"), 8);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Abrir Cerraduras", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Arte", "Música",  "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Autocontrol", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Callejear", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Conocim. del Cuerpo", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Embarcación", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Artefacto Cuerpo a C.", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Etiqueta", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Investigar", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Liderazgo", "es"), 2);

		player.addBlessing(new Blessing("Inflexible", 2, 2, "Fortaleza", "Honor en juego"));
		player.addBlessing(new Blessing("Orgulloso", -2, -2, "Voluntad", "Insulta"));
		player.addBlessing(new Blessing("Marca Horrible", -2, -2, "Influencia", "Si es visible"));

		player.addBenefit(new Benefit("Rango Caballero", 4));
		player.addBenefit(new Benefit("Idioma Latín", 2));
		player.addBenefit(new Benefit("Decreto Imperial", 4));
		player.addBenefit(new Benefit("Espada de Flujo", 11));
		player.addBenefit(new Benefit("Vendetta", -2));

		player.getWeapons().addElement(new Weapon("Espada de Flujo", "Ds+Art.", 1, 7, "3", null, null, 8, Size.L, "Plasma"));

		LanguagePool.clearCache();
		CharacterSheet sheet = new CharacterSheet(player);
		sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Ana.pdf");

		LanguagePool.clearCache();
		SmallCharacterSheet smallSheet = new SmallCharacterSheet(player);
		smallSheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Ana_Small.pdf");

		Assert.assertEquals(CostCalculator.getCost(player), FreeStyleCharacterCreation.FREE_AVAILABLE_POINTS);
	}

	@Test
	public void createCarlosCharacter() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException {
		CharacterPlayer player = new CharacterPlayer("es");
		player.getInfo().setName("");
		player.getInfo().setPlayer("Carlos");
		player.getInfo().setGender(Gender.MALE);
		player.getInfo().setAge(28);
		player.setRace(RaceFactory.getInstance().getElement("Humano", "es"));
		player.getInfo().setPlanet("Byzantium Sec.");
		player.getInfo().setFaction("Carroñero");
		player.getInfo().setRank("Genin");

		player.getCharacteristics().getCharacteristic(CharacteristicName.STRENGTH).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.DEXTERITY).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WITS).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.TECH).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WILL).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.FAITH).setValue(3);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Influenciar", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Observar", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Vigor", "es"), 7);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Armas de Fuego", "es"), 8);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Artería", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Autocontrol", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Callejear", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Conocim. del Cuerpo", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Aeronave", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Pelear", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Demoliciones", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Juego", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Liderazgo", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Recuperación Tecnológic.", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Supervivencia", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Torturar", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Vehíc. Terrestre", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Investigar", "es"), 3);

		player.addBlessing(new Blessing("El Hombre", 2, 2, "Influenciar", "Liderar subalternos"));
		player.addBlessing(new Blessing("Posesivo", -2, -2, "Voluntad", "Excluir acción"));
		player.addBlessing(new Blessing("Marca Horrible", -2, -2, "Influencia", "Si es visible"));

		player.addBenefit(new Benefit("Genin", 8));
		player.addBenefit(new Benefit("Contrato de Pasaje", 3));
		player.addBenefit(new Benefit("1000 fénix", 4));

		CombatStyle fightStyle = new CombatStyle("Talón de Acero");
		fightStyle.addElement(new CombatAction("Cadena de Destrucción", null, "3d", "Presa Especial"));
		fightStyle.addElement(new CombatAction("Cabezazo", 2, "4d", "Ignora armadura*"));
		player.getMeleeCombatStyles().add(fightStyle);

		player.getWeapons().addElement(new Weapon("Escopeta", "Ds+Arma Fuego", 0, 7, "30/80", 7, "2", 3, Size.L, null));
		player.getWeapons().addElement(new Weapon("Pistola Automática", "Ds+Arma Fuego", 0, 5, "20/30", 10, "3", 4, Size.S, null));

		LanguagePool.clearCache();
		CharacterSheet sheet = new CharacterSheet(player);
		sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Carlos.pdf");

		LanguagePool.clearCache();
		SmallCharacterSheet smallSheet = new SmallCharacterSheet(player);
		smallSheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Carlos_Small.pdf");

		Assert.assertEquals(CostCalculator.getCost(player), FreeStyleCharacterCreation.FREE_AVAILABLE_POINTS);
	}

	@Test
	public void createNoeliaCharacer() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException {
		CharacterPlayer player = new CharacterPlayer("es");
		player.getInfo().setName("");
		player.getInfo().setPlayer("Noelia");
		player.getInfo().setGender(Gender.FEMALE);
		// player.getInfo().setAge(30);
		player.setRace(RaceFactory.getInstance().getElement("Ur-Obun", "es"));
		// player.setRace(new Race("Ur-Obun", 3, 4, 3, 3, 3, 3, 3, 3, 3, 6, 1, 0, 0, 0, 2));
		player.getInfo().setPlanet("Obun");
		player.getInfo().setFaction("Voavenlohjun");
		player.getInfo().setRank("Novicio");

		player.getCharacteristics().getCharacteristic(CharacteristicName.STRENGTH).setValue(3);
		player.getCharacteristics().getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		player.getCharacteristics().getCharacteristic(CharacteristicName.ENDURANCE).setValue(3);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WITS).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PERCEPTION).setValue(4);
		player.getCharacteristics().getCharacteristic(CharacteristicName.TECH).setValue(3);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WILL).setValue(8);
		player.getCharacteristics().getCharacteristic(CharacteristicName.FAITH).setValue(8);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Influenciar", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Observar", "es"), 6);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Vigor", "es"), 5);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Artería", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Autocontrol", "es"), 6);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Conocim. del Cuerpo", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Empatía", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Etiqueta", "es"), 2);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Investigar", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Saber", "Kelanti",  "es"), 3);

		player.getOccultism().setPsiValue(6);

		player.getOccultism().addElement(new OccultismPower("Mano Levitante", OccultismType.PSI, "Vol+Autoc.", 1, "Sensorial", "Temporal", "", 1));
		player.getOccultism().addElement(new OccultismPower("Mano Lanzadora", OccultismType.PSI, "Vol+Autoc.", 2, "Sensorial", "Temporal", "", 1));
		player.getOccultism().addElement(new OccultismPower("Mano Aplastante", OccultismType.PSI, "Vol+Autoc.", 3, "Sensorial", "Temporal", "", 1));
		player.getOccultism().addElement(new OccultismPower("Mano Duelista", OccultismType.PSI, "Vol+Autoc.", 4, "Sensorial", "Temporal", "", 1));
		player.getOccultism().addElement(new OccultismPower("Mano Nº5", OccultismType.PSI, "", 5, "", "", "", 1, false));
		player.getOccultism().addElement(new OccultismPower("Intuir", OccultismType.PSI, "Vol+Empatía", 1, "Toque", "Instantáneo", "", 1));
		player.getOccultism().addElement(new OccultismPower("Emocionar", OccultismType.PSI, "Pre+Influenciar", 2, "Toque", "Instantáneo", "", 1));
		player.getOccultism().addElement(new OccultismPower("Visión Mental", OccultismType.PSI, "Vol+Empatía", 3, "Toque", "Instantáneo", "", 1));
		player.getOccultism().setExtraWyrd(3);

		player.addBlessing(new Blessing("Recto", 2, 2, "Fe", "Corregir al errado"));
		player.addBlessing(new Blessing("Condescendiente", -2, -2, "Presencia", "Entre incultos"));
		player.addBlessing(new Blessing("Marca Horrible", -2, -2, "Influencia", "Si es visible"));

		player.addBenefit(new Benefit("Rango Novicio", 4));
		player.addBenefit(new Benefit("Idioma Terráqueo", 2));
		player.addBenefit(new Benefit("Idioma Obun", 0));
		player.addBenefit(new Benefit("Estigma", -2));
		player.addBenefit(new Benefit("Huérfano", -1));

		LanguagePool.clearCache();
		CharacterSheet sheet = new CharacterSheet(player);
		sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Noelia.pdf");

		LanguagePool.clearCache();
		SmallCharacterSheet smallSheet = new SmallCharacterSheet(player);
		smallSheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Noelia_Small.pdf");

		Assert.assertEquals(CostCalculator.getCost(player), FreeStyleCharacterCreation.FREE_AVAILABLE_POINTS);
	}

	@Test
	public void createGolemCharacer() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException {
		CharacterPlayer player = new CharacterPlayer("es");
		player.getInfo().setPlayer("PNJ");
		player.getInfo().setName("A (Prototipo A)");
		player.getInfo().setGender(Gender.FEMALE);
		player.setRace(new Race("Gólem", 5, 5, 5, 3, 3, 6, 0, 0, 0, 6, 0, 0, 0, 0, 0));
		player.getInfo().setPlanet("Ligaheim");
		player.getInfo().setFaction("Ingeniero");
		player.getInfo().setAge(1432);

		player.getCharacteristics().getCharacteristic(CharacteristicName.STRENGTH).setValue(12);
		player.getCharacteristics().getCharacteristic(CharacteristicName.DEXTERITY).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.ENDURANCE).setValue(10);
		player.getCharacteristics().getCharacteristic(CharacteristicName.WITS).setValue(5);
		player.getCharacteristics().getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		player.getCharacteristics().getCharacteristic(CharacteristicName.TECH).setValue(5);

		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Observar", "es"), 6);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Pelear", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Vigor", "es"), 7);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Lanzar", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Atletismo", "es"), 4);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Autocontrol", "es"), 5);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Nave Espacial", "es"), 1);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Controlar Vehíc. Terrestre", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Mantenimiento de Naves", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Guerra", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Saber [Red de Salto]", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Saber [Máquina Pensante]", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Máquina Pensante", "es"), 3);
		player.setSkillRank(AvailableSkillsFactory.getInstance().getElement("Recuperación Tecnológic.", "es"), 3);

		player.addBlessing(new Blessing("Crédulo", -2, -2, "Voluntad", "Lo engatusan"));
		player.addBlessing(new Blessing("Justificado", -2, -2, "Voluntad", "Se cuestion su juicio"));

		player.getCybernetics().addElement(new Device("Omnienchufe", 1, 0, "Normal", "Normal", "Automático", "Oculto", ""));
		player.getCybernetics().addElement(new Device("Interfaz de Datos (Turing)", 1, 1, "Normal", "Normal", "Automático", "Oculto", "Turing"));
		player.getCybernetics().addElement(new Device("Armadura", 1, 2, "Normal", "Normal", "Automático", "Oculto", "2d"));

		player.setArmour(new Armour("Piel", 2, false, false, false, false, false, false, false, 5, 0, 0, 0, 0));

		LanguagePool.clearCache();
		CharacterSheet sheet = new CharacterSheet(player);
		sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Golem.pdf");

		Assert.assertEquals(CostCalculator.getCost(player), -2);
	}

}