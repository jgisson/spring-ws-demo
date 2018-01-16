package jgisson.spring.ws.springwsdemo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import jgisson.spring.ws.springwsdemo.model.GetDocumentRequest;
import jgisson.spring.ws.springwsdemo.model.GetDocumentResponse;
import jgisson.spring.ws.springwsdemo.model.StoreDocumentRequest;
import jgisson.spring.ws.springwsdemo.model.StoreDocumentResponse;
import jgisson.spring.ws.springwsdemo.repository.documents.DocumentRepository;


@Endpoint
public class DocumentEndpoint {

    private static final String NAMESPACE_URI = "http://jgisson.spring.ws.springwsdemo.model.documents";

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentEndpoint(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDocumentRequest")
    @ResponsePayload
    public GetDocumentResponse getDocument(@RequestPayload GetDocumentRequest request) {
        GetDocumentResponse response = new GetDocumentResponse();
        response.setDocument(documentRepository.getDocument());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeDocumentRequest")
    @ResponsePayload
    public StoreDocumentResponse getDocument(@RequestPayload StoreDocumentRequest request) {
        StoreDocumentResponse response = new StoreDocumentResponse();

        response.setSuccess(documentRepository.storeDocument(request.getDocument()));

        return response;
    }

}