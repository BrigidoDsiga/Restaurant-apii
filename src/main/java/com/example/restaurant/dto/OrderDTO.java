package com.example.restaurant.dto;

import java.util.List;

public class OrderDTO {
    private Long id;
    private Long clientId;
    private List<Long> dishIds;
    private Double total;
    private String status;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long clientId, List<Long> dishIds, Double total, String status) {
        this.id = id;
        this.clientId = clientId;
        this.dishIds = dishIds;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Long> getDishIds() {
        return dishIds;
    }

    public void setDishIds(List<Long> dishIds) {
        this.dishIds = dishIds;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}