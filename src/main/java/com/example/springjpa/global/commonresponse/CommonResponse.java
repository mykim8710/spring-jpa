package com.example.springjpa.global.commonresponse;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommonResponse {
    private int    status; 		// http Status(2XX, 3XX....)
    private String code; 		// 지정 code
    private String message; 	// 메세지
}
