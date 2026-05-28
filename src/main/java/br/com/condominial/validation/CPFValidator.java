package br.com.condominial.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) return true; // handled by @NotBlank elsewhere
        String digits = cpf.replaceAll("[^0-9]", "");
        if (digits.length() != 11) return false;
        if (digits.chars().distinct().count() == 1) return false;
        return checkDigit(digits, 9) && checkDigit(digits, 10);
    }

    private boolean checkDigit(String digits, int position) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (position + 1 - i);
        }
        int remainder = (sum * 10) % 11;
        int expected = (remainder == 10 || remainder == 11) ? 0 : remainder;
        return expected == Character.getNumericValue(digits.charAt(position));
    }
}
