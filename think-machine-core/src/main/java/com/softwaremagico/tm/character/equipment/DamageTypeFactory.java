package com.softwaremagico.tm.character.equipment;

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

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.XmlFactory;
import com.softwaremagico.tm.character.equipment.weapons.InvalidWeaponException;
import com.softwaremagico.tm.language.ITranslator;
import com.softwaremagico.tm.language.LanguagePool;

public class DamageTypeFactory extends XmlFactory<DamageType> {
	private final static ITranslator translatorDamage = LanguagePool.getTranslator("damage.xml");

	private final static String NAME = "name";

	private static DamageTypeFactory instance;

	private static void createInstance() {
		if (instance == null) {
			synchronized (DamageTypeFactory.class) {
				if (instance == null) {
					instance = new DamageTypeFactory();
				}
			}
		}
	}

	public static DamageTypeFactory getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	@Override
	protected ITranslator getTranslator() {
		return translatorDamage;
	}

	@Override
	protected DamageType createElement(ITranslator translator, String damageId, String language) throws InvalidXmlElementException {
		DamageType damageType = null;
		try {
			String name = translator.getNodeValue(damageId, NAME, language);
			damageType = new DamageType(damageId, name, language);
		} catch (Exception e) {
			throw new InvalidWeaponException("Invalid name in damage '" + damageId + "'.", e);
		}
		return damageType;
	}

}
