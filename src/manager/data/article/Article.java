package manager.data.article;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Article {
	private String name;
	private String price;
	private String quantity;
	private String description;
	private String articlenumber;

	public String getName() {
		return name;
	}

	@XmlElement(name = "Name")
	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	@XmlElement(name = "Price")
	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	@XmlElement(name = "Quantity")
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement(name = "Description")
	public void setDescription(String description) {
		this.description = description;
	}

	public String getArticlenumber() {
		return articlenumber;
	}

	@XmlAttribute(name = "Articlenumber")
	public void setArticlenumber(String articlenumber) {
		this.articlenumber = articlenumber;
	}

}
