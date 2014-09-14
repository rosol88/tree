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
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import pl.bit4mation.tree.cfg.AppConfig;

@EnableWebMvc
@Configuration
@ComponentScan( { "pl.bit4mation.tree.web.*" } )
@Import( { AppConfig.class } )
public class MvcConfig
    extends WebMvcConfigurerAdapter
{
    @Bean
    public InternalResourceViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass( JstlView.class );
        viewResolver.setPrefix( "/WEB-INF/pages/" );
        viewResolver.setSuffix( ".jsp" );
        return viewResolver;
    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
    {
        converters.add( converter() );
        super.configureMessageConverters( converters );
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper( new HibernateAwareObjectMapper() );
        return converter;
    }

}
