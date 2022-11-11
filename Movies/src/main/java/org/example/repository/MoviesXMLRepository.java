package org.example.repository;

import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;
import org.example.domain.Movies;
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

public class MoviesXMLRepository extends InMemoryRepository<Long, Movies> {

    private String filename;
    private static final String Movies_Tag = "movies";
    private static final String Movies_Id_Tag = "moviesId";

    private static final String Movies_Name_Tag = "name";

    private static final String Movies_Year_Tag = "year";

    private static final String Movies_Duration_Tag = "duration";
    public MoviesXMLRepository(Validator<Movies> validator, String filename) {
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
            NodeList moviesNodes = rootElement.getChildNodes();

            for (int i = 0; i < moviesNodes.getLength(); i++) {
                Node moviesNode = moviesNodes.item(i);
                if (!(moviesNodes instanceof Element)) {
                    continue;
                }
                Element moviesElement = (Element) moviesNode;
                Movies movies = buildMoviesFromMoviesElement(moviesElement);

                try {
                    super.save(movies);
                } catch (ValidatorException e) {//is thrown only when I could not add because a car with the same id already is in repoi
                    e.printStackTrace();
                }
            }
        }catch (ValidatorException | ParserConfigurationException | SAXException | IOException  ex){
            ex.printStackTrace();
        }
    }

    private static Movies buildMoviesFromMoviesElement(Element moviesElement) {
        Node idNode = moviesElement.getElementsByTagName(Movies_Tag).item(0);
        String id = idNode.getTextContent();

        Node nameNode = moviesElement.getElementsByTagName(Movies_Name_Tag).item(0);
        String name = nameNode.getTextContent();

        Node yearNode = moviesElement.getElementsByTagName(Movies_Year_Tag).item(0);
        String year = yearNode.getTextContent();

        Node durationNode = moviesElement.getElementsByTagName(Movies_Duration_Tag).item(0);
        String duration = durationNode.getTextContent();

        Movies movies = new Movies(Long.parseLong(id), name, Integer.parseInt(year), Integer.parseInt(duration));
        return movies;
        }

    @Override
    public Optional<Movies> save(Movies movies) throws ValidatorException {
        Optional<Movies> moviesOptional = super.save(movies);
        if (moviesOptional.isPresent()) {
            return moviesOptional;
        }
        try {
            saveToFile(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    protected void saveToFile(Movies entity) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        // Getting the Document
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filename);

        // Adding a general Car Node(Tag) to the Document
        addMoviesToDOM(entity, document);

        // Save Document to XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(
                new DOMSource(document),
                new StreamResult(new File(this.filename)));

    }

    private static void addMoviesToDOM(Movies movies, Document document) {
        Element rootElement = document.getDocumentElement();
        Node moviesNode = createNodeFromMovies(movies, document);
        rootElement.appendChild(moviesNode);
    }

    private static Node createNodeFromMovies(Movies moviesToSave, Document document) {
        // Create Doctor node with its respective children
        Element moviesElement = document.createElement(Movies_Tag);

        // append id Element(Tag)
        Element idElement = document.createElement(Movies_Id_Tag);
        idElement.setTextContent(moviesToSave.getId().toString());
        moviesElement.appendChild(idElement);

        //append lastName Element (Tag)
        Element nameElement = document.createElement(Movies_Name_Tag);
        nameElement.setTextContent(moviesToSave.getName());
        moviesElement.appendChild(nameElement);

        // append firstName Element(Tag)
        Element yearElement = document.createElement(Movies_Year_Tag);
        yearElement.setTextContent(String.valueOf(moviesToSave.getYear()));
        moviesElement.appendChild(yearElement);

        // append age Element(Tag)
        Element durationElement = document.createElement(Movies_Duration_Tag);
        durationElement.setTextContent(String.valueOf(moviesToSave.getDuration()));
        moviesElement.appendChild(durationElement);

        return moviesElement;
    }

    @Override
    public Optional<Movies> update (Movies movies) throws ValidatorException {
        Optional<Movies> optionalDoctor = super.update(movies);
        try {
            deleteNode(movies.getId().toString());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        try {
            saveToFile(movies);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
        return optionalDoctor;
    }


    public void deleteNode(String id) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory  docFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filename);

        NodeList products = doc.getElementsByTagName("movies");
        for (int i = 0; i < products.getLength(); i++) {
            Element product = (Element) products.item(i);
            Element idTag = (Element) product.getElementsByTagName("moviesId").item(0);
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
    public Optional<Movies> delete(Long id) {
        Optional<Movies> movies = super.delete(id);
        try {
            deleteNode(id.toString());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return movies;
    }
}

