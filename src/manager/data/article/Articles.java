package manager.data.article;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Articles")
public class Articles {

	private List<Article> article = new ArrayList<Article>();

	public List<Article> getArticle() {
		return article;
	}

	@XmlElement(name = "Article")
	public void setArticle(List<Article> article) {
		
		this.article = article;
	}

}
