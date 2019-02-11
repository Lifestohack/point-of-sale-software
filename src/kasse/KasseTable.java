package kasse;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KasseTable extends RecursiveTreeObject<KasseTable> {
	StringProperty articleNumber;
	StringProperty articleName;
	StringProperty quantity;
	StringProperty discount;
	StringProperty price;

	public KasseTable(String articleNumber, String articleName, String quantity, String discount, String price) {

		this.articleNumber = new SimpleStringProperty(articleNumber);
		this.articleName = new SimpleStringProperty(articleName);
		this.quantity = new SimpleStringProperty(quantity);
		this.discount = new SimpleStringProperty(Double.toString(Double.parseDouble(discount)));
		this.price = new SimpleStringProperty(Double.toString(Double.parseDouble(price)));
	}

}
