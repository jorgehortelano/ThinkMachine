package com.softwaremagico.tm.character;

import java.util.HashMap;
import java.util.Map;

import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.characteristics.Characteristics;
import com.softwaremagico.tm.character.skills.AvailableSkill;
import com.softwaremagico.tm.character.skills.SelectedSkill;
import com.softwaremagico.tm.character.skills.SkillFactory;

public class CharacterPlayer {

	// Basic description of the character.
	private CharacterInfo info;

	// Characteristics.
	private Characteristics characteristics;

	// All Psi/Teurgy powers
	private Occultism occultism;

	// Skills
	private Map<String, SelectedSkill> skills;

	public CharacterPlayer() {
		reset();
	}

	private void reset() {
		info = new CharacterInfo();
		characteristics = new Characteristics();
		occultism = new Occultism();
		skills = new HashMap<>();
	}

	public CharacterInfo getInfo() {
		return info;
	}

	public void setInfo(CharacterInfo info) {
		this.info = info;
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}

	/**
	 * Gets the starting value for a characteristic depending on the race.
	 * 
	 * @param characteristicName
	 * @return
	 */
	public Integer getStartingValue(CharacteristicName characteristicName) {
		if (CharacteristicName.DEFENSE.equals(characteristicName)) {
			return 1;
		}
		if (CharacteristicName.MOVEMENT.equals(characteristicName)) {
			return 5;
		}
		if (CharacteristicName.INITIATIVE.equals(characteristicName)) {
			return getStartingValue(CharacteristicName.DEXTERITY) + getStartingValue(CharacteristicName.WITS);
		}
		return 3;
	}

	public Integer getValue(CharacteristicName characteristicName) {
		if (CharacteristicName.INITIATIVE.equals(characteristicName)) {
			return getValue(CharacteristicName.DEXTERITY) + getValue(CharacteristicName.WITS);
		}
		if (CharacteristicName.DEFENSE.equals(characteristicName)) {
			return getStartingValue(characteristicName);
		}
		if (CharacteristicName.MOVEMENT.equals(characteristicName)) {
			return getStartingValue(characteristicName);
		}
		return getCharacteristics().getCharacteristic(characteristicName).getValue();
	}

	public Integer getVitalityValue() {
		return getValue(CharacteristicName.ENDURANCE) + 5;
	}

	public Integer getWyrdValue() {
		return Math.max(getValue(CharacteristicName.WILL), getValue(CharacteristicName.FAITH)) + occultism.getExtraWyrd();
	}

	public void addSkill(String skillName, int value) {
		skills.put(skillName, new SelectedSkill(skillName, value));
	}

	public Integer getSkillValue(String skillName) {
		if (skills.get(skillName) == null) {
			return null;
		}
		return skills.get(skillName).getValue();
	}
}