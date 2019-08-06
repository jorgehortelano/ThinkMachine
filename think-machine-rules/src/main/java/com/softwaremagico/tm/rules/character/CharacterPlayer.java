package com.softwaremagico.tm.rules.character;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import com.softwaremagico.tm.rules.InvalidXmlElementException;
import com.softwaremagico.tm.rules.character.benefices.AvailableBenefice;
import com.softwaremagico.tm.rules.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.rules.character.benefices.BeneficeAlreadyAddedException;
import com.softwaremagico.tm.rules.character.benefices.BeneficeClassification;
import com.softwaremagico.tm.rules.character.benefices.BeneficeGroup;
import com.softwaremagico.tm.rules.character.benefices.BeneficeSpecialization;
import com.softwaremagico.tm.rules.character.benefices.InvalidBeneficeException;
import com.softwaremagico.tm.rules.character.blessings.Blessing;
import com.softwaremagico.tm.rules.character.blessings.BlessingAlreadyAddedException;
import com.softwaremagico.tm.rules.character.blessings.BlessingClassification;
import com.softwaremagico.tm.rules.character.blessings.TooManyBlessingsException;
import com.softwaremagico.tm.rules.character.characteristics.Characteristic;
import com.softwaremagico.tm.rules.character.characteristics.CharacteristicDefinition;
import com.softwaremagico.tm.rules.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.rules.character.characteristics.CharacteristicType;
import com.softwaremagico.tm.rules.character.characteristics.CharacteristicsDefinitionFactory;
import com.softwaremagico.tm.rules.character.combat.CombatStyle;
import com.softwaremagico.tm.rules.character.combat.CombatStyleFactory;
import com.softwaremagico.tm.rules.character.combat.CombatStyleGroup;
import com.softwaremagico.tm.rules.character.creation.CostCalculator;
import com.softwaremagico.tm.rules.character.creation.FreeStyleCharacterCreation;
import com.softwaremagico.tm.rules.character.cybernetics.CyberneticDevice;
import com.softwaremagico.tm.rules.character.cybernetics.Cybernetics;
import com.softwaremagico.tm.rules.character.cybernetics.ICyberneticDevice;
import com.softwaremagico.tm.rules.character.cybernetics.RequiredCyberneticDevicesException;
import com.softwaremagico.tm.rules.character.cybernetics.SelectedCyberneticDevice;
import com.softwaremagico.tm.rules.character.cybernetics.TooManyCyberneticDevicesException;
import com.softwaremagico.tm.rules.character.equipment.armours.Armour;
import com.softwaremagico.tm.rules.character.equipment.armours.InvalidArmourException;
import com.softwaremagico.tm.rules.character.equipment.shields.InvalidShieldException;
import com.softwaremagico.tm.rules.character.equipment.shields.Shield;
import com.softwaremagico.tm.rules.character.equipment.weapons.Weapon;
import com.softwaremagico.tm.rules.character.equipment.weapons.WeaponFactory;
import com.softwaremagico.tm.rules.character.equipment.weapons.WeaponType;
import com.softwaremagico.tm.rules.character.equipment.weapons.Weapons;
import com.softwaremagico.tm.rules.character.factions.Faction;
import com.softwaremagico.tm.rules.character.occultism.InvalidOccultismPowerException;
import com.softwaremagico.tm.rules.character.occultism.InvalidPsiqueLevelException;
import com.softwaremagico.tm.rules.character.occultism.Occultism;
import com.softwaremagico.tm.rules.character.occultism.OccultismPower;
import com.softwaremagico.tm.rules.character.occultism.OccultismType;
import com.softwaremagico.tm.rules.character.races.InvalidRaceException;
import com.softwaremagico.tm.rules.character.races.Race;
import com.softwaremagico.tm.rules.character.races.RaceCharacteristic;
import com.softwaremagico.tm.rules.character.skills.AvailableSkill;
import com.softwaremagico.tm.rules.character.skills.AvailableSkillsFactory;
import com.softwaremagico.tm.rules.character.skills.InvalidSkillException;
import com.softwaremagico.tm.rules.character.skills.SelectedSkill;
import com.softwaremagico.tm.rules.character.skills.Skill;
import com.softwaremagico.tm.rules.character.skills.SkillDefinition;
import com.softwaremagico.tm.rules.character.skills.SkillGroup;
import com.softwaremagico.tm.rules.character.skills.SkillsDefinitionsFactory;
import com.softwaremagico.tm.rules.character.skills.Specialization;
import com.softwaremagico.tm.rules.character.values.Bonification;
import com.softwaremagico.tm.rules.character.values.IValue;
import com.softwaremagico.tm.rules.character.values.SpecialValue;
import com.softwaremagico.tm.rules.character.values.SpecialValuesFactory;
import com.softwaremagico.tm.rules.character.values.StaticValue;
import com.softwaremagico.tm.rules.log.CostCalculatorLog;
import com.softwaremagico.tm.rules.log.MachineLog;
import com.softwaremagico.tm.rules.txt.CharacterSheet;

public class CharacterPlayer {
	private final String language;

	// Basic description of the character.
	private CharacterInfo info;

	private Race race;

	private Faction faction;

	// Characteristics.
	private Map<String, Characteristic> characteristics;

	private transient Map<CharacteristicType, Set<Characteristic>> characteristicsByType;

