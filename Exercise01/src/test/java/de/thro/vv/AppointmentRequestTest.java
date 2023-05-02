package de.thro.vv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppointmentRequestTest {
    private AppointmentRequest test;
    private Date currentDate = new Date(1999);


    @BeforeEach
    void setup(){
        test = new AppointmentRequest("Jan", 10, currentDate, true);
    }

    @Test
    void constructor(){
        assertDoesNotThrow(() -> new AppointmentRequest("Jan", 10, currentDate));
    }

    @Test
    void getCustomerName() {
        assertEquals("Jan",test.getCustomerName());
    }

    @Test
    void setCustomerName() {
        test.setCustomerName("Sissi");
        assertEquals("Sissi",test.getCustomerName());
    }

    @Test
    void getAppointmentRequestHour() {
        assertEquals(10, test.getAppointmentRequestHour());
    }

    @Test
    void setAppointmentRequestHour() {
        test.setAppointmentRequestHour(11);
        assertEquals(11, test.getAppointmentRequestHour());

    }

    @Test
    void getCreateDate() {
        assertEquals(new Date(1999), test.getCreateDate());
    }

    @Test
    void setCreateDate() {
        test.setCreateDate(new Date(2023));
        assertEquals(new Date(2023), test.getCreateDate());
    }

    @Test
    void isSuccess() {
        assertEquals(true, test.isSuccess());
    }

    @Test
    void setSuccess() {
        test.setSuccess(false);
        assertEquals(false, test.isSuccess());
    }
}