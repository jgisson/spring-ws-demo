package jgisson.spring.ws.springwsdemo.repository.documents;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import jgisson.spring.ws.springwsdemo.model.Document;

@Component
public class DocumentRepository {

    private static final String STORE_PATH = "c:/tests";

    public boolean storeDocument(Document doc) {

        boolean success = false;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = doc.getContent().getInputStream();
            out = new FileOutputStream(STORE_PATH + File.separator + doc.getName());
            IOUtils.copy(in, out);

            success = true;
        } catch (IOException e) {
            System.err.println("Can not store file " + doc.getName());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                // Blank
            }
        }

        return success;
    }

    public Document getDocument() {

        Document doc = new Document();
        doc.setName("tests.pdf");

        FileDataSource fds = new FileDataSource(STORE_PATH + File.separator + "tests.pdf");
        DataHandler contentHandler = new DataHandler(fds);
        doc.setContent(contentHandler);

        return doc;
    }

}