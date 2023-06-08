package com.swati.ecommerce.entity;

import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
public class CustomFields {

    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String isActive;
}
