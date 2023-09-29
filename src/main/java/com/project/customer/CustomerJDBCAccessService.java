package com.project.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCAccessService implements CustomerDAO{
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                Select id, name, email, age from customer;
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer custId) {
//         var sql = """
//                 Select id, name, email, age from customer where id = custId;
//                 """;
//        return jdbcTemplate.query;
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(age, name, email) VALUES(?, ?, ?)
                """;
        int update = jdbcTemplate.update(sql,
                customer.getAge(), customer.getName(), customer.getEmail());

        System.out.println("jdbcTemplate.update = " + update);
    }


    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public void deleteCustomerById(Integer custId) {

    }

    @Override
    public boolean idExists(Integer id) {
        return false;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
