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
    private int id;
    private int objectId;
    private int userId;
    private int typeId;
    private int classifierId;
    private String destination;
    private String pointOfDeparture;
    private boolean performed;
}