	// All Psi/Teurgy powers
	private Occultism occultism;

	// Skills
	private Map<String, SelectedSkill> skills;

	private List<Blessing> blessings;

	private List<AvailableBenefice> benefices;

	private Cybernetics cybernetics;

	private Weapons weapons;

	private Armour armour;

	private Shield shield;

	private int experience = 0;

	public CharacterPlayer() {
		this("en");
	}

	public CharacterPlayer(String language) {
		this.language = language;
		reset();
	}

	private void reset() {
		info = new CharacterInfo();
		initializeCharacteristics();
		occultism = new Occultism();
		skills = new HashMap<>();
		blessings = new ArrayList<>();
		benefices = new ArrayList<>();
		cybernetics = new Cybernetics();
		weapons = new Weapons();
		try {
			setArmour(null);
		} catch (InvalidArmourException e) {
			MachineLog.errorMessage(this.getClass().getName(), e);
		}
		try {
			setShield(null);
		} catch (InvalidShieldException e) {
			MachineLog.errorMessage(this.getClass().getName(), e);
		}
	}

	public CharacterInfo getInfo() {
		return info;
	}

	public void setInfo(CharacterInfo info) {
		this.info = info;
	}

	private void initializeCharacteristics() {
		characteristics = new HashMap<String, Characteristic>();
		for (final CharacteristicDefinition characteristicDefinition : CharacteristicsDefinitionFactory.getInstance()
				.getAll(language)) {
			characteristics.put(characteristicDefinition.getId(), new Characteristic(characteristicDefinition));
		}
	}

	/**
	 * Gets the starting value for a characteristic depending on the race.
	 * 
	 * @param characteristicName
	 * @return the initial value of a characteristic.
	 */
	public Integer getStartingValue(CharacteristicName characteristicName) {
		if (CharacteristicName.DEFENSE.equals(characteristicName)) {
			return 1;
		}
		if (CharacteristicName.INITIATIVE.equals(characteristicName)) {
			return getStartingValue(CharacteristicName.DEXTERITY) + getStartingValue(CharacteristicName.WITS);
		}
		return getRaceCharacteristicStartingValue(characteristicName);
	}

	public Integer getStartingValue(AvailableSkill skill) {
		if (skill.getSkillDefinition().isNatural()) {
			return FreeStyleCharacterCreation.getMinInitialNaturalSkillsValues(getInfo().getAge());
		}
		return 0;
	}

	private Integer getRawValue(CharacteristicName characteristicName) {
		if (CharacteristicName.INITIATIVE.equals(characteristicName)) {
			return getValue(CharacteristicName.DEXTERITY) + getValue(CharacteristicName.WITS);
		}
		if (CharacteristicName.DEFENSE.equals(characteristicName)) {
			return getStartingValue(characteristicName);
		}
		if (CharacteristicName.MOVEMENT.equals(characteristicName)) {
			return getStartingValue(characteristicName);
		}
		final Integer value = characteristics.get(characteristicName.getId()).getValue();

		return value;
	}

	public Integer getValue(CharacteristicName characteristicName) {
		if (CharacteristicName.INITIATIVE.equals(characteristicName)) {
			return getValue(CharacteristicName.DEXTERITY) + getValue(CharacteristicName.WITS)
					+ getBlessingModificationAlways(
							CharacteristicsDefinitionFactory.getInstance().get(characteristicName, language));
		}
		if (CharacteristicName.DEFENSE.equals(characteristicName)) {
			return getStartingValue(characteristicName) + getBlessingModificationAlways(
					CharacteristicsDefinitionFactory.getInstance().get(characteristicName, language));
		}
		if (CharacteristicName.MOVEMENT.equals(characteristicName)) {
			return getStartingValue(characteristicName) + getBlessingModificationAlways(
					CharacteristicsDefinitionFactory.getInstance().get(characteristicName, language));
		}
		Integer value = characteristics.get(characteristicName.getId()).getValue();

		// Add cybernetics modifications
		value += getCyberneticsModificationAlways(getCharacteristic(characteristicName));

		// Add modifications always applied.
		value += getBlessingModificationAlways(
				CharacteristicsDefinitionFactory.getInstance().get(characteristicName, language));

		return value;
	}

	public Integer getVitalityValue() throws InvalidXmlElementException {
		return getValue(CharacteristicName.ENDURANCE) + 5 + getBlessingModificationAlways(
				SpecialValuesFactory.getInstance().getElement(SpecialValue.VITALITY, getLanguage()));
	}

	public Integer getBasicWyrdValue() {
		return Math.max(getValue(CharacteristicName.WILL), getValue(CharacteristicName.FAITH));
	}

	public Integer getWyrdValue() throws InvalidXmlElementException {
		return getBasicWyrdValue() + occultism.getExtraWyrd() + getBlessingModificationAlways(
				SpecialValuesFactory.getInstance().getElement(SpecialValue.WYRD, getLanguage()));
	}

	public void setSkillRank(AvailableSkill availableSkill, int value) throws InvalidSkillException {
		if (availableSkill == null) {
			throw new InvalidSkillException("Null skill is not allowed here.");
		}
		final SelectedSkill skillWithRank = new SelectedSkill(availableSkill, value, false);
		skills.put(availableSkill.getUniqueId(), skillWithRank);
	}

