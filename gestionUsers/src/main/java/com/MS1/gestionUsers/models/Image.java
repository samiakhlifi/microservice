package com.bac.tn.gestionusers.models;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 64)
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;


    private long userId;


    public Image(String name, long userId, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.userId = userId;
    }
}
