package manager;

public interface IArticleManager {

	public boolean addArticle(String name, double price, int quantity, String description, String shipping,
			String shippingCenter);

	public boolean changeArticle(String articleNumber, String attribute, String value);

	public boolean addDiscount(float discountPercent);

	public boolean deleteArticle(int productID);

	public String getArticleInformation(String articleNumber, String attribute);

	public String startNewSale();

	public boolean endSale(String orderID);
}
