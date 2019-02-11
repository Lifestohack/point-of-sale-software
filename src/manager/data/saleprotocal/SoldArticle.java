package manager.data.saleprotocal;

import javax.xml.bind.annotation.XmlElement;

public class SoldArticle {
	private String articleName;
	private String articleNumber;
	private String quantity;
	private String price;
	private String discount;

	public String getDiscount() {
		return discount;
	}

	@XmlElement(name = "Discount")
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getArticleName() {
		return articleName;
	}

	@XmlElement(name = "Articlename")
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	@XmlElement(name = "ArticleNumber")
	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	@XmlElement(name = "Quantity")
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	@XmlElement(name = "Price")
	public void setPrice(String price) {
		this.price = price;
	}
}
