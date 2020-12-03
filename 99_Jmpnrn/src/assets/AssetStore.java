/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package assets;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AssetStore {

	public AssetStore() {

	}

	public void load(String filename) {

	}

	public static void main(String args[]) {
		try {
			File file = new File("res/assets.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			Node img = doc.getElementsByTagName("images").item(0);
			Element element = (Element) img;
			NodeList images = element.getElementsByTagName("image");
			for (int itr = 0; itr < images.getLength(); itr++) {
				Element node = (Element) images.item(itr);
				System.out.println("\nNode Name :" + node.getNodeName());
				System.out.println("name: " + node.getAttribute("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
