package in.manoj.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import in.manoj.entity.Student;
import jakarta.servlet.http.HttpServletResponse;

public class StudentPdfExporter {

	public List<Student> students;
	
	public StudentPdfExporter(List<Student>students) {
		this.students=students;
	}
	
	public void export(HttpServletResponse response)throws IOException {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);
	
		Paragraph title = new Paragraph("Student Results",fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		document.add(new Paragraph(" "));
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {3.0f,3.0f,2.0f,2.0f,2.0f,2.0f,2.0f});
		table.setSpacingBefore(0);
		
		
		writeTableHeader(table);
		writeTableData(table);
		
		
		document.add(table);
		document.close();
	}

	private void writeTableHeader(PdfPTable table) {
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(7);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.white);
		
		String headers[]= {"Name","Roll Number","Math","Science","English","Total","Grade"};
		for(String h:headers) {
			cell.setPhrase(new Phrase(h,font));
			table.addCell(cell);
		}
	}

	private void writeTableData(PdfPTable table) {
		
		for(Student s:students) {
			table.addCell(s.getName());
			table.addCell(s.getRollNumber());
			table.addCell(String.valueOf(s.getMathMarks()));
			table.addCell(String.valueOf(s.getScienceMarks()));
			table.addCell(String.valueOf(s.getEnglishMarks()));
			table.addCell(String.valueOf(s.getTotalMarks()));
			table.addCell(s.getGrade());
		}
	}
}
