package net.quizz.common.utils;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DocumentSource;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

/**
 * @author lutfun
 * @since 4/28/17
 */
public class Utils {

    private static final String RESOURCE_PATH = "xml/";

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Utils.class);

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String getXml(Object object, Class clazz) {
        String xml = "";
        try (StringWriter xmlWriter = new StringWriter()) {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(object, xmlWriter);
            xml = xmlWriter.toString();
        } catch (PropertyException e) {
            log.error("Exception while setting marshaller property", e);
        } catch (JAXBException e) {
            log.error("Exception while marshalling", e);
        } catch (IOException e) {
            log.error("Exception while writing data: ", e);
        }

        return xml;
    }

    public static void writeToResponse(Object object,
                                       Class clazz,
                                       String fileName,
                                       HttpServletResponse response)
            throws TransformerException, DocumentException, IOException {

        String xml = Utils.getXml(object, clazz);
        String data = getData(fileName, xml);
        writePdfToResponse(fileName, response, data);
    }

    private static void writePdfToResponse(String fileName, HttpServletResponse response, String data) throws IOException {
        response.setContentType("application/pdf; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".pdf\"");

        Tidy tidy = new Tidy();
        tidy.setXmlOut(true);
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setShowErrors(0);
        tidy.setInputEncoding("UTF-8");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Document doc = tidy.parseDOM(inputStream, null);
        inputStream.close();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();
        try {
            renderer.createPDF(response.getOutputStream());
        } catch (com.lowagie.text.DocumentException e) {
            log.warn("Exception During Creation of XPDF: ", e);
        }
    }

    private static String getData(String fileName, String xml) throws DocumentException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();

        factory.setURIResolver(new URIResolver() {
            @Override
            public Source resolve(String href, String base) throws TransformerException {
                return getSource(RESOURCE_PATH + href);
            }
        });

        Transformer transformer = factory.newTransformer(getSource(getXsltFilePath(fileName)));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");


        DocumentSource source = new DocumentSource(DocumentHelper.parseText(xml));
        StringWriter stringWriter = new StringWriter();
        transformer.transform(source, new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    private static StreamSource getSource(String xsltFilePath) {
        return new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(xsltFilePath));
    }

    private static String getXsltFilePath(String fileName) {
        return RESOURCE_PATH + fileName + ".xsl";
    }
}
