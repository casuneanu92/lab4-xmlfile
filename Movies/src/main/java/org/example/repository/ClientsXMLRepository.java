package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ClientsXMLRepository extends InMemoryRepository<Long, Clients> {

    private String filename;
    private static final String Clients_Tag = "clients";
    private static final String Clients_Id_Tag = "clientsId";
    private static final String Clients_LastName_Tag = "lastName";
    private static final String Clients_FirstName_Tag = "firstName";
    private static final String Clients_Age_Tag = "age";


    public ClientsXMLRepository(Validator<Clients> validator, String filename) {
        super(validator);
        this.filename = filename;

        loadData();

    }

    private void loadData() {
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            Element rootElement = document.getDocumentElement();
            //NodeList doctorNodes = rootElement.getChildNodes();

            NodeList nodes = rootElement.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes.item(i);
                    Element clientsElement = (Element) element;
                    Clients clients = buildClientsFromClientsElement(clientsElement);

                    try {
                        super.save(clients);
                    } catch (ValidatorException e) {//is thrown only when I could not add because a car with the same id already is in repoi
                        e.printStackTrace();
                    }
                }
            }
        }catch (ValidatorException | ParserConfigurationException | SAXException | IOException  ex){
            ex.printStackTrace();
        }
    }

    private static Clients buildClientsFromClientsElement(Element doctorElement) {

        Node idNode = doctorElement.getElementsByTagName(Clients_Id_Tag).item(0);
        String id = idNode.getTextContent();

        Node lastNameNode = doctorElement.getElementsByTagName(Clients_LastName_Tag).item(0);
        String lastName = lastNameNode.getTextContent();

        Node firstNameNode = doctorElement.getElementsByTagName(Clients_FirstName_Tag).item(0);
        String fistName = firstNameNode.getTextContent();

        Node ageNode = doctorElement.getElementsByTagName(Clients_Age_Tag).item(0);
        String age = ageNode.getTextContent();

        Clients clients = new Clients(Long.parseLong(id), lastName, fistName, Integer.parseInt(age));
        return clients;

    }

    @Override
    public Optional<Clients> save(Clients clients) throws ValidatorException {
        Optional<Clients> clientsOptional = super.save(clients);
        if (clientsOptional.isPresent()) {
            return clientsOptional;
        }
        try {
            saveToFile(clients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    protected void saveToFile(Clients entity) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        // Getting the Document
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filename);

        // Adding a general Car Node(Tag) to the Document
        addClientsToDOM(entity, document);

        // Save Document to XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(
                new DOMSource(document),
                new StreamResult(new File(this.filename)));

    }

    private static void addClientsToDOM(Clients clients, Document document) {
        Element rootElement = document.getDocumentElement();
        Node clientsNode = createNodeFromClients(clients, document);
        rootElement.appendChild(clientsNode);
    }

    private static Node createNodeFromClients(Clients clientsToSave, Document document) {
        // Create Doctor node with its respective children
        Element clientsElement = document.createElement(Clients_Tag);

        // append id Element(Tag)
        Element idElement = document.createElement(Clients_Id_Tag);
        idElement.setTextContent(clientsToSave.getIdEntity().toString());
        clientsElement.appendChild(idElement);

        //append lastName Element (Tag)
        Element lastNameElement = document.createElement(Clients_LastName_Tag);
        lastNameElement.setTextContent(clientsToSave.getLastName());
        clientsElement.appendChild(lastNameElement);

        // append firstName Element(Tag)
        Element firstNameElement = document.createElement(Clients_FirstName_Tag);
        firstNameElement.setTextContent(clientsToSave.getFirstName());
        clientsElement.appendChild(firstNameElement);

        // append age Element(Tag)
        Element ageElement = document.createElement(Clients_Age_Tag);
        ageElement.setTextContent(String.valueOf(clientsToSave.getAge()));
        clientsElement.appendChild(ageElement);

        return clientsElement;
    }

    @Override
    public Optional<Clients> update (Clients clients) throws ValidatorException, IllegalAccessException {
        Optional<Clients> optionalClients = super.update(clients);
        try {
            deleteNode(clients.getIdEntity().toString());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        try {
            saveToFile(clients);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
        return optionalClients;
    }


    public void deleteNode(String id) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory  docFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filename);

        NodeList products = doc.getElementsByTagName("clients");
        for (int i = 0; i < products.getLength(); i++) {
            Element product = (Element) products.item(i);
            Element idTag = (Element) product.getElementsByTagName("clientsId").item(0);
            if (idTag.getTextContent().equalsIgnoreCase(id)) {
                idTag.getParentNode().getParentNode().removeChild(products.item(i));
                break;
            }
        }
        saveXMLContent(doc, filename);
    }

    private void saveXMLContent(Document doc, String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //          transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(fileName);
            transformer.transform(domSource, streamResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public Optional<Clients> delete(Long id) throws IllegalAccessException {
        Optional<Clients> clients = super.delete(id);
        try {
            deleteNode(id.toString());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
