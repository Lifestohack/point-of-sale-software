package manager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import manager.data.article.Article;
import manager.data.article.Articles;
import manager.data.saleprotocal.SoldProtocal;
import manager.exportPDF.ExportPDF;

public class ArticleManager implements IArticleManager {
	private final String database = "db.xml";
	Articles articles;
	SoldProtocal soldProtocal;

	public boolean addArticle(String name, double price, int quantity, String description, String shipping,
			String shippingCenter) {

		return false;
	}

	public boolean changeArticle(String articleNumber, String attribute, String value) {
		for (Article s : articles.getArticle()) {
			if (s.getArticlenumber().contentEquals(articleNumber)) {
				switch (attribute) {
				case "articlenumber":
					s.setArticlenumber(value);
					return true;
				case "name":
					s.setName(value);
					return true;
				case "price":
					s.setPrice(value);
					return true;
				case "quantity":
					s.setQuantity(value);
					return true;
				case "description":
					s.setDescription(value);
					return true;
				default:
					return false;
				}
			}
		}
		return false;
	}

	public boolean addDiscount(float discountPercent) {

		return false;
	}

	public boolean deleteArticle(int productID) {

		return false;
	}

	public String getArticleInformation(String articleNumber, String attribute) {

		for (Article s : articles.getArticle()) {
			if (s.getArticlenumber().contentEquals(articleNumber)) {
				switch (attribute) {
				case "articlenumber":
					return s.getArticlenumber();
				case "name":
					return s.getName();
				case "price":
					return s.getPrice();
				case "quantity":
					return s.getQuantity();
				case "description":
					return s.getDescription();
				default:
					return "Invalid attribute";
				}
			}
		}

		return null;
	}

	public String startNewSale() {
		String path = System.getProperty("user.dir");
		articles = XMLParser.XMLUnmarshall(new File(path, database).toString());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();

		return dateFormat.format(date);
	}

	public boolean endSale(String orderID) {
		if (orderID == null || orderID.isEmpty() || soldProtocal == null) {
			return false;
		}
		String path = System.getProperty("user.dir");
		XMLParser.XMLArticlesMarshall(new File(path, database), articles);
		XMLParser.XMLMarshall(new File(path, soldProtocal.getSellingId() + ".xml"), soldProtocal);
		ExportPDF.createPDF(new File(path, soldProtocal.getSellingId() + ".pdf"), soldProtocal);
		return true;
	}

	public void setSoldArticle(SoldProtocal soldProtocal) {
		this.soldProtocal = soldProtocal;
	}

	public SoldProtocal getSoldArticle() {
		return soldProtocal;
	}
}
