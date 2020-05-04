package com.company.bean;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Object extends Shipping implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idObject;
    private String description;
    private String phoneNumber;
    private int idCity;
    private String siteManager;
}
