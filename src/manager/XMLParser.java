package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import manager.data.article.Articles;
import manager.data.saleprotocal.SoldProtocal;

public class XMLParser {

	public static Articles XMLUnmarshall(String path) {
		Articles articles = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Articles.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(path);
				Reader reader = null;
				try {
					reader = new InputStreamReader(inputStream, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				articles = (Articles) jaxbUnmarshaller.unmarshal(reader);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return articles;
	}

	public static void XMLMarshall(File file, SoldProtocal SoldProtocal) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SoldProtocal.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(SoldProtocal, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static void XMLArticlesMarshall(File file, Articles articles) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Articles.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(articles, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
