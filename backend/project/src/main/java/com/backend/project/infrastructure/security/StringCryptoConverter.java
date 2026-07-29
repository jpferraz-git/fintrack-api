package com.backend.project.infrastructure.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
@Component
public class StringCryptoConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";
    
    // Na prática deveria ser uma chave injetada via properties, 
    // mas AttributeConverter nem sempre injeta beans facilmente sem configuração extra no Hibernate.
    // Para fins do projeto, usaremos uma chave estática baseada na property ou uma padrão de 16 bytes.
    private static final byte[] KEY = "MySuperSecretKey".getBytes(StandardCharsets.UTF_8);

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting PII data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Se falhar a decriptação, retorna o dado puro (pode ser dado antigo não criptografado ainda)
            // Em produção, precisaria de script de migração.
            return dbData;
        }
    }
}
