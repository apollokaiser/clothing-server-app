package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum BusinessErrorCode {
  CONNECTION_FAILED(999, "Connection Failed"),
  ACTIVATION_ACCOUNT_FAILED(1000, "Cannot active account because of internal error"),
  USER_NOT_FOUND(1001, "User do not exist"),
  USER_ALREADY_EXIST(1002, "User already exists"),
  USER_NOT_ACTIVATED(1003, "User not activated"),
  ACCOUNT_ASSOCIATES_FAILED(1004, "User has already been assigned to another account"),
  EMAIL_REGISTERED(1005, "Your email address is registered as an user"),
  WRONG_PASSWORD(1006, "Password is incorrect"),
  USER_ACTIVATED(1007, "User is already activated"),
  UNAUTHORIZED_FOR_READ(1008, "User is not authorized to read data"),
  UNAUTHORIZED_FOR_WRITE(1009, "User is not authorized to write data"),
  UNAUTHORIZED_FOR_UPDATE(1010, "User is not authorized to update data"),
  UNAUTHORIZED_FOR_DELETE(1011, "User is not authorized to delete data"),
  UNAUTHORIZED_FOR_MANAGEMENT(1012, "User is not authorized to manage data"),
  NO_CHANGE_APPLY(2000, "No data will be updated"),
  TOKEN_HAS_DESTROYED(3000, "Token has been destroyed"),
  INVALID_TOKEN(3001, "Invalid token"),
  EXPIRED_TOKEN(3002, "Token is expired"),
  INVALID_REFRESH_TOKEN(3003, "Refresh Token is invalid"),
  EXPIRED_REFRESH_TOKEN(3004, "Refresh Token is expired"),
  ACCESS_TOKEN_ERROR(3005, "Access Token Error"),
  INVALID_PROMOTION_CODE(3006, "Invalid Code"),
  EXPIRED_PROMOTION_CODE(3007, "Expired Code"),
  ERROR_MAIL(5001, "Can't send email"),
  NULL_POINTER_REQUEST_DATA(5002, "Request data might have been be null"),
  NOT_ALLOW_DATA_SOURCE(5003, "Cannot allowed data that was requested by another source"),
  ROLE_NOT_AVAILABLE(5004, "Role not available"),
  INTERNAL_ERROR(5005, "Internal error"),
  NOT_FOUND(5007, "Not found with your data"),
  DATA_EXISTS(5008, "Data exists"),
  DATA_INTERNAL_ERROR(5009, "Handling with data has some wrong"),
  CANNOT_DELETE_WITH_REFERENCE(5010, "Cannot delete entity with reference"),
  DUPLICATE_DATA(5011, "Duplicate data"),
  DATA_NOT_ENOUGH(5012, "Data not enough"),
  PROCESSING_ERROR(5013, "Processing error"),
  NULL_DATA(5006, "Null data"), INVALID_DATE( 5014,"Date value not allow" );
  private final int code;
  private final String message;

  BusinessErrorCode(int code, String message) {
      this.code = code;
      this.message = message;
  }
}
