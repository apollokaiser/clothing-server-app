package com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') and hasAnyAuthority('SUPER_ACCOUNT', 'FULL_CONTROL')")
public @interface ManagerRequired {
}
