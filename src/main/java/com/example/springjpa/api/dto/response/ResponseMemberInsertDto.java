package com.example.springjpa.api.dto.response;

import lombok.Data;

@Data
public class ResponseMemberInsertDto {
    private Long id;

    public ResponseMemberInsertDto(Long id) {
        this.id = id;
    }
}
