package org.example.walletservice.controller

import org.example.walletservice.dto.WalletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WalletController {

    @GetMapping("/wallet")
    fun getWallet(): WalletResponse {
        return WalletResponse("Sarthak", "10 Rs")
    }

}

