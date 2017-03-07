package com.softwaremagico.tm.pdf.characteristics;

/*-
 * #%L
 * The Thinking Machine (Core)
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.characteristics.CharacteristicType;
import com.softwaremagico.tm.pdf.FadingSunsTheme;
import com.softwaremagico.tm.pdf.elements.BaseElement;
import com.softwaremagico.tm.pdf.elements.CustomPdfTable;
import com.softwaremagico.tm.pdf.skills.MainSkillsTableFactory;

public class CharacteristicSmallColumn extends CustomPdfTable {
	private final static String GAP = "   ";
	private final static String SKILL_VALUE_GAP = "___";

	private final static int ROWS = 3;
	private final static int ROW_HEIGHT = 36;
	private final static float[] widths = { 1 };

	public CharacteristicSmallColumn(CharacterPlayer characterPlayer, CharacteristicType characteristicType, CharacteristicName[] content) {
		super(widths);
		PdfPCell title = createTitle(getTranslator().getTranslatedText(characteristicType.getTranslationTag()),
				FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_TITLE_FONT_SIZE);
		title.setColspan(widths.length);
		addCell(title);
		addCell(createContent(characterPlayer, content));
	}

	private PdfPCell createContent(CharacterPlayer characterPlayer, CharacteristicName[] content) {
		float[] widths = { 4f, 1f };
		PdfPTable table = new PdfPTable(widths);
		BaseElement.setTablePropierties(table);
		table.getDefaultCell().setBorder(0);

		for (CharacteristicName characteristicName : content) {
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Paragraph(getTranslator().getTranslatedText(characteristicName.getTranslationTag()), new Font(FadingSunsTheme.getLineFont(),
					FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE)));
			paragraph.add(new Paragraph(" (", new Font(FadingSunsTheme.getLineFont(), FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE)));
			if (characterPlayer == null) {
				paragraph.add(new Paragraph(GAP, new Font(FadingSunsTheme.getLineFont(), FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE)));
			} else {
				paragraph.add(new Paragraph(characterPlayer.getStartingValue(characteristicName) + "", new Font(FadingSunsTheme.getHandwrittingFont(),
						FadingSunsTheme.getHandWrittingFontSize(FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE))));
			}
			paragraph.add(new Paragraph(")", new Font(FadingSunsTheme.getLineFont(), FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE)));

			PdfPCell characteristicTitle = new PdfPCell(paragraph);
			characteristicTitle.setBorder(0);
			characteristicTitle.setMinimumHeight(ROW_HEIGHT / content.length);
			table.addCell(characteristicTitle);

			// Rectangle
			if (characterPlayer == null) {
				table.addCell(createSkillLine(SKILL_VALUE_GAP));
			} else {
				table.addCell(getHandwrittingCell(characterPlayer.getValue(characteristicName) + "", Element.ALIGN_LEFT));
			}
		}

		PdfPCell cell = new PdfPCell();
		cell.addElement(table);
		BaseElement.setCellProperties(cell);

		return cell;
	}

	private static PdfPCell getHandwrittingCell(String text, int align) {
		PdfPCell cell = BaseElement.getCell(text, 0, 0, align, BaseColor.WHITE, FadingSunsTheme.getHandwrittingFont(),
				FadingSunsTheme.getHandWrittingFontSize(FadingSunsTheme.CHARACTER_SMALL_CHARACTERISTICS_LINE_FONT_SIZE));
		return cell;
	}

	private static PdfPCell createSkillLine(String text) {
		PdfPCell cell = BaseElement.getCell(text, 0, 1, Element.ALIGN_LEFT, BaseColor.WHITE, FadingSunsTheme.getLineFont(),
				FadingSunsTheme.SKILLS_LINE_FONT_SIZE);
		cell.setMinimumHeight((MainSkillsTableFactory.HEIGHT / ROWS));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}

}