	private Integer getSkillAssignedRanks(Skill<?> skill) {
		if (skills.get(skill.getUniqueId()) == null) {
			if (SkillsDefinitionsFactory.getInstance().isNaturalSkill(skill.getName(), language)) {
				return FreeStyleCharacterCreation.getMinInitialNaturalSkillsValues(getInfo().getAge());
			}
			return 0;
		}
		return skills.get(skill.getUniqueId()).getValue();
	}

	/**
	 * All ranks assigned to an skill, avoiding any blessing modification.
	 * 
	 * @param skill Skill to check.
	 * @return ranks of the skill.
	 */
	public Integer getSkillAssignedRanks(AvailableSkill skill) {
		final SelectedSkill selectedSkill = getSelectedSkill(skill);
		// Use the skill with generalization.
		if (selectedSkill != null) {
			return getSkillAssignedRanks(selectedSkill);
		} else {
			// Use a simple skill if not generalization.
			if (!skill.getSkillDefinition().isSpecializable() || skill.getSkillDefinition().isNatural()) {
				return getSkillAssignedRanks((Skill<AvailableSkill>) skill);
			} else {
				return 0;
			}
		}
	}

	/**
	 * All ranks assigned to an skill plus blessing and cybernetics modification.
	 * 
	 * @param skill Skill to check.
	 * @return ranks of the skill.
	 */
	public Integer getSkillTotalRanks(AvailableSkill skill) {
		final Integer cyberneticBonus = getCyberneticsValue(skill);
		Integer skillValue = getSkillAssignedRanks(skill);
		// Set the modifications of blessings.
		if (skills.get(skill.getUniqueId()) != null) {
			skillValue += getBlessingModificationAlways(
					skills.get(skill.getUniqueId()).getAvailableSkill().getSkillDefinition());
		}
		// Cybernetics only if better.
		if (cyberneticBonus != null) {
			return Math.max(skillValue, cyberneticBonus);
		}
		return skillValue;
	}

	private Integer getCyberneticsValue(Skill<?> skill) {
		int maxValue = 0;
		for (final ICyberneticDevice device : cybernetics.getElements()) {
			for (final StaticValue staticValue : device.getStaticValues()) {
				if (Objects.equals(staticValue.getAffects().getId(), skill.getId())) {
					if (maxValue < staticValue.getValue()) {
						maxValue = staticValue.getValue();
					}
				}
			}
		}
		return maxValue;
	}

	public boolean isSkillSpecial(AvailableSkill availableSkill) {
		if (getSelectedSkill(availableSkill) != null && getSelectedSkill(availableSkill).hasCost()) {
			return true;
		}

		return false;
	}

	/**
	 * Gets the selected skill by specialization.
	 * 
	 * @param skill
	 * @return a selectedSkill
	 */
	public SelectedSkill getSelectedSkill(AvailableSkill skill) {
		for (final SelectedSkill selectedSkill : skills.values()) {
			if (Objects.equals(selectedSkill.getAvailableSkill(), skill)) {
				return selectedSkill;
			}
		}
		return null;
	}

	public String getLanguage() {
		return language;
	}

	private Occultism getOccultism() {
		return occultism;
	}

	public void addBlessing(Blessing blessing) throws TooManyBlessingsException, BlessingAlreadyAddedException {
		if (getAllBlessings().contains(blessing)) {
			throw new BlessingAlreadyAddedException("Character already has blessing '" + blessing + "'!");
		}
		if (blessing.getBlessingClassification() == BlessingClassification.CURSE) {
			if (CostCalculator.getBlessingCosts(getCurses()) + blessing.getCost() >= FreeStyleCharacterCreation
					.getMaxCursePoints(getInfo().getAge())) {
				throw new TooManyBlessingsException(
						"Only a total of '" + FreeStyleCharacterCreation.getMaxCursePoints(getInfo().getAge())
								+ "' points are allowed for curses.");
			}
		}
		// Only 7 values can be modified by blessings.
		if (getBlessingModificationsNumber() + blessing.getBonifications().size() > FreeStyleCharacterCreation
				.getMaxBlessingModifications(getInfo().getAge())) {
			throw new TooManyBlessingsException(
					"Only a total of '" + FreeStyleCharacterCreation.getMaxBlessingModifications(getInfo().getAge())
							+ "' modifications are allowed for blessings. Now exists '" + getAllBlessings()
							+ "' and adding '" + blessing + "'.");
		}
		// Only 7 blessings as max.
		if (getAllBlessings().size() > FreeStyleCharacterCreation.getMaxBlessingModifications(getInfo().getAge())) {
			throw new TooManyBlessingsException(
					"Only a total of '" + FreeStyleCharacterCreation.getMaxBlessingModifications(getInfo().getAge())
							+ "' modifications are allowed for blessings. Now exists '" + getAllBlessings()
							+ "' and adding '" + blessing + "'.");
		}
		blessings.add(blessing);
		Collections.sort(blessings);
	}

	private int getBlessingModificationsNumber() {
		int counter = 0;
		for (final Blessing blessing : getAllBlessings()) {
			counter += blessing.getBonifications().size();
		}
		return counter;
	}

	public List<Blessing> getCurses() {
		final List<Blessing> curses = new ArrayList<>();

		for (final Blessing blessing : getAllBlessings()) {
			if (blessing.getBlessingClassification() == BlessingClassification.CURSE) {
				curses.add(blessing);
			}
		}

		Collections.sort(curses);
		return Collections.unmodifiableList(curses);
	}

