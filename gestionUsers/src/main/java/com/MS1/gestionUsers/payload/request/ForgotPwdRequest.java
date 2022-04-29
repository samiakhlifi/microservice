package com.bac.tn.gestionusers.payload.request;

import javax.validation.constraints.NotBlank;

public class ForgotPwdRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String resetToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
