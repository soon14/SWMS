package com.swms.utils.http.parameter;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

@Data
public class DateSearchParam {

    /**
     * 创建日期
     */
    private Long startCreateTime;

    private Long endCreateTime;

    public Long getEndCreateTimePlus1Day() {
        if (this.endCreateTime == null) {
            return null;
        }
        return DateUtils.addDays(new Date(this.endCreateTime), 1).getTime();
    }

    private Long startUpdateTime;

    private Long endUpdateTime;

    public Long getEndUpdateTimePlus1Day() {
        if (this.endUpdateTime == null) {
            return null;
        }
        return DateUtils.addDays(new Date(this.endUpdateTime), 1).getTime();
    }

}