	public List<Blessing> getBlessings() {
		final List<Blessing> blessings = new ArrayList<>();

		for (final Blessing blessing : getAllBlessings()) {
			if (blessing.getBlessingClassification() == BlessingClassification.BLESSING) {
				blessings.add(blessing);
			}
		}

		Collections.sort(blessings);
		return Collections.unmodifiableList(blessings);
	}

	/**
	 * Return all blessings include the factions blessings and curses.
	 * 
	 * @return a list of blessings
	 */
	public List<Blessing> getAllBlessings() {
		final List<Blessing> allBlessings = new ArrayList<>(blessings);
		// Add faction blessings
		if (getFaction() != null) {
			allBlessings.addAll(getFaction().getBlessings());
		}
		Collections.sort(allBlessings);
		return Collections.unmodifiableList(allBlessings);
	}

	public void addBenefice(AvailableBenefice benefice) throws InvalidBeneficeException, BeneficeAlreadyAddedException {
		if (benefice.getBeneficeDefinition().getGroup() == BeneficeGroup.RESTRICTED) {
			throw new InvalidBeneficeException("Benefice '" + benefice + "' is restricted and cannot be added.");
		}
		if (getBenefice(benefice.getBeneficeDefinition().getId()) != null) {
			throw new BeneficeAlreadyAddedException("Character already has benefice '" + benefice + "'!");
		}
		benefices.add(benefice);
		Collections.sort(benefices);
	}

	/**
	 * Return all benefices include the factions benefices.
	 * 
	 * @return a list of available benefices
	 */
	public List<AvailableBenefice> getAllBenefices() throws InvalidXmlElementException {
		final List<AvailableBenefice> positiveBenefices = new ArrayList<>();
		for (final AvailableBenefice benefice : benefices) {
			if (benefice.getBeneficeClassification() == BeneficeClassification.BENEFICE) {
				positiveBenefices.add(benefice);
			}
		}
		// Add faction benefices
		if (getFaction() != null && getFaction().getBenefices() != null) {
			for (final AvailableBenefice benefice : getFaction().getBenefices()) {
				if (benefice.getBeneficeClassification() == BeneficeClassification.BENEFICE) {
					positiveBenefices.add(benefice);
				}
			}
		}
		Collections.sort(positiveBenefices);
		return Collections.unmodifiableList(positiveBenefices);
	}

	public void removeBenefice(AvailableBenefice benefice) {
		benefices.remove(benefice);
	}

	public AvailableBenefice getBenefice(String beneficeDefinitionId) {
		for (final AvailableBenefice benefice : benefices) {
			if (benefice.getBeneficeDefinition().getId().equalsIgnoreCase(beneficeDefinitionId)) {
				return benefice;
			}
		}
		return null;
	}

	public List<AvailableBenefice> getAfflictions() {
		final List<AvailableBenefice> afflictions = new ArrayList<>();
		for (final AvailableBenefice affliction : benefices) {
			if (affliction.getBeneficeClassification() == BeneficeClassification.AFFLICTION) {
				afflictions.add(affliction);
			}
		}
		// Add faction afflictions
		if (getFaction() != null && getFaction().getBenefices() != null) {
			for (final AvailableBenefice affliction : getFaction().getBenefices()) {
				if (affliction.getBeneficeClassification() == BeneficeClassification.AFFLICTION) {
					afflictions.add(affliction);
				}
			}
		}
		return Collections.unmodifiableList(afflictions);
	}

	private Cybernetics getCyberneticList() {
		return cybernetics;
	}

	public List<SelectedCyberneticDevice> getCybernetics() {
		return cybernetics.getElements();
	}

	public boolean hasCyberneticDevice(CyberneticDevice cyberneticDevice) {
		for (final SelectedCyberneticDevice device : getCybernetics()) {
			if (Objects.equals(device.getCyberneticDevice(), cyberneticDevice)) {
				return true;
			}
		}
		return false;
	}

	public SelectedCyberneticDevice addCybernetics(CyberneticDevice cyberneticDevice)
			throws TooManyCyberneticDevicesException, RequiredCyberneticDevicesException {
		if (getCyberneticsIncompatibility() + cyberneticDevice.getIncompatibility() > Cybernetics
				.getMaxCyberneticIncompatibility(this)) {
			throw new TooManyCyberneticDevicesException(
					"Cybernatic device cannot be added due to incompatibility requirements. Current incompatibility '"
							+ getCyberneticsIncompatibility() + "', device incompatibility '"
							+ cyberneticDevice.getIncompatibility()
							+ "', maximum incompatibility for this character is '"
							+ Cybernetics.getMaxCyberneticIncompatibility(this) + "'.");
		}
		if (cyberneticDevice.getRequirement() != null) {
			if (!hasCyberneticDevice(cyberneticDevice.getRequirement())) {
				throw new RequiredCyberneticDevicesException("Cybernetic device '" + cyberneticDevice + "' requires '"
						+ cyberneticDevice.getRequirement() + "' to be added to the character.");
			}
		}
		final SelectedCyberneticDevice selectedCiberneticDevice = new SelectedCyberneticDevice(cyberneticDevice);
		getCyberneticList().addElement(selectedCiberneticDevice);
		return selectedCiberneticDevice;
	}

