package com.bac.tn.gestionusers.payload.request;

import javax.validation.constraints.NotBlank;

public class ResetPawdRequest {
    @NotBlank
    private String password;

    @NotBlank
    private String resetToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
