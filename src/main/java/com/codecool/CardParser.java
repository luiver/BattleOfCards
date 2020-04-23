package com.codecool;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class CardParser {
    private Deck deck;
    private Document doc;

    public CardParser(){
        this.deck = new Deck();
        parseCards();
    }

    public Document getDoc() {
        return this.doc;
    }

    public Deck getDeck() {
        return deck;
    }

    protected void loadXmlDocument(String path) {
        try {
            File file = new File(path);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            this.doc = dBuilder.parse(file);
            this.doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void parseCards() {
        this.loadXmlDocument("data/Cards.xml");
        NodeList nlist = doc.getElementsByTagName("Card");
        for (int i = 0; i < nlist.getLength(); i++) {
            Node node = nlist.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element element = (Element) node;
            String id = element.getAttribute("id");
            NodeList nodeList = doc.getElementsByTagName("Description");
            Node node2 = nodeList.item(i);
            Element element2 = (Element) node2;
            String cardName = element2.getAttribute("value");
            Card card = new Card(id, cardName);
            addStatsToCard(card, element);
            this.deck.addCard(card);
        }
    }

    private void addStatsToCard(Card card, Element element) {
        Element evalsNode = (Element) element.getElementsByTagName("Evals").item(0);
        NodeList eval = evalsNode.getElementsByTagName("Eval");
        for (int i = 0; i < eval.getLength(); i++) {
            Element evalElement = (Element) eval.item(i);
            String id = evalElement.getAttribute("id");
            int value = Integer.parseInt(evalElement.getTextContent());
            card.setCardStatsById(id, value);
        }
    }
}
