package pl.bit4mation.tree.cfg;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class
 * 
 * @author Paweł Rosolak
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "pl.bit4mation.tree.repo" )
@ComponentScan( "pl.bit4mation.tree" )
public class AppConfig
{
    /**
     * Configure entity manager factory
     * 
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource() );
        em.setPackagesToScan( new String[] { "pl.bit4mation.tree.model" } );

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter( vendorAdapter );
        em.setJpaProperties( additionalProperties() );

        return em;
    }

    @Bean
    public DataSource dataSource()
    {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType( EmbeddedDatabaseType.HSQL ).setName( "tree" ).build();
        return dataSource;
    }

    /**
     * Configure transaction manager
     * 
     * @param emf entity manager factory
     * @return transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf )
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( emf );
        return transactionManager;
    }

    /**
     * Create class for exception translation
     * 
     * @return exception translator
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Hibernate properties definition
     * 
     * @return hibernate properties
     */
    Properties additionalProperties()
    {
        Properties properties = new Properties();
        properties.setProperty( "hibernate.hbm2ddl.auto", "create-drop" );
        properties.setProperty( "hibernate.dialect", "org.hibernate.dialect.HSQLDialect" );
        return properties;
    }
}
