package jgisson.spring.ws.springwsdemo.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.ArrayList;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurationSupport {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /* WSDL def */
    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws/countries");
        wsdl11Definition.setTargetNamespace("http://jgisson.spring.ws.springwsdemo.model");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean(name = "documents")
    public DefaultWsdl11Definition documentsWsdl11Definition(XsdSchema documentsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DocumentsPort");
        wsdl11Definition.setLocationUri("/ws/documents");
        wsdl11Definition.setTargetNamespace("http://jgisson.spring.ws.springwsdemo.model.documents");
        wsdl11Definition.setSchema(documentsSchema);
        return wsdl11Definition;
    }

    /* XSD def */
    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

    @Bean
    public XsdSchema documentsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("documents.xsd"));
    }

    /* Marshaller def */
    @Bean
    public Jaxb2Marshaller countriesMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("jgisson.spring.ws.springwsdemo.model");
        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller documentsMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("jgisson.spring.ws.springwsdemo.model");
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    /* MarshallingPayloadMethodProcessor def */
    @Bean
    public MarshallingPayloadMethodProcessor countriesMethodProcessor() {
        return new MarshallingPayloadMethodProcessor(countriesMarshaller());
    }

    @Bean
    public MarshallingPayloadMethodProcessor documentsMethodProcessor() {
        return new MarshallingPayloadMethodProcessor(documentsMarshaller());
    }

    @Bean
    @Override
    public DefaultMethodEndpointAdapter defaultMethodEndpointAdapter() {
        List<MethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(countriesMethodProcessor());
        argumentResolvers.add(documentsMethodProcessor());

        List<MethodReturnValueHandler> returnValueHandlers = new ArrayList<MethodReturnValueHandler>();
        returnValueHandlers.add(countriesMethodProcessor());
        returnValueHandlers.add(documentsMethodProcessor());

        DefaultMethodEndpointAdapter adapter = new DefaultMethodEndpointAdapter();
        adapter.setMethodArgumentResolvers(argumentResolvers);
        adapter.setMethodReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

}
