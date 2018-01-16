package jgisson.spring.ws.springwsdemo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import jgisson.spring.ws.springwsdemo.model.GetCountryRequest;
import jgisson.spring.ws.springwsdemo.model.GetCountryResponse;
import jgisson.spring.ws.springwsdemo.repository.countries.CountryRepository;

@Endpoint
public class CountryEndpoint {

    private static final String NAMESPACE_URI = "http://jgisson.spring.ws.springwsdemo.model";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }

}