	public int getCyberneticsIncompatibility() {
		int incompatibility = 0;
		for (final SelectedCyberneticDevice device : getCybernetics()) {
			incompatibility += device.getIncompatibility();
		}
		return incompatibility;
	}

	public void addWeapon(Weapon weapon) {
		weapons.addElement(weapon);
	}

	public List<Weapon> getAllWeapons(WeaponType type) {
		final List<Weapon> allWeapons = new ArrayList<>(getAllWeapons());
		final List<Weapon> selectedWeapons = new ArrayList<>();
		for (final Weapon weapon : allWeapons) {
			if (Objects.equals(weapon.getType(), type)) {
				selectedWeapons.add(weapon);
			}
		}
		Collections.sort(selectedWeapons);
		return Collections.unmodifiableList(selectedWeapons);
	}

	public List<Weapon> getAllWeapons() {
		final List<Weapon> allWeapons = new ArrayList<>();
		allWeapons.addAll(weapons.getElements());
		try {
			// Weapons from benefices.
			for (final AvailableBenefice benefice : getAllBenefices()) {
				try {
					allWeapons.add(WeaponFactory.getInstance().getElement(benefice.getId(), getLanguage()));
				} catch (InvalidXmlElementException ixmle) {
					// Benefice is not a weapon.
				}
			}
			// Weapons from cybernetics.
			for (final ICyberneticDevice cyberneticDevice : getCyberneticList().getElements()) {
				if (cyberneticDevice.getWeapon() != null) {
					allWeapons.add(cyberneticDevice.getWeapon());
				}
			}
		} catch (InvalidXmlElementException e) {
			MachineLog.errorMessage(this.getClass().getName(), e);
		}
		Collections.sort(allWeapons);
		return Collections.unmodifiableList(allWeapons);
	}

	public Armour getArmour() {
		return armour;
	}

	public void setArmour(Armour armour) throws InvalidArmourException {
		if (getShield() != null && armour != null && !armour.getAllowedShields().contains(getShield())) {
			throw new InvalidArmourException(
					"Armour '" + armour + "' is not compatible with shield '" + getShield() + "'.");
		}
		this.armour = armour;
	}

	public Shield getShield() {
		return shield;
	}

	public void setShield(Shield shield) throws InvalidShieldException {
		if (getArmour() != null && shield != null && !getArmour().getAllowedShields().contains(shield)) {
			throw new InvalidShieldException(
					"Shield '" + shield + "' is not compatible with armour '" + getArmour() + "'.");
		}
		this.shield = shield;
	}

	public List<CombatStyle> getMeleeCombatStyles() throws InvalidXmlElementException {
		final List<CombatStyle> meleeCombatActions = new ArrayList<>();
		for (final AvailableBenefice beneficeDefinition : getAllBenefices()) {
			if (beneficeDefinition.getBeneficeDefinition().getGroup() == BeneficeGroup.FIGHTING) {
				final CombatStyle combatStyle = CombatStyleFactory.getInstance().getElement(beneficeDefinition.getId(),
						getLanguage());
				if (combatStyle.getGroup() == CombatStyleGroup.MELEE
						|| combatStyle.getGroup() == CombatStyleGroup.FIGHT) {
					meleeCombatActions.add(combatStyle);
				}
			}
		}
		Collections.sort(meleeCombatActions);
		return Collections.unmodifiableList(meleeCombatActions);
	}

	public List<CombatStyle> getRangedCombatStyles() throws InvalidXmlElementException {
		final List<CombatStyle> rangedCombatActions = new ArrayList<>();
		for (final AvailableBenefice beneficeDefinition : getAllBenefices()) {
			if (beneficeDefinition.getBeneficeDefinition().getGroup() == BeneficeGroup.FIGHTING) {
				final CombatStyle combatStyle = CombatStyleFactory.getInstance().getElement(beneficeDefinition.getId(),
						getLanguage());
				if (combatStyle.getGroup() == CombatStyleGroup.RANGED) {
					rangedCombatActions.add(combatStyle);
				}
			}
		}
		Collections.sort(rangedCombatActions);
		return Collections.unmodifiableList(rangedCombatActions);
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) throws InvalidRaceException {
		if (race == null) {
			throw new InvalidRaceException("Race is null!");
		}
		this.race = race;
	}

	private int getRaceCharacteristicStartingValue(CharacteristicName characteristicName) {
		if (getRace() != null) {
			final RaceCharacteristic value = getRace().getParameter(characteristicName);
			if (value != null) {
				return value.getInitialValue();
			}
			return Characteristic.DEFAULT_INITIAL_VALUE;
		}
		return 0;
	}

	public List<AvailableSkill> getNaturalSkills() throws InvalidXmlElementException {
		final List<AvailableSkill> naturalSkills = new ArrayList<>();
		// Adds default planet and faction.
		for (final AvailableSkill skill : AvailableSkillsFactory.getInstance().getNaturalSkills(language)) {
			if (skill.getSkillDefinition().getId().equals(SkillDefinition.PLANETARY_LORE_ID)) {
				if (getInfo().getPlanet() != null) {
					skill.setSpecialization(new Specialization(getInfo().getPlanet().getName().toLowerCase(),
							getInfo().getPlanet().getName(), language));
				}
			} else if (skill.getSkillDefinition().getId().equals(SkillDefinition.FACTORION_LORE_ID)) {
				if (getFaction() != null) {
					skill.setSpecialization(
							new Specialization(getFaction().getName().toLowerCase(), getFaction().getName(), language));
				}
			}
			naturalSkills.add(skill);
		}
		return naturalSkills;
	}

