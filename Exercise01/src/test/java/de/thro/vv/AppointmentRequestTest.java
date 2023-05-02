package de.thro.vv;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppointmentRequestTest {
    private final Date currentDate = new Date(1999);
    private AppointmentRequest test;

    @BeforeEach
    void setup() {
        test = new AppointmentRequest("Jan", 10, currentDate, true);
    }

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new AppointmentRequest("Jan", 10, currentDate));
    }

    @Test
    void getCustomerName() {
        Assertions.assertEquals("Jan", test.getCustomerName());
    }

    @Test
    void setCustomerName() {
        test.setCustomerName("Sissi");
        Assertions.assertEquals("Sissi", test.getCustomerName());
    }

    @Test
    void getAppointmentRequestHour() {
        assertEquals(10, test.getAppointmentRequestHour());
    }

    @Test
    void setAppointmentRequestHour() {
        test.setAppointmentRequestHour(11);
        Assertions.assertEquals(11, test.getAppointmentRequestHour());

    }

    @Test
    void getCreateDate() {
        Assertions.assertEquals(new Date(1999), test.getCreateDate());
    }

    @Test
    void setCreateDate() {
        test.setCreateDate(new Date(2023));
        Assertions.assertEquals(new Date(2023), test.getCreateDate());
    }

    @Test
    void isSuccess() {
        Assertions.assertTrue(test.isSuccess());
    }

    @Test
    void setSuccess() {
        test.setSuccess(false);
        Assertions.assertFalse(test.isSuccess());
    }
}