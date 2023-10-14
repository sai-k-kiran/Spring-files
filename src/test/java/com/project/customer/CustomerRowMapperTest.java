package com.project.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        CustomerRowMapper rowMapper = new CustomerRowMapper();
        ResultSet set = mock(ResultSet.class);

        when(set.getInt("id")).thenReturn(1);
        when(set.getInt("age")).thenReturn(19);
        when(set.getString("name")).thenReturn("Alex");
        when(set.getString("email")).thenReturn("alex@gmail.com");
        when(set.getString("gender")).thenReturn("MALE");

        Customer c = rowMapper.mapRow(set, 1);

        Customer expectedCustomer = new Customer(1, 19, "Alex", "alex@gmail.com", Gender.MALE);
        assertThat(c).isEqualTo(expectedCustomer); // this will fail unless you have override "equals()" method of
    }                                               // Customer. See Customer.java NOTE 1
}