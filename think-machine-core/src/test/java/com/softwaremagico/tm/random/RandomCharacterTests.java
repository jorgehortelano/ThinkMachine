package com.softwaremagico.tm.random;

/*-
 * #%L
 * Think Machine (Core)
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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.skills.SkillDefinition;
import com.softwaremagico.tm.character.skills.SkillsDefinitionsFactory;
import com.softwaremagico.tm.language.LanguagePool;
import com.softwaremagico.tm.random.exceptions.DuplicatedPreferenceException;
import com.softwaremagico.tm.random.selectors.TechnologicalPreferences;

@Test(groups = { "randomCharacter" })
public class RandomCharacterTests {

	@Test(expectedExceptions = { DuplicatedPreferenceException.class })
	public void preferencesCollision() throws InvalidXmlElementException, DuplicatedPreferenceException {
		CharacterPlayer characterPlayer = new CharacterPlayer("es");
		new RandomizeCharacter(characterPlayer, 0, TechnologicalPreferences.MEDIEVAL, TechnologicalPreferences.FUTURIST);
	}

	@Test(enabled = false)
	public void basicCharacterCreation() throws InvalidXmlElementException, DuplicatedPreferenceException {
		CharacterPlayer characterPlayer = new CharacterPlayer("es");
		RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0);
		randomizeCharacter.createCharacter();

		LanguagePool.clearCache();
	}

	@Test
	public void readRandomSkillConfiguration() throws InvalidXmlElementException, DuplicatedPreferenceException {
		SkillDefinition skillDefinition = SkillsDefinitionsFactory.getInstance().get("energyGuns", "en");
		Assert.assertEquals(skillDefinition.getRandomDefinition().getMinimumTechLevel(), 5);
	}
}