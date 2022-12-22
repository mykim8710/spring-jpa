package com.example.springjpa.api.dto.member.response;

import lombok.Data;

@Data
public class ResponseMemberInsertDto {
    private Long id;

    public ResponseMemberInsertDto(Long id) {
        this.id = id;
    }
}
