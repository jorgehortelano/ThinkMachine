package com.softwaremagico.tm.random.profiles;

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

import java.util.Map;
import java.util.Set;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.benefices.AvailableBenefice;
import com.softwaremagico.tm.character.benefices.BeneficeDefinition;
import com.softwaremagico.tm.character.blessings.Blessing;
import com.softwaremagico.tm.character.characteristics.Characteristic;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.equipment.armours.Armour;
import com.softwaremagico.tm.character.equipment.shields.Shield;
import com.softwaremagico.tm.character.equipment.weapons.Weapon;
import com.softwaremagico.tm.character.skills.AvailableSkill;
import com.softwaremagico.tm.random.selectors.IRandomPreference;

public interface IRandomProfile {

	int getExperiencePoints();

	Set<IRandomPreference> getPreferences();

	Set<Characteristic> getCharacteristicsMinimumValues();

	Map<AvailableSkill, Integer> getSkillsMinimumValues();

	Set<Blessing> getBlessings();

	Set<AvailableBenefice> getBenefices();

	void setParent(IRandomProfile randomProfile) throws InvalidXmlElementException;

	public String getLanguage();
	
	public String getModuleName();

	Set<AvailableSkill> getRequiredSkills();

	Set<AvailableSkill> getSuggestedSkills();

	Set<BeneficeDefinition> getSuggestedBenefices();

	Set<BeneficeDefinition> getMandatoryBenefices();

	Characteristic getCharacteristicMinimumValues(CharacteristicName characteristicName);

	Set<Weapon> getMandatoryWeapons();

	Set<Armour> getMandatoryArmours();

	Set<Shield> getMandatoryShields();

}
