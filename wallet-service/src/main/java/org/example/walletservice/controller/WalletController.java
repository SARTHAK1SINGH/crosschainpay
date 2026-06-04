package org.example.walletservice.controller;

import org.example.walletservice.dto.WalletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @GetMapping("/wallet")
    public WalletResponse getWallet() {
        return new WalletResponse("Sarthak", "10 Rs");
    }

}

