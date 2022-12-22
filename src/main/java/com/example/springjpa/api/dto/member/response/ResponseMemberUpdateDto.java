package com.example.springjpa.api.dto.member.response;

import lombok.Data;

@Data
public class ResponseMemberUpdateDto {
    private Long id;
    private String name;

    public ResponseMemberUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
