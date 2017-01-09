package com.softwaremagico.tm.pdf.perks;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.softwaremagico.tm.pdf.FadingSunsTheme;
import com.softwaremagico.tm.pdf.elements.BaseElement;
import com.softwaremagico.tm.pdf.elements.VerticalHeaderPdfPTable;

public class VictoryPointsTable extends VerticalHeaderPdfPTable {
	private final static float[] WIDTHS = { 3f, 4f, 4f };

	public VictoryPointsTable() {
		super(WIDTHS);
		addCell(createVerticalTitle("Tabla de Victoria", 12));

		addCell(createSubTitle("Dado", BaseColor.WHITE));
		addCell(createSubTitle("PV", BaseColor.WHITE));

		addCell(createLine("1", BaseColor.LIGHT_GRAY));
		addCell(createLine("0", BaseColor.LIGHT_GRAY));

		addCell(createLine("2-3", BaseColor.WHITE));
		addCell(createLine("1", BaseColor.WHITE));

		addCell(createLine("4-5", BaseColor.LIGHT_GRAY));
		addCell(createLine("2", BaseColor.LIGHT_GRAY));

		addCell(createLine("6-7", BaseColor.WHITE));
		addCell(createLine("3", BaseColor.WHITE));

		addCell(createLine("8-9", BaseColor.LIGHT_GRAY));
		addCell(createLine("4", BaseColor.LIGHT_GRAY));

		addCell(createLine("10-11", BaseColor.WHITE));
		addCell(createLine("5", BaseColor.WHITE));

		addCell(createLine("12-13", BaseColor.LIGHT_GRAY));
		addCell(createLine("6", BaseColor.LIGHT_GRAY));

		addCell(createLine("14-15", BaseColor.WHITE));
		addCell(createLine("7", BaseColor.WHITE));

		addCell(createLine("16-17", BaseColor.LIGHT_GRAY));
		addCell(createLine("8", BaseColor.LIGHT_GRAY));

		addCell(createLine("18-19", BaseColor.WHITE));
		addCell(createLine("9", BaseColor.WHITE));

		addCell(createLine("20", BaseColor.LIGHT_GRAY));
		addCell(createLine("*", BaseColor.LIGHT_GRAY));

	}

	private static PdfPCell createLine(String text, BaseColor color) {
		PdfPCell cell = BaseElement.getCell(text, 0, 1, Element.ALIGN_CENTER, color, FadingSunsTheme.getLineFont(), FadingSunsTheme.VICTORY_POINTS_FONT_SIZE);
		cell.setMinimumHeight(11);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}

	private static PdfPCell createSubTitle(String text, BaseColor color) {
		PdfPCell cell = BaseElement.getCell(text, 0, 1, Element.ALIGN_CENTER, color, FadingSunsTheme.getLineFontBold(),
				FadingSunsTheme.VICTORY_POINTS_FONT_SIZE);
		cell.setMinimumHeight(13);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}

	@Override
	protected int getTitleFontSize() {
		return FadingSunsTheme.VICTORY_POINTS_TITLE_FONT_SIZE;
	}

}
