package com.softwaremagico.tm.character.equipment.shields;

import java.util.ArrayList;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.equipment.EquipmentSelector;
import com.softwaremagico.tm.log.RandomGenerationLog;
import com.softwaremagico.tm.random.exceptions.InvalidRandomElementSelectedException;
import com.softwaremagico.tm.random.selectors.CombatPreferences;
import com.softwaremagico.tm.random.selectors.DifficultLevelPreferences;
import com.softwaremagico.tm.random.selectors.IRandomPreference;
import com.softwaremagico.tm.random.selectors.ShieldPreferences;

public class RandomShield extends EquipmentSelector<Shield> {

    public RandomShield(CharacterPlayer characterPlayer, Set<IRandomPreference> preferences,
                        Set<Shield> mandatoryShields) throws InvalidXmlElementException {
        super(characterPlayer, preferences, mandatoryShields);
    }

    @Override
    public void assign() throws InvalidRandomElementSelectedException, InvalidShieldException {
        final Random random = new Random();

        final ShieldPreferences shieldPreferences = ShieldPreferences.getSelected(getPreferences());
        if (random.nextFloat() < shieldPreferences.getShieldProbability()) {
            final Shield selectedShield = selectElementByWeight();
            if (getCharacterPlayer().getShield() == null) {
                getCharacterPlayer().setShield(selectedShield);
                RandomGenerationLog.info(this.getClass().getName(), "Selected shield '{}'.", selectedShield);
            }
        }
    }

    @Override
    protected Collection<Shield> getAllElements() throws InvalidXmlElementException {
        return ShieldFactory.getInstance().getElements(getCharacterPlayer().getLanguage(), getCharacterPlayer().getModuleName());
    }

    /**
     * Not so expensive shields.
     *
     * @param shield
     * @return
     */
    @Override
    protected int getWeightCostModificator(Shield shield) {
        if (shield.getCost() > getCurrentMoney() / (double) 2) {
            return 5;
        } else {
            return 1;
        }
    }

    @Override
    protected int getWeight(Shield shield) throws InvalidRandomElementSelectedException {
        int weight = super.getWeight(shield);

        // Difficulty modification
        final DifficultLevelPreferences preference = DifficultLevelPreferences.getSelected(getPreferences());
        switch (preference) {
            case VERY_EASY:
            case EASY:
            case MEDIUM:
                break;
            case HARD:
            case VERY_HARD:
                weight *= shield.getHits();
                break;
        }

        // More protection is better.
        if (getPreferences().contains(CombatPreferences.BELLIGERENT)) {
            weight *= shield.getHits();
            RandomGenerationLog.debug(this.getClass().getName(),
                    "Protection multiplicator for '" + shield + "' is '" + shield.getHits() + "'.");
        }

        // shields depending on the purchasing power of the character.
        final int costModificator = getWeightCostModificator(shield);
        RandomGenerationLog.debug(this.getClass().getName(),
                "Cost multiplication for weight for '" + shield + "' is '" + costModificator + "'.");
        weight /= (double) costModificator;

        RandomGenerationLog.debug(this.getClass().getName(), "Total weight for '{}' is '{}'.", shield, weight);
        return weight;
    }

    @Override
    public void validateElement(Shield shield) throws InvalidRandomElementSelectedException {
        super.validateElement(shield);

        // Difficulty modification
        final DifficultLevelPreferences preference = DifficultLevelPreferences.getSelected(getPreferences());
        switch (preference) {
            case VERY_EASY:
            case EASY:
                throw new InvalidRandomElementSelectedException(
                        "Shield '" + shield + "' are not allowed by selected preference '" + preference + "'.");
            default:
                break;
        }
    }

    @Override
    protected void assignIfMandatory(Shield element) throws InvalidXmlElementException {
        return;
    }

    @Override
    protected void assignMandatoryValues(Set<Shield> mandatoryValues) throws InvalidXmlElementException {
        // We only assign the most expensive one.
        if (!mandatoryValues.isEmpty()) {
            final List<Shield> sortedShields = new ArrayList<>(mandatoryValues);
            Collections.sort(sortedShields, new Comparator<Shield>() {

                @Override
                public int compare(Shield shield0, Shield shield1) {
                    return Float.compare(shield0.getCost(), shield1.getCost());
                }
            });
            getCharacterPlayer().setShield(sortedShields.get(0));
        }
    }
}
