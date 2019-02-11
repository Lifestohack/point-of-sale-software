package manager.exportPDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import manager.data.saleprotocal.SoldArticle;
import manager.data.saleprotocal.SoldProtocal;

public class ExportPDF {

	public static void createPDF(File file, SoldProtocal soldProtocal) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		document.open();
		Image image = addImage();

		PdfPTable table = createTable(soldProtocal);

		try {

			document.add(image);
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();
	}

	private static Image addImage() {
		Image image = null;
		String path = System.getProperty("user.dir");
		String logo = "icon.png";
		try {
			image = Image.getInstance(new File(path, logo).getPath());
			image.scaleToFit(200, 100);
		} catch (BadElementException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return image;
	}

	public static PdfPTable createTable(SoldProtocal sp) {
		PdfPTable table = null;
		try {

			table = new PdfPTable(5);
			table.setWidths(new int[] { 1, 2, 1, 1, 1 });
			table.addCell(createCell("Artikel Nummer", 2, 1, Element.ALIGN_LEFT));
			table.addCell(createCell("Artikel Name", 2, 1, Element.ALIGN_LEFT));
			table.addCell(createCell("Menge", 2, 1, Element.ALIGN_LEFT));
			table.addCell(createCell("%", 2, 1, Element.ALIGN_LEFT));
			table.addCell(createCell("Preis", 2, 1, Element.ALIGN_LEFT));
			String[][] data = ListOfSoldArticletoArray(sp.getSoldArticles());
			for (String[] row : data) {
				table.addCell(createCell(row[0], 1, 1, Element.ALIGN_LEFT));
				table.addCell(createCell(row[1], 1, 1, Element.ALIGN_LEFT));
				table.addCell(createCell(row[2], 1, 1, Element.ALIGN_RIGHT));
				table.addCell(createCell(row[3], 1, 1, Element.ALIGN_RIGHT));
				table.addCell(createCell(row[4], 1, 1, Element.ALIGN_RIGHT));

			}
			table.addCell(createCell("Saldo", 2, 4, Element.ALIGN_LEFT));
			table.addCell(createCell(sp.getTotalPrice(), 2, 1, Element.ALIGN_RIGHT));

		} catch (

		DocumentException e) {
			e.printStackTrace();
		}
		return table;
	}

	public static PdfPCell createCell(String content, float borderWidth, int colspan, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(content));
		cell.setBorderWidth(borderWidth);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		return cell;
	}

	private static String[][] ListOfSoldArticletoArray(List<SoldArticle> sa) {

		String[][] array = new String[sa.size()][5];
		try {
			for (int i = 0; i < sa.size(); i++) {
				SoldArticle row = sa.get(i);
				array[i][0] = row.getArticleNumber();
				array[i][1] = row.getArticleName();
				array[i][2] = row.getQuantity();
				array[i][3] = row.getDiscount();
				array[i][4] = row.getPrice();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;

	}

}
