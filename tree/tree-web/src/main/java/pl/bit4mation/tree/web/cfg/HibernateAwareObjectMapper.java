package pl.bit4mation.tree.web.cfg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * ObjectMapper extension for serializing hibernate proxied object
 * 
 * @author Paweł Rosolak
 */
public class HibernateAwareObjectMapper
    extends ObjectMapper
{

    private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper()
    {
        registerModule( new Hibernate4Module() );
    }
}
