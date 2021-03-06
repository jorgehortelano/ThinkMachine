package com.softwaremagico.tm.character.occultism;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.benefices.AvailableBenefice;
import com.softwaremagico.tm.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.character.factions.Faction;

public class Occultism {
	private Map<String, Integer> psiqueValue;
	private Map<String, Integer> darkSideValue;
	private Wyrd extraWyrd;

	// Path --> Set<Power>
	private final Map<String, List<OccultismPower>> selectedPowers;

	public Occultism() {
		selectedPowers = new HashMap<>();
		psiqueValue = new HashMap<>();
		darkSideValue = new HashMap<>();
	}

	public Wyrd getExtraWyrd() {
		return extraWyrd;
	}

	public void setExtraWyrd(int extraWyrd, String language, String moduleName) {
		if (extraWyrd > 0) {
			this.extraWyrd = new Wyrd(language, moduleName, extraWyrd);
		} else {
			this.extraWyrd = null;
		}
	}

	public int getPsiqueLevel(OccultismType occultismType) {
		if (psiqueValue.get(occultismType.getId()) != null) {
			return psiqueValue.get(occultismType.getId());
		}
		return 0;
	}

	public void setPsiqueLevel(OccultismType occultismType, int psyValue, String language, String moduleName,
			Faction faction) throws InvalidPsiqueLevelException {
		if (psyValue < 0) {
			throw new InvalidPsiqueLevelException("Psique level cannot be less than zero.");
		}
		AvailableBenefice noOccult = null;
		try {
			noOccult = AvailableBeneficeFactory.getInstance().getElement("noOccult", language, moduleName);
		} catch (InvalidXmlElementException e) {
			// Module without noocc benefice.
		}
		if (psyValue != 0 && (faction == null || faction.getBenefices().contains(noOccult))) {
			throw new InvalidPsiqueLevelException("Faction '" + faction + "' cannot have psique levels.");
		}
		psiqueValue.put(occultismType.getId(), Integer.valueOf(psyValue));
	}

	public int getDarkSideLevel(OccultismType occultismType) {
		if (darkSideValue.get(occultismType.getId()) != null) {
			return darkSideValue.get(occultismType.getId());
		}
		return 0;
	}

	public void setDarkSideLevel(OccultismType occultismType, int darkSideValue) {
		this.darkSideValue.put(occultismType.getId(), Integer.valueOf(darkSideValue));
	}

	public Map<String, List<OccultismPower>> getSelectedPowers() {
		return selectedPowers;
	}

	public int getTotalSelectedPowers() {
		int total = 0;
		for (final Entry<String, List<OccultismPower>> entry : getSelectedPowers().entrySet()) {
			if (entry.getValue() != null) {
				total += entry.getValue().size();
			}
		}
		return total;
	}

	public boolean addPower(OccultismPath path, OccultismPower power, String language, Faction faction)
			throws InvalidOccultismPowerException {
		if (power == null) {
			throw new InvalidOccultismPowerException("Power cannot be null.");
		}
		// Correct level of psi or teurgy
		if (Objects.equals(path.getOccultismType(), OccultismTypeFactory.getPsi(language, faction.getModuleName()))
				&& power.getLevel() > getPsiqueLevel(OccultismTypeFactory.getPsi(language, faction.getModuleName()))) {
			throw new InvalidPsiqueLevelException("Insufficient psi level to acquire '" + power + "'.");
		}
		if (Objects.equals(path.getOccultismType(), OccultismTypeFactory.getTheurgy(language, faction.getModuleName()))
				&& power.getLevel() > getPsiqueLevel(
						OccultismTypeFactory.getTheurgy(language, faction.getModuleName()))) {
			throw new InvalidPsiqueLevelException("Insufficient theurgy level to acquire '" + power + "'.");
		}
		// Limited to some factions
		if (!path.getFactionsAllowed().isEmpty() && !path.getFactionsAllowed().contains(faction)) {
			throw new InvalidFactionOfPowerException("Power '" + power + "' can only be acquired by  '"
					+ path.getFactionsAllowed() + "' character faction is '" + faction + "'.");
		}

		// Psi must have previous level.
		if (Objects.equals(path.getOccultismType(), OccultismTypeFactory.getPsi(language, faction.getModuleName()))) {
			boolean acquiredLevel = false;
			for (final OccultismPower previousLevelPower : path.getPreviousLevelPowers(power)) {
				if (selectedPowers.get(path.getId()) != null
						&& selectedPowers.get(path.getId()).contains(previousLevelPower)) {
					acquiredLevel = true;
					break;
				}
			}
			if (!acquiredLevel && !path.getPreviousLevelPowers(power).isEmpty()) {
				throw new InvalidPowerLevelException(
						"At least one power of '" + path.getPreviousLevelPowers(power) + "' must be selected.");
			}
		}
		if (selectedPowers.get(path.getId()) == null) {
			selectedPowers.put(path.getId(), new ArrayList<OccultismPower>());
		}

		return selectedPowers.get(path.getId()).add(power);
	}
}
