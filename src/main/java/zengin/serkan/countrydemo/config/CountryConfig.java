package zengin.serkan.countrydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import zengin.serkan.countrydemo.dto.CountryDTO;
import zengin.serkan.countrydemo.mapper.ICountryMapper;
import zengin.serkan.countrydemo.model.Country;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Set;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "zengin.serkan.countrydemo.mapper")
public class CountryConfig {

    private Set<CountryDTO> countries;

    @Bean
    public Set<Country> getCountries() {
        return mapper.mapToCountrySet(countries);
    }

    @Autowired
    private ICountryMapper mapper;

    public CountryConfig(@Value("${countryconfig.serviceurl}") String serviceUrl,
                         @Value("${countryconfig.proxy.host}") String proxyHost,
                         @Value("${countryconfig.proxy.port}") int proxyPort,
                         @Value("${countryconfig.useproxy}") Boolean useproxy) {

        //I needed to add proxy configration to ovecome connection issues
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        if (useproxy) {
            requestFactory.setProxy(proxy);
        }

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Set<CountryDTO>> entity = new HttpEntity<Set<CountryDTO>>(headers);

        ParameterizedTypeReference<Set<CountryDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        //Response from https://raw.githubusercontent.com/mledoze/countries/master/countries.json comes as text/plain, so I had to add this converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        ResponseEntity<Set<CountryDTO>> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, entity, typeRef);

        this.countries = response.getBody();
    }
}
