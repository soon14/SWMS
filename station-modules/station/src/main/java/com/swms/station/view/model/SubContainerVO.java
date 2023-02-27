package com.swms.station.view.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubContainerVO {
    private Integer level;
    private Integer bay;
    private boolean active;
    private boolean enable;
    private String subContainerCode;
    private String subContainerName;
}
