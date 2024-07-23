package com.stu.dissertation.clothingshop.Payload.Response;

import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class ResponseMessage {
    private int status;
    private String message;
    private Map<String, Object> data;
    @Builder
    public ResponseMessage(HttpStatus status, String message, Map<String, Object> data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    @Builder(builderMethodName = "errorBuilder", buildMethodName = "handle")
    public ResponseMessage(BusinessErrorCode errorCode, String message) {
        this.status = errorCode.getCode();
        this.message = message.isEmpty()? errorCode.getMessage() : message;
        this.data = null;
    }
}
