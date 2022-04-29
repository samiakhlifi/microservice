package com.bac.tn.gestionusers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDTO {

    private String nom;
    private String prenom;
    private String numTel;
    private String email;
}
