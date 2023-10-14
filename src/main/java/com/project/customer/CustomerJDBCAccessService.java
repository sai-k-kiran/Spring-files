package com.project.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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
                Select id, name, email, age, gender from customer;
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer custId) {
         var sql = """
                 Select id, name, email, age, gender from customer where id = ?;
                 """;
        return jdbcTemplate.query(sql, customerRowMapper, custId)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(age, name, email, gender) VALUES(?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                customer.getAge(), customer.getName(), customer.getEmail(), customer.getGender().name() );
    }


    @Override
    public boolean emailExists(String email) {
        var sql = """
                Select count(id) from customer where email = ?;
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return (count != null && count > 0);
    }

    @Override
    public void deleteCustomerById(Integer custId) {
        var sql = """
                Delete from customer where id = ?;
                """;
        jdbcTemplate.update(sql, custId);
    }

    @Override
    public boolean idExists(Integer id) {
        var sql = """
                Select count(id) from customer where id = ?;
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return (count != null && count > 0);
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId()
            );
            System.out.println("update customer name result = " + result);
        }
        if (update.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getAge(),
                    update.getId()
            );
            System.out.println("update customer age result = " + result);
        }
        if (update.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId());
            System.out.println("update customer email result = " + result);
        }
        if (update.getGender() != null) {
            String sql = "UPDATE customer SET gender = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getGender().name(),
                    update.getId());
            System.out.println("update customer gender result = " + result);
        }
    }
}
