package de.thro.vv.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class AppointmentRequest {
    @SerializedName(value = "customerName")
    private String customerName;
    @SerializedName(value = "appointmentRequestHour")
    private int appointmentRequestHour;
    @SerializedName(value = "createDate")
    private Date createDate; // yyyy-MM-dd hh:mm:ss
    private boolean success;

    public AppointmentRequest(String customerName, int appointmentRequestHour, Date createDate, boolean success) {
        this.customerName = customerName;
        this.appointmentRequestHour = appointmentRequestHour;
        this.createDate = createDate;
        this.success = success;
    }

    public AppointmentRequest(String customerName, int appointmentRequestHour, Date createDate) {
        this(customerName, appointmentRequestHour, createDate, false);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAppointmentRequestHour() {
        return appointmentRequestHour;
    }

    public void setAppointmentRequestHour(int appointmentRequestHour) {
        this.appointmentRequestHour = appointmentRequestHour;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}