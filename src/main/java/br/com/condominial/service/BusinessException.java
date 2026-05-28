package br.com.condominial.service;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