	public List<AvailableSkill> getLearnedSkills() throws InvalidXmlElementException {
		final List<AvailableSkill> learnedSkills = new ArrayList<>();
		for (final AvailableSkill skill : AvailableSkillsFactory.getInstance().getLearnedSkills(language)) {
			if (getSkillTotalRanks(skill) != null) {
				learnedSkills.add(skill);
			}
		}
		return learnedSkills;
	}

	@Override
	public String toString() {
		final String name = getNameRepresentation();
		if (name.length() > 0) {
			return name;
		}
		return super.toString();
	}

	public String getRepresentation() {
		final CharacterSheet characterSheet = new CharacterSheet(this);
		return characterSheet.toString();
	}

	public int getStrengthDamangeModification() {
		try {
			final int strength = characteristics.get(CharacteristicName.STRENGTH.getId()).getValue();

			if (strength > 5) {
				return strength / 3 - 1;
			}
		} catch (NullPointerException npe) {

		}
		return 0;
	}

	/**
	 * Total points spent in characteristics.
	 * 
	 * @return the number of points spent in characteristics
	 */
	public int getCharacteristicsTotalPoints() {
		int characteristicPoints = 0;
		for (final CharacteristicName characteristicName : CharacteristicName.getBasicCharacteristics()) {
			characteristicPoints += getRawValue(characteristicName) - getStartingValue(characteristicName);
		}
		return characteristicPoints;
	}

	/**
	 * Total points spent in skills.
	 * 
	 * @return the number of points spent in skills
	 * @throws InvalidXmlElementException
	 */
	public int getSkillsTotalPoints() throws InvalidXmlElementException {
		int skillPoints = 0;
		for (final AvailableSkill skill : AvailableSkillsFactory.getInstance().getNaturalSkills(getLanguage())) {
			skillPoints += getSkillAssignedRanks(skill) - getStartingValue(skill);
		}

		for (final AvailableSkill skill : AvailableSkillsFactory.getInstance().getLearnedSkills((getLanguage()))) {
			if (isSkillSpecial(skill)) {
				continue;
			}
			if (getSkillAssignedRanks(skill) != null) {
				skillPoints += getSkillAssignedRanks(skill);
			}
		}

		CostCalculatorLog.debug(this.getClass().getName(), skills.toString());
		return skillPoints;
	}

	public int getRemainginExperience() {
		return experience;
	}

	public Characteristic getCharacteristic(String characteristicId) {
		return characteristics.get(characteristicId);
	}

	public Characteristic getCharacteristic(CharacteristicName characteristicName) {
		return getCharacteristic(characteristicName.getId());
	}

	public Set<Characteristic> getCharacteristics() {
		return new HashSet<Characteristic>(characteristics.values());
	}

	public Set<Characteristic> getCharacteristics(CharacteristicType characteristicType) {
		if (characteristicsByType == null || characteristicsByType.isEmpty()) {
			characteristicsByType = new HashMap<>();
			for (final Characteristic characteristic : characteristics.values()) {
				if (characteristicsByType.get(characteristic.getType()) == null) {
					characteristicsByType.put(characteristic.getType(), new HashSet<Characteristic>());
				}
				characteristicsByType.get(characteristic.getType()).add(characteristic);
			}
		}
		return characteristicsByType.get(characteristicType);
	}

	public boolean isSkillTrained(AvailableSkill skill) {
		final int skillRanks = getSkillTotalRanks(skill);
		try {
			final boolean isNatural = getNaturalSkills().contains(skill);
			return ((skillRanks > FreeStyleCharacterCreation.getMinInitialNaturalSkillsValues(getInfo().getAge())
					&& isNatural)
					// check ranks and if is natural.
					|| (skillRanks > 0 && isNatural));
		} catch (InvalidXmlElementException e) {
			MachineLog.errorMessage(this.getClass().getName(), e);
		}
		return false;
	}

	public boolean isCharacteristicTrained(Characteristic characteristic) {
		return characteristic.getValue() > getStartingValue(characteristic.getCharacteristicName());
	}

