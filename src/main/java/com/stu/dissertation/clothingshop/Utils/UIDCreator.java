package com.stu.dissertation.clothingshop.Utils;

import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;

import java.time.Instant;

public class UIDCreator {
    public static String createAdminEmail(String email, String suffix) {
       if(!email.contains("@"))
           throw new ApplicationException(BusinessErrorCode.NULL_DATA, "email cannot be empty");
       String prefix = email.split("@")[0];
       return prefix + suffix;
    }
    public static String createAdminPassword() {
        return RandomCodeGenerator.generateRandomString(8);
    }
    public static String createAdminCode (String role) {
        String rolePrefix =  switch (role) {
            case "FULL_CONTROL" -> "FC";
            case "OUTFIT_UPDATE" -> "OU";
            case "SUPER_ACCOUNT" -> "SA";
            case "PROMOTION_UPDATE" -> "PU";
            default -> throw new ApplicationException(BusinessErrorCode.ROLE_NOT_AVAILABLE);
        };
        return rolePrefix + Instant.now().getEpochSecond();
    }
    public static String createUserCode() {
        return "U" + Instant.now().getEpochSecond();
    }
}
