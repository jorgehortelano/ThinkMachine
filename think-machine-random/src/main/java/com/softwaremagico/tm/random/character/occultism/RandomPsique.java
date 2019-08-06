package com.softwaremagico.tm.random.character.occultism;

/*-
 * #%L
 * Think Machine (Core)
 * %%
 * Copyright (C) 2017 - 2018 Softwaremagico
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

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import com.softwaremagico.tm.random.RandomSelector;
import com.softwaremagico.tm.random.exceptions.ImpossibleToAssignMandatoryElementException;
import com.softwaremagico.tm.random.exceptions.InvalidRandomElementSelectedException;
import com.softwaremagico.tm.random.selectors.IGaussianDistribution;
import com.softwaremagico.tm.random.selectors.IRandomPreference;
import com.softwaremagico.tm.random.selectors.PsiqueLevelPreferences;
import com.softwaremagico.tm.rules.InvalidXmlElementException;
import com.softwaremagico.tm.rules.character.CharacterPlayer;
import com.softwaremagico.tm.rules.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.rules.character.factions.FactionGroup;
import com.softwaremagico.tm.rules.character.occultism.OccultismType;
import com.softwaremagico.tm.rules.character.occultism.OccultismTypeFactory;
import com.softwaremagico.tm.rules.log.RandomGenerationLog;

public class RandomPsique extends RandomSelector<OccultismType> {

	public RandomPsique(CharacterPlayer characterPlayer, Set<IRandomPreference> preferences)
			throws InvalidXmlElementException {
		super(characterPlayer, preferences);
	}

	@Override
	public void assign() throws InvalidRandomElementSelectedException, InvalidXmlElementException {
		// Select which type of psique.
		final OccultismType selectedOccultismType = selectElementByWeight();
		RandomGenerationLog.info(this.getClass().getName(), "Assinged psique '" + selectedOccultismType + "'.");
		// Select a level of psique.
		final int level = assignLevelOfPsique(selectedOccultismType);
		RandomGenerationLog.info(this.getClass().getName(), "Assinged psique level of '" + level + "'.");
		// Assign to the character.
		getCharacterPlayer().setPsiqueLevel(selectedOccultismType, level);
	}

	@Override
	protected Collection<OccultismType> getAllElements() throws InvalidXmlElementException {
		return OccultismTypeFactory.getInstance().getElements(getCharacterPlayer().getLanguage());
	}

	@Override
	protected int getWeight(OccultismType element) throws InvalidRandomElementSelectedException {
		// Church factions must have always theurgy.
		if (Objects.equals(element, OccultismTypeFactory.getPsi(getCharacterPlayer().getLanguage()))) {
			if (getCharacterPlayer().getFaction().getFactionGroup() == FactionGroup.CHURCH) {
				throw new InvalidRandomElementSelectedException("Psi not allowed to church factions");
			}
			// No church factions have psi.
		} else if (Objects.equals(element, OccultismTypeFactory.getTheurgy(getCharacterPlayer().getLanguage()))) {
			if (getCharacterPlayer().getFaction().getFactionGroup() != FactionGroup.CHURCH) {
				throw new InvalidRandomElementSelectedException("Theurgy restricted to church factions");
			}
		}
		return 1;
	}

	private int assignLevelOfPsique(OccultismType psique) throws InvalidXmlElementException {
		// A curse does not allow occultism.
		if (getCharacterPlayer().getAfflictions().contains(
				AvailableBeneficeFactory.getInstance().getElement("noOccult", getCharacterPlayer().getLanguage()))) {
			return 0;
		}
		final IGaussianDistribution psiqueLevelSelector = PsiqueLevelPreferences.getSelected(getPreferences());
		int maxLevelSelected = psiqueLevelSelector.randomGaussian();
		if (maxLevelSelected > psiqueLevelSelector.maximum()) {
			maxLevelSelected = psiqueLevelSelector.maximum();
		}
		return maxLevelSelected;
	}

	@Override
	protected void assignIfMandatory(OccultismType element) throws InvalidXmlElementException,
			ImpossibleToAssignMandatoryElementException {
		return;
	}

	@Override
	protected void assignMandatoryValues(Set<OccultismType> mandatoryValues) throws InvalidXmlElementException {
		return;
	}
}