package com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CodigoAcessoValidator.class})
@Order(1)
public @interface CodigoAcesso {
    String message() default "O codigo deve ter 6 digitos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
