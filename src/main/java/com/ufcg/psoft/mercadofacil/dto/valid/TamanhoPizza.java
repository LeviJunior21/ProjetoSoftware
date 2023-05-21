package com.ufcg.psoft.mercadofacil.dto.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TamanhoPizzaValidator.class})
@Order(10)
public @interface TamanhoPizza {
    String message() default "O tamanho deve ser MEDIO ou GRANDE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
