package com.softwaremagico.tm.factory;

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

import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.tm.CacheHandler;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.cybernetics.CyberneticDeviceFactory;
import com.softwaremagico.tm.character.cybernetics.CyberneticType;

@Test(groups = { "cyberneticFactory" })
public class CyberneticDeviceFactoryTests {

	private final static int DEFINED_DEVICES = 18;
	private final static int SECOND_BRAIN_SOFTWARE = 3;
	private final static String LANGUAGE = "es";

	@BeforeClass
	public void clearCache() {
		CacheHandler.clearCache();
	}

	@Test
	public void readDevices() throws InvalidXmlElementException {
		Assert.assertEquals(CyberneticDeviceFactory.getInstance().getElements(LANGUAGE).size(), DEFINED_DEVICES);
	}

	@Test
	public void getRequirements() throws InvalidXmlElementException {
		Assert.assertEquals(
				CyberneticDeviceFactory.getInstance()
						.getDevicesThatRequires(CyberneticDeviceFactory.getInstance().getElement("secondBrain", LANGUAGE), LANGUAGE).size(),
				SECOND_BRAIN_SOFTWARE);
	}

	@Test
	public void getTypes() throws InvalidXmlElementException {
		Assert.assertEquals(CyberneticType.ENHANCEMENT, CyberneticDeviceFactory.getInstance().getElement("secondBrain", LANGUAGE).getType());
		Assert.assertEquals(CyberneticType.COMBAT, CyberneticDeviceFactory.getInstance().getElement("viperSword", LANGUAGE).getType());
		Assert.assertEquals(CyberneticType.OTHERS, CyberneticDeviceFactory.getInstance().getElement("stimusim", LANGUAGE).getType());
	}
}
