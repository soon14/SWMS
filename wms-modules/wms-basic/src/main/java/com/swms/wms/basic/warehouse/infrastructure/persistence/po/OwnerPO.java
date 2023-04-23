package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.utils.id.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class OwnerPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    // unique identifier
    private String ownerCode;
    private String ownerName;


    private String person;
    private String tel;
    private String fax;
    private String email;
    private String website;

    private String province;
    private String city;
    private String district;
    private String address;
    private String remark;

    private String batchAttributeConfigCode;

    private boolean deleted;
    private boolean enable;
}
