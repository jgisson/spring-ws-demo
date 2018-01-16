package jgisson.spring.ws.springwsdemo.repository.documents;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jgisson.spring.ws.springwsdemo.model.Document;

@Component
public class DocumentRepository {

    private static final String STORE_PATH = "c:/tests";

    public boolean storeDocument(Document doc) {

        boolean success = false;

        try {
            byte[] buf = Base64Utils.decode(doc.getContent());
            File docFile = new File(STORE_PATH + File.separator + doc.getName());
            FileUtils.writeByteArrayToFile(docFile, buf);
            success = true;
        } catch (IOException e) {
            System.err.println("Can not store file " + doc.getName());
        }

        return success;
    }

    public Document getDocument() {

        Document doc = new Document();
        doc.setName("tests.pdf");

        try {
            InputStream in = new FileInputStream(STORE_PATH + File.separator + "tests.pdf");
            byte[] buf = IOUtils.toByteArray(in);
            byte[] buf64 = Base64Utils.encode(buf);
            doc.setContent(buf64);
        } catch (IOException e) {
            System.err.println("Can not load file " + doc.getName() + ".");
            doc.setContent(null);
        }

        return doc;
    }

}