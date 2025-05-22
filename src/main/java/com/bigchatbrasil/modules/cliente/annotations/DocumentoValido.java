package com.bigchatbrasil.modules.cliente.annotations;

import com.bigchatbrasil.modules.cliente.validators.DocumentoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentoValido {
    String message() default "Documento inválido para o tipo informado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
