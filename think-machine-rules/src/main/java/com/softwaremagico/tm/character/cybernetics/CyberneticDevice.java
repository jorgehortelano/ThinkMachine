package com.softwaremagico.tm.character.cybernetics;

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

import java.util.Set;

import com.softwaremagico.tm.Element;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.IElementWithBonification;
import com.softwaremagico.tm.character.equipment.weapons.Weapon;
import com.softwaremagico.tm.character.values.Bonification;
import com.softwaremagico.tm.character.values.StaticValue;
import com.softwaremagico.tm.log.MachineLog;

public class CyberneticDevice extends Element<CyberneticDevice> implements IElementWithBonification {
	private final int points;
	private final int incompatibility;
	private final int cost;
	private final int techLevel;
	private final CyberneticDeviceTrait usability;
	private final CyberneticDeviceTrait quality;
	private final CyberneticDeviceTrait visibility;
	private final CyberneticDeviceTrait material;
	private final CyberneticDeviceTrait attached;
	private final CyberneticDeviceTrait power;
	private final String others;
	private final String requirement;
	private final Weapon weapon;
	private final boolean proscribed;
	private final Set<Bonification> bonifications;
	private final Set<StaticValue> staticValues;

	public CyberneticDevice(String id, String name, String language, int points, int incompatibility, int cost, int techLevel, CyberneticDeviceTrait usability,
			CyberneticDeviceTrait quality, CyberneticDeviceTrait visibility, CyberneticDeviceTrait material, CyberneticDeviceTrait attached,
			CyberneticDeviceTrait power, boolean proscribed, String others, String requirement, Weapon weapon, Set<Bonification> bonifications,
			Set<StaticValue> staticValues) {
		super(id, name, language);
		this.points = points;
		this.incompatibility = incompatibility;
		this.cost = cost;
		this.techLevel = techLevel;
		this.usability = usability;
		this.quality = quality;
		this.visibility = visibility;
		this.material = material;
		this.attached = attached;
		this.power = power;
		this.others = others;
		this.requirement = requirement;
		this.proscribed = proscribed;
		this.weapon = weapon;
		this.bonifications = bonifications;
		this.staticValues = staticValues;
	}

	public int getPoints() {
		return points;
	}

	public int getIncompatibility() {
		return incompatibility;
	}

	public CyberneticDeviceTrait getUsability() {
		return usability;
	}

	public CyberneticDeviceTrait getQuality() {
		return quality;
	}

	public CyberneticDeviceTrait getVisibility() {
		return visibility;
	}

	public String getOthers() {
		return others;
	}

	public CyberneticDevice getRequirement() {
		if (requirement != null) {
			try {
				return CyberneticDeviceFactory.getInstance().getElement(requirement, getLanguage());
			} catch (InvalidXmlElementException e) {
				MachineLog.errorMessage(this.getClass().getName(), e);
			}
		}
		return null;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public CyberneticDeviceTrait getMaterial() {
		return material;
	}

	public CyberneticDeviceTrait getAttached() {
		return attached;
	}

	public CyberneticDeviceTrait getPower() {
		return power;
	}

	public boolean isProscribed() {
		return proscribed;
	}

	public int getCost() {
		return cost;
	}

	public int getTechLevel() {
		return techLevel;
	}

	@Override
	public Set<Bonification> getBonifications() {
		return bonifications;
	}

	public Set<StaticValue> getStaticValues() {
		return staticValues;
	}
}
