package kasse;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import manager.ArticleManager;
import manager.data.saleprotocal.SoldArticle;
import manager.data.saleprotocal.SoldProtocal;

public class KasseManager {

	ArticleManager articleManager = new ArticleManager();
	SoldProtocal soldProtocal = new SoldProtocal();

	public void startNewSale() {

		String orderID = articleManager.startNewSale();
		System.out.println("Verkaufsnummer (Bon-ID) = " + orderID);
		soldProtocal.setSellingId(orderID);
	}

	public String[] addArticleToReceipt(String articleNumber) {
		String articleInfo[] = new String[4];
		String quantity = articleManager.getArticleInformation(articleNumber, "quantity");

		
		
		if (quantity != null && Integer.parseInt(quantity) > 0) {
			articleInfo[0] = articleManager.getArticleInformation(articleNumber, "articlenumber");
			articleInfo[1] = articleManager.getArticleInformation(articleNumber, "name");
			articleInfo[2] = Integer.toString(1);
			articleInfo[3] = articleManager.getArticleInformation(articleNumber, "price");
			changeArticle(articleInfo[0], "quantity", Integer.toString(-1));
			return articleInfo;
		}
		return null;
	}

	public void endSale(ObservableList<KasseTable> kasseTable, String saldo) {
		List<SoldArticle> listsa = new ArrayList<SoldArticle>();
		for (int i = 0; i < kasseTable.size(); i++) {
			SoldArticle sa = new SoldArticle();
			sa.setArticleName(kasseTable.get(i).articleName.getValue().toString());
			sa.setArticleNumber(kasseTable.get(i).articleNumber.getValue().toString());
			sa.setQuantity(kasseTable.get(i).quantity.getValue().toString());
			sa.setPrice(kasseTable.get(i).price.getValue().toString());
			sa.setDiscount(kasseTable.get(i).discount.getValue().toString());
			listsa.add(sa);
		}
		soldProtocal.setTotalPrice(saldo);
		soldProtocal.setSoldArticles(listsa);
		articleManager.setSoldArticle(soldProtocal);
		articleManager.endSale(soldProtocal.getSellingId());
	}

	public void changeArticle(String articleNumber, String attribute, String value) {
		int valueToAdd = Integer.parseInt(value);
		String quantity = articleManager.getArticleInformation(articleNumber, "quantity");
		int quantityInt = Integer.parseInt(quantity);
		int finalQuantity = quantityInt + valueToAdd;
		articleManager.changeArticle(articleNumber, attribute, Integer.toString(finalQuantity));
	}

	public String getArticleInformation(String articleNumber, String attribute) {
		return articleManager.getArticleInformation(articleNumber, attribute);
	}

	public void dispose() {
		articleManager = null;
		soldProtocal = null;

	}

}
