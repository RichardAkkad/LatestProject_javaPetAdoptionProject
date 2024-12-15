package com.postgresql.ytdemo2.model;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name="dogs")


public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Add this line to auto-generate IDs
    private long id;

    public String sex;
    public String breed;
    private long age;
    public String colour;



}
