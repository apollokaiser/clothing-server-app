package com.stu.dissertation.clothingshop.Exception.CustomException;

import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final BusinessErrorCode errorCode;
    private final Class<?> errorClass;
    public ApplicationException(BusinessErrorCode errorCode, Class<?> errorClass){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorClass = errorClass;
    }
    public ApplicationException(BusinessErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorClass = null;
    }
    public ApplicationException(BusinessErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
        this.errorClass = null;
    }
}
