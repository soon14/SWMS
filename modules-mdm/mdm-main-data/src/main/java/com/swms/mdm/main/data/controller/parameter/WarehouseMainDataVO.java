package com.swms.mdm.main.data.controller.parameter;

import com.swms.mdm.api.main.data.dto.AddressDTO;
import com.swms.mdm.api.main.data.dto.ContactorDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseMainDataVO extends WarehouseMainDataDTO {

    private String name;
    private String tel;
    private String mail;
    private String fax;

    private String country;
    private String province;
    private String city;
    private String district;
    private String address;

    @Override
    public ContactorDTO getContactorDTO() {
        return new ContactorDTO(name, tel, mail, fax);
    }

    @Override
    public AddressDTO getAddressDTO() {
        return new AddressDTO(country, province, city, district, address);
    }
}
