package com.softwaremagico.tm.random;

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

import java.util.Set;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.equipment.weapons.Weapon;
import com.softwaremagico.tm.character.equipment.weapons.WeaponType;
import com.softwaremagico.tm.log.RandomGenerationLog;
import com.softwaremagico.tm.random.selectors.IRandomPreference;

public class RandomRangeWeapon extends RandomWeapon {

	protected RandomRangeWeapon(CharacterPlayer characterPlayer, Set<IRandomPreference> preferences) throws InvalidXmlElementException {
		super(characterPlayer, preferences);
	}

	@Override
	protected Set<WeaponType> weaponTypesFilter() {
		return WeaponType.getRangedTypes();
	}

	@Override
	protected int getWeightCostModificator(Weapon weapon) {
		if (weapon.getCost() > getCurrentMoney() / 1.1) {
			return 100;
		} else if (weapon.getCost() > getCurrentMoney() / 2) {
			return 7;
		} else if (weapon.getCost() > getCurrentMoney() / 3) {
			return 5;
		} else if (weapon.getCost() > getCurrentMoney() / 4) {
			return 4;
		} else if (weapon.getCost() > getCurrentMoney() / 5) {
			return 3;
		} else if (weapon.getCost() > getCurrentMoney() / 10) {
			return 2;
		} else if (weapon.getCost() > getCurrentMoney() / 20) {
			return 1;
		} else {
			// No so cheap weapons.
			return MAX_PROBABILITY;
		}
	}

	@Override
	protected int getWeightTechModificator(Weapon weapon) {
		int weight = 0;
		// Similar tech level preferred.
		weight += MAX_PROBABILITY / Math.pow(10, 2 * (getCharacterPlayer().getCharacteristic(CharacteristicName.TECH).getValue() - weapon.getTechLevel()));
		RandomGenerationLog.debug(
				this.getClass().getName(),
				"Weight tech bonus for '" + weapon + "' is '" + MAX_PROBABILITY
						/ Math.pow(10, 2 * (getCharacterPlayer().getCharacteristic(CharacteristicName.TECH).getValue() - weapon.getTechLevel())) + "'.");
		if (weight <= 0) {
			weight = 0;
		}
		return weight;
	}
}
