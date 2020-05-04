package com.company.bean;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Shipping implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String pointOfDeparture;
    private String destination;
    private boolean performed;
    private int userId;
    private int typeId;
    private int classifierId;
    private int carId;
}