	/**
	 * Return which characteristic type has higher values in its characteristics.
	 * 
	 * @return a list of characteristicTypes
	 */
	public List<Entry<CharacteristicType, Integer>> getPreferredCharacteristicsTypeSorted() {
		final Map<CharacteristicType, Integer> totalRanksByCharacteristicType = new TreeMap<>();
		for (final CharacteristicType characteristicType : CharacteristicType.values()) {
			if (characteristicType != CharacteristicType.OTHERS && getCharacteristics(characteristicType) != null) {
				for (final Characteristic characteristic : getCharacteristics(characteristicType)) {
					if (totalRanksByCharacteristicType.get(characteristicType) == null) {
						totalRanksByCharacteristicType.put(characteristicType, 0);
					}
					totalRanksByCharacteristicType.put(characteristicType,
							totalRanksByCharacteristicType.get(characteristicType)
									+ getValue(characteristic.getCharacteristicName()));
				}
			}
		}
		if (totalRanksByCharacteristicType.isEmpty()) {
			return null;
		}
		// Sort result.
		final List<Entry<CharacteristicType, Integer>> sortedList = new LinkedList<>(
				totalRanksByCharacteristicType.entrySet());
		Collections.sort(sortedList, new Comparator<Entry<CharacteristicType, Integer>>() {
			public int compare(Entry<CharacteristicType, Integer> o1, Entry<CharacteristicType, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		return sortedList;
	}

	public BeneficeSpecialization getStatus() throws InvalidXmlElementException {
		for (final AvailableBenefice benefice : getAllBenefices()) {
			if (benefice.getBeneficeDefinition().getGroup() == BeneficeGroup.STATUS) {
				// Must have an specialization.
				if (benefice.getSpecialization() != null) {
					return benefice.getSpecialization();
				}
			}
		}
		return null;
	}

	/**
	 * Gets the starting value of money in firebirds. The value cames from "cash"
	 * benefices.
	 * 
	 * @return an integer represented the starting ammount of firebirds.
	 */
	public int getInitialMoney() {
		try {
			for (final AvailableBenefice benefice : getAllBenefices()) {
				if ((benefice.getId().startsWith("cash"))) {
					// Must have an specialization.
					if (benefice.getSpecialization() != null) {
						return Integer.parseInt(benefice.getId().replaceAll("[^\\d.]", ""));
					}
				}
			}
			return Integer.parseInt(AvailableBeneficeFactory.getInstance()
					.getElement("cash [firebirds250]", getLanguage()).getId().replaceAll("[^\\d.]", ""));
		} catch (InvalidXmlElementException e) {
			return 0;
		}
	}

	/**
	 * Returns the total cost of money spent in equipment.
	 * 
	 * @return the summatory of the costs of the equipment.
	 */
	public int getSpentMoney() {
		int total = 0;
		for (final Weapon weapon : weapons.getElements()) {
			// Skip weapons payed by benefices.
			try {
				if (!getAllBenefices()
						.contains(AvailableBeneficeFactory.getInstance().getElement(weapon.getId(), getLanguage()))) {
					total += weapon.getCost();
				}
			} catch (InvalidXmlElementException ibe) {
				total += weapon.getCost();
			}
		}
		if (shield != null) {
			total += shield.getCost();
		}
		if (armour != null) {
			total += armour.getCost();
		}

		return total;
	}

	/**
	 * Gets the actual remaining money of the character.
	 * 
	 * @return the difference between the starting money and the spent one.
	 */
	public int getMoney() {
		return getInitialMoney() - getSpentMoney();
	}

	/**
	 * Gets the current rank of the status of a character.
	 * 
	 * @return the status of the character.
	 * @throws InvalidXmlElementException
	 */
	public String getRank() throws InvalidXmlElementException {
		final BeneficeSpecialization status = getStatus();
		if (status == null) {
			return null;
		}
		// Some factions have different names.
		if (getFaction() != null && getFaction().getRankTranslation(status.getId()) != null) {
			return getFaction().getRankTranslation(status.getId()).getName();
		} else {
			return status.getName();
		}
	}

	public Faction getFaction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public int getBlessingModificationSituation(IValue value) {
		int modification = 0;
		for (final Blessing blessing : getAllBlessings()) {
			modification += getModification(blessing, value, false);
		}
		return modification;
	}

	public int getCyberneticsModificationSituation(IValue value) {
		int modification = 0;
		for (final ICyberneticDevice cyberneticDevice : getCybernetics()) {
			modification += getModification(cyberneticDevice, value, false);
		}
		return modification;
	}

	public int getBlessingModificationAlways(IValue value) {
		int modification = 0;
		for (final Blessing blessing : getAllBlessings()) {
			modification += getModification(blessing, value, true);
		}
		return modification;
	}

	public int getCyberneticsModificationAlways(IValue value) {
		int modification = 0;
		for (final ICyberneticDevice cyberneticDevice : getCyberneticList().getElements()) {
			modification += getModification(cyberneticDevice, value, true);
		}
		return modification;
	}

	private int getModification(IElementWithBonification element, IValue value, boolean always) {
		int bonus = 0;
		for (final Bonification bonification : element.getBonifications()) {
			if (bonification.getSituation() == null || (always && bonification.getSituation().isEmpty())
					|| (!always && !bonification.getSituation().isEmpty())) {
				if (bonification.getAffects() instanceof SpecialValue) {
					final SpecialValue specialValue = (SpecialValue) bonification.getAffects();
					// Has a list of values defined.
					for (final IValue specialValueSkill : specialValue.getAffects()) {
						if (Objects.equals(specialValueSkill, value)) {
							bonus += bonification.getBonification();
							break;
						}
					}
				}
				if (value instanceof Characteristic) {
					if (Objects.equals(bonification.getAffects(),
							((Characteristic) value).getCharacteristicDefinition())) {
						bonus += bonification.getBonification();
					}
				}
				if (Objects.equals(bonification.getAffects(), value)) {
					bonus += bonification.getBonification();
				}
			}
		}
		return bonus;
	}

	public boolean hasSkillTemporalModificator(AvailableSkill availableSkill) {
		if (getBlessingModificationSituation(availableSkill.getSkillDefinition()) != 0) {
			return true;
		}
		if (getCyberneticsModificationSituation(availableSkill.getSkillDefinition()) != 0) {
			return true;
		}
		return false;
	}

	public boolean hasSkillModificator(AvailableSkill availableSkill) {
		if (getBlessingModificationAlways(availableSkill.getSkillDefinition()) != 0) {
			return true;
		}
		if (getCyberneticsModificationAlways(availableSkill.getSkillDefinition()) != 0) {
			return true;
		}
		return false;
	}

	public boolean hasCharacteristicTemporalModificator(CharacteristicName characteristicName) {
		if (getBlessingModificationSituation(
				CharacteristicsDefinitionFactory.getInstance().get(characteristicName, getLanguage())) != 0) {
			return true;
		}
		if (getCyberneticsModificationSituation(
				CharacteristicsDefinitionFactory.getInstance().get(characteristicName, getLanguage())) != 0) {
			return true;
		}
		return false;
	}

	public boolean hasCharacteristicModificator(CharacteristicName characteristicName) {
		if (getBlessingModificationAlways(
				CharacteristicsDefinitionFactory.getInstance().get(characteristicName, getLanguage())) != 0) {
			return true;
		}
		if (getCyberneticsModificationAlways(
				CharacteristicsDefinitionFactory.getInstance().get(characteristicName, getLanguage())) != 0) {
			return true;
		}
		return false;
	}

	public int getExtraWyrd() {
		return getOccultism().getExtraWyrd();
	}

	public void setExtraWyrd(int extraWyrd) {
		getOccultism().setExtraWyrd(extraWyrd);
	}

	public int getPsiqueLevel(OccultismType occultismType) {
		return getOccultism().getPsiqueLevel(occultismType);
	}

	public void setPsiqueLevel(OccultismType occultismType, int psyValue) throws InvalidPsiqueLevelException {
		getOccultism().setPsiqueLevel(occultismType, psyValue, getLanguage(), getFaction());
	}

	public int getDarkSideLevel(OccultismType occultismType) {
		return getOccultism().getDarkSideLevel(occultismType);
	}

	public void setDarkSideLevel(OccultismType occultismType, int darkSideValue) {
		getOccultism().setDarkSideLevel(occultismType, darkSideValue);
	}

	public Map<String, List<String>> getSelectedPowers() {
		return getOccultism().getSelectedPowers();
	}

	public int getTotalSelectedPowers() {
		return getOccultism().getTotalSelectedPowers();
	}

	public void addOccultismPower(OccultismPower power) throws InvalidOccultismPowerException {
		getOccultism().addPower(power, getLanguage(), getFaction());
	}

	public String getNameRepresentation() {
		final StringBuilder stringBuilder = new StringBuilder("");
		if (getInfo() != null && getInfo().getNames() != null && !getInfo().getNames().isEmpty()) {
			for (final Name name : getInfo().getNames()) {
				stringBuilder.append(name.getName());
				stringBuilder.append(" ");
			}
		}
		if (getInfo() != null && getInfo().getSurname() != null) {
			stringBuilder.append(getInfo().getSurname().getName());
		}
		return stringBuilder.toString().trim();
	}

	/**
	 * Gets the total ranks spent in a group of characteristics.
	 * 
	 * @param skillGroup group to check.
	 * @return the number of ranks
	 * @throws InvalidXmlElementException if malformed file in translations.
	 */
	public int getRanksAssigned(SkillGroup skillGroup) throws InvalidXmlElementException {
		int ranks = 0;
		for (final AvailableSkill skill : AvailableSkillsFactory.getInstance().getSkillsByGroup(skillGroup,
				getLanguage())) {
			ranks += getSkillAssignedRanks(skill);
			if (skill.getSkillDefinition().isNatural()) {
				ranks -= FreeStyleCharacterCreation.getMinInitialNaturalSkillsValues(getInfo().getAge());
			}
		}
		return ranks;
	}

	/**
	 * Check if exists a weapon that needs that skill.
	 * 
	 * @param skill skill to check.
	 * @return a weapon that needs this skill
	 */
	public Weapon hasWeaponWithSkill(AvailableSkill skill) {
		for (final Weapon weapon : getAllWeapons()) {
			if (Objects.equals(weapon.getSkill(), skill)) {
				return weapon;
			}
		}
		return null;
	}

	/**
	 * Gets the weapon with higher ranks.
	 * 
	 * @return a weapon
	 */
	public Weapon getMainWeapon() {
		Weapon mainWeapon = null;
		int totalValue = 0;
		for (final Weapon weapon : getAllWeapons()) {
			if (getSkillTotalRanks(weapon.getSkill())
					+ getCharacteristic(weapon.getCharacteristic().getCharacteristicName()).getValue() > totalValue) {
				totalValue = getSkillTotalRanks(weapon.getSkill())
						+ getCharacteristic(weapon.getCharacteristic().getCharacteristicName()).getValue();
				mainWeapon = weapon;
			}
		}
		return mainWeapon;
	}

	public int getEquipmentMaxTechnologicalLevel() {
		int techLevel = 1;
		for (final Weapon weapon : getAllWeapons()) {
			if (weapon.getTechLevel() > techLevel) {
				techLevel = weapon.getTechLevel();
			}
		}
		if (getArmour() != null && getArmour().getTechLevel() > techLevel) {
			techLevel = getArmour().getTechLevel();
		}
		if (getShield() != null && getShield().getTechLevel() > techLevel) {
			techLevel = getShield().getTechLevel();
		}
		return techLevel;
	}
}