package config;

import Beans.Cat;
import Beans.Owner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"services", "repository"})
@EnableTransactionManagement
public class ProjectConfig {
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/demo");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    //------------------------------------------------------------------------------------
    @Bean
    public Cat cat(){
        Cat c = new Cat();
        c.setName("Cat");
        return c;
    }
    @Bean
    public Cat cat1(){
        Cat c = new Cat();
        c.setName("Cat1");
        return c;
    }

    @Bean
    public Owner owner(@Qualifier("cat1") Cat cat){
        Owner man = new Owner();
        man.setCat(cat); // the cat1() method created above is passed as param here.
        return man;
    }
}
