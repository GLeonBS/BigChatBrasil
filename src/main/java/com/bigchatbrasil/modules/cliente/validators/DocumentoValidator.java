package com.bigchatbrasil.modules.cliente.validators;

import com.bigchatbrasil.modules.cliente.annotations.DocumentoValido;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidator implements ConstraintValidator<DocumentoValido, ClienteEntity> {
    @Override
    public boolean isValid(ClienteEntity cliente, ConstraintValidatorContext context) {
        if (cliente == null) return true;

        String documento = cliente.getDocumento();
        TipoDocumento tipo = cliente.getTipoDocumento();

        if (documento == null || tipo == null) return false;

        if (TipoDocumento.CPF.equals(tipo)) {
            return isValidCPF(documento);
        }
        return isValidCNPJ(documento);
    }

    private boolean isValidCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1) {
            return false;
        }

        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            sum1 += num * (10 - i);
            sum2 += num * (11 - i);
        }

        int check1 = 11 - (sum1 % 11);
        check1 = (check1 >= 10) ? 0 : check1;
        sum2 += check1 * 2;

        int check2 = 11 - (sum2 % 11);
        check2 = (check2 >= 10) ? 0 : check2;

        return check1 == Character.getNumericValue(cpf.charAt(9)) &&
                check2 == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || cnpj.chars().distinct().count() == 1) {
            return false;
        }

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 12; i++) {
            int num = Character.getNumericValue(cnpj.charAt(i));
            sum1 += num * weights1[i];
            sum2 += num * weights2[i];
        }

        int check1 = sum1 % 11;
        check1 = (check1 < 2) ? 0 : 11 - check1;
        sum2 += check1 * weights2[12];

        int check2 = sum2 % 11;
        check2 = (check2 < 2) ? 0 : 11 - check2;

        return check1 == Character.getNumericValue(cnpj.charAt(12)) &&
                check2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
