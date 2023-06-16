package com.swms.mdm.api.main.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactorDTO {
    private String name;
    private String tel;
    private String mail;
    private String fax;
}
