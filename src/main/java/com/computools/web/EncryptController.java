package com.computools.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/secure")
public class EncryptController {


    @GetMapping(value = "/encrypt")
    public String encrypt(@RequestParam String password) {
        TextEncryptor encryptor = Encryptors.text("password", "5c0744940b5c369b");
        String result = encryptor.encrypt(password);
        return result;
    }

    @GetMapping(value = "/decrypt")
    public String decrypt(@RequestParam String encryptedPassword) {
        TextEncryptor encryptor = Encryptors.text("password", "5c0744940b5c369b");
        String result = encryptor.decrypt(encryptedPassword);
        return result;
    }
}
