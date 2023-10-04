package com.project.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer>{
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException{
        Customer c = new Customer(
                rs.getInt("id"),
                rs.getInt("age"),
                rs.getString("name"),
                rs.getString("email"));
        return c;
    }
}
