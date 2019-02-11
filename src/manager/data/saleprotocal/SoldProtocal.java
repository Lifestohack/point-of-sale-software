package manager.data.saleprotocal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SoldArticle")
public class SoldProtocal {

	private String sellingId;
	private String sellingDate;
	private String totalPrice;
	private List<SoldArticle> soldArticle = new ArrayList<SoldArticle>();

	public String getSellingId() {
		return sellingId;
	}

	@XmlAttribute(name = "SellingId")
	public void setSellingId(String sellingId) {
		this.sellingId = sellingId;
	}

	public String getSellingDate() {
		return sellingDate;
	}

	@XmlAttribute(name = "SellingDate")
	public void setSellingDate(String sellingDate) {
		this.sellingDate = sellingDate;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	@XmlElement(name = "Saldo")
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<SoldArticle> getSoldArticles() {
		return soldArticle;
	}

	@XmlElement(name = "Article")
	public void setSoldArticles(List<SoldArticle> soldArticle) {
		this.soldArticle = soldArticle;
	}

}
