package org.example.dto;

public class ResponseDTO {
    private String status;
    private String data;

    public ResponseDTO(String status, String data) {
        this.status = status;
        this.data = data;
    }
}
