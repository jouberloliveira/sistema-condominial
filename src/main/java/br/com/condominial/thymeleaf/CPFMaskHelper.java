package br.com.condominial.thymeleaf;

public class CPFMaskHelper {

    public String mask(String cpf) {
        if (cpf == null) return "";
        String digits = cpf.replaceAll("[^0-9]", "");
        if (digits.length() < 11) return cpf;
        // Shows only last 6 digits: ***.***.XXX-YY
        return "***.***."+digits.substring(5, 8)+"-"+digits.substring(9, 11);
    }
}
