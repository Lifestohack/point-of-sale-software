package kasse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import manager.data.saleprotocal.SoldArticle;
import manager.data.saleprotocal.SoldProtocal;
import manager.exportPDF.ExportPDF;

public class CreatePDF {

	//For testing
	public static void main(String[] args) {
		SoldProtocal sp = new SoldProtocal();
		sp.setSellingId("65464865132");
		List<SoldArticle> lsa = new ArrayList<SoldArticle>();

		for (int i = 0; i < 10; i++) {
			SoldArticle sa = new SoldArticle();
			sa.setArticleName("Diwas" + i);
			sa.setArticleNumber("1234");
			sa.setPrice(Integer.toString(i));
			sa.setQuantity(Integer.toString(i));
			sa.setDiscount("10");
			lsa.add(sa);
		}
		sp.setSoldArticles(lsa);
		sp.setTotalPrice("1000");
		String path = System.getProperty("user.dir");
		ExportPDF.createPDF(new File(path, sp.getSellingId() + ".pdf"), sp);

	}

}
