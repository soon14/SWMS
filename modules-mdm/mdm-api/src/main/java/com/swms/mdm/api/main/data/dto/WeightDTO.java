package com.swms.mdm.api.main.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightDTO {
    private Long grossWeight;
    private Long netWeight;
}
