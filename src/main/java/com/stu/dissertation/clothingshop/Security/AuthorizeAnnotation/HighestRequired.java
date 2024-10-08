package com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') and hasAuthority('SUPER_ACCOUNT')")
public @interface HighestRequired {
}
