package pl.bit4mation.tree.web.cfg;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pl.bit4mation.tree.cfg.AppConfig;

/**
 * Configure web app
 * 
 * @author Paweł Rosolak
 */
@EnableWebMvc
@Configuration
@ComponentScan( { "pl.bit4mation.tree.web.*" } )
@Import( { AppConfig.class } )
public class MvcConfig
    extends WebMvcConfigurerAdapter
{
    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
    {
        converters.add( converter() );
        super.configureMessageConverters( converters );
    }

    /**
     * Converter which allows to serialize hibernate object with proxied fields
     * 
     * @return JSON converter
     */
    @Bean
    public MappingJackson2HttpMessageConverter converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper( new HibernateAwareObjectMapper() );
        return converter;
    }

}
