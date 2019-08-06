package com.softwaremagico.tm.rules.character.blessings;

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

import java.util.HashSet;
import java.util.Set;

import com.softwaremagico.tm.rules.InvalidXmlElementException;
import com.softwaremagico.tm.rules.XmlFactory;
import com.softwaremagico.tm.rules.character.values.Bonification;
import com.softwaremagico.tm.rules.character.values.IValue;
import com.softwaremagico.tm.rules.character.values.SpecialValue;
import com.softwaremagico.tm.rules.language.ITranslator;
import com.softwaremagico.tm.rules.language.LanguagePool;
import com.softwaremagico.tm.rules.log.SuppressFBWarnings;

public class BlessingFactory extends XmlFactory<Blessing> {
	private static final ITranslator translatorBlessing = LanguagePool.getTranslator("blessings.xml");

	private static final String NAME = "name";
	private static final String COST = "cost";
	private static final String BONIFICATION = "bonification";
	private static final String VALUE = "value";
	private static final String AFFECTS = "affects";
	private static final String SITUATION = "situation";
	private static final String CURSE = "curse";
	private static final String GROUP = "group";

	private static class BlessingFactoryInit {
		public static final BlessingFactory INSTANCE = new BlessingFactory();
	}

	public static BlessingFactory getInstance() {
		return BlessingFactoryInit.INSTANCE;
	}

	@Override
	@SuppressFBWarnings("REC_CATCH_EXCEPTION")
	protected Blessing createElement(ITranslator translator, String blessingId, String language)
			throws InvalidXmlElementException {

		try {
			final String name = translator.getNodeValue(blessingId, NAME, language);

			final String cost = translator.getNodeValue(blessingId, COST);

			BlessingGroup blessingGroup = null;
			final String groupName = translator.getNodeValue(blessingId, GROUP);
			if (groupName != null) {
				blessingGroup = BlessingGroup.get(groupName);
			}

			final Set<Bonification> bonifications = new HashSet<>();
			int node = 0;
			while (true) {
				try {
					final String bonificationValue = translator.getNodeValue(blessingId, BONIFICATION, VALUE, node);
					final String valueName = translator.getNodeValue(blessingId, BONIFICATION, AFFECTS, node);
					IValue affects = null;
					if (valueName != null) {
						affects = SpecialValue.getValue(valueName, language);
					}
					final String situation = translator.getNodeValue(blessingId, SITUATION, language, node);

					final Bonification bonification = new Bonification(Integer.parseInt(bonificationValue), affects,
							situation);
					bonifications.add(bonification);
					node++;
				} catch (Exception e) {
					break;
				}
			}

			final String curseTag = translator.getNodeValue(blessingId, CURSE);
			BlessingClassification blessingClassification = BlessingClassification.BLESSING;

			if (curseTag != null) {
				if (Boolean.parseBoolean(curseTag)) {
					blessingClassification = BlessingClassification.CURSE;
				}
			}

			final Blessing blessing = new Blessing(blessingId, name, language, Integer.parseInt(cost), bonifications,
					blessingClassification, blessingGroup);
			return blessing;
		} catch (Exception e) {
			throw new InvalidBlessingException("Invalid structure in blessing '" + blessingId + "'.", e);
		}
	}

	@Override
	protected ITranslator getTranslator() {
		return translatorBlessing;
	}
}