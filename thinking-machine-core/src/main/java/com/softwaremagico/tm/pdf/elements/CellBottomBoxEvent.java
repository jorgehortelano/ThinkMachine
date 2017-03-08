package com.softwaremagico.tm.pdf.elements;

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

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class CellBottomBoxEvent implements PdfPCellEvent {
	private int border = 1;
	private int margin = 3;

	public CellBottomBoxEvent() {

	}

	public CellBottomBoxEvent(int border) {
		this.border = border;
	}

	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
		PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
		canvas.setLineWidth(border);
		canvas.moveTo(position.getLeft() + margin, position.getBottom() + margin);
		canvas.lineTo(position.getRight() - margin, position.getBottom() + margin);
		canvas.moveTo(position.getRight() - margin, position.getBottom() + margin);
		canvas.lineTo(position.getRight() - margin, position.getTop());
		canvas.moveTo(position.getLeft() + margin, position.getBottom() + margin);
		canvas.lineTo(position.getLeft() + margin, position.getTop());
		canvas.stroke();
	}
}
