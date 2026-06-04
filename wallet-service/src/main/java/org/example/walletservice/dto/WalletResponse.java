package org.example.walletservice.dto;

public class WalletResponse {
    private String user;
    private String balance;

    public WalletResponse() {
    }

    public WalletResponse(String user, String balance) {
        this.user = user;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

