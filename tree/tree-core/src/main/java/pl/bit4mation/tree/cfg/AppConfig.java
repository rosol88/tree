package pl.bit4mation.tree.cfg;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "pl.bit4mation.tree.repo" )
@ComponentScan( "pl.bit4mation.tree" )
public class AppConfig
{
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

    // @Bean
    // public DataSource dataSource()
    // {
    // DataSource dataSource = new EmbeddedDatabaseBuilder().setType( EmbeddedDatabaseType.HSQL ).setName( "tree"
    // ).build();
    // return dataSource;
    // }

    @Bean
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName( "org.postgresql.Driver" );
        dataSource.setUrl( "jdbc:postgresql://localhost:5432/plusproject" );
        dataSource.setUsername( "postgres" );
        dataSource.setPassword( "postgres" );
        return dataSource;
    }

    Properties hibernateProperties()
    {
        Properties props = new Properties();
        props.setProperty( "hibernate.hbm2ddl.auto", "update" );
        props.setProperty( "hibernate.show_sql", "true" );
        props.setProperty( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
        return props;
    }

    @Bean
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf )
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( emf );
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties()
    {
        Properties properties = new Properties();
        properties.setProperty( "hibernate.hbm2ddl.auto", "create-drop" );
        // properties.setProperty( "hibernate.dialect", "org.hibernate.dialect.HSQLDialect" );
        properties.setProperty( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
        return properties;
    }
}
