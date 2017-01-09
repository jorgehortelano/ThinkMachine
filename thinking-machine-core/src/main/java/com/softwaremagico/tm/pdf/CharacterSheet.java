package com.softwaremagico.tm.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.softwaremagico.tm.pdf.characteristics.CharacteristicsTable;
import com.softwaremagico.tm.pdf.info.CharacterBasicsTable;
import com.softwaremagico.tm.pdf.others.MainOthersTable;
import com.softwaremagico.tm.pdf.perks.MainPerksTable;
import com.softwaremagico.tm.pdf.skills.MainSkillsTableFactory;

public class CharacterSheet extends PdfDocument {

	public CharacterSheet() {
		super();
	}

	@Override
	protected Rectangle getPageSize() {
		return PageSize.A4;
	}

	@Override
	protected void createPagePDF(Document document) throws Exception {
		// addBackGroundImage(document, Path.returnBackgroundPath(), writer);
		PdfPTable mainTable = CharacterBasicsTable.getCharacterBasicsTable();
		document.add(mainTable);
		PdfPTable characteristicsTable = CharacteristicsTable.getCharacterBasicsTable();
		document.add(characteristicsTable);
		PdfPTable skillsTable = MainSkillsTableFactory.getSkillsTable();
		document.add(skillsTable);
		PdfPTable perksTable = MainPerksTable.getPerksTable();
		document.add(perksTable);
//		PdfPTable othersTable = MainOthersTable.getOthersTable();
//		document.add(othersTable);
//		PdfPTable perksTable = MainPerksTable.getPerksTable();
//		document.add(perksTable);

		document.newPage();
	}

	public float[] getTableWidths() {
		float[] widths = { 0.60f, 0.30f };
		return widths;
	}

	public void setTablePropierties(PdfPTable mainTable) {
		mainTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		mainTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
