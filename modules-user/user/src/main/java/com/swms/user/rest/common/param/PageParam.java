package com.swms.user.rest.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页参数
 *
 * @author sws
 * @version 1.0
 * @since 2020-12-26
 */
@Data
public class PageParam {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页码(>=0)(内部兼容，前端忽略，请使用外层pageIndex)", required = true)
    private Integer currentPage;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小(>=1)(内部兼容，前端忽略，请使用外层pageSize)", required = true)
    private Integer pageSize;

    public Integer getCurrentPage() {
        if (null == currentPage || currentPage.intValue() <= 0) {
            return 1;
        } else {
            return currentPage;
        }
    }

    public Integer getPageSize() {
        if (null == pageSize || pageSize.intValue() <= 0) {
            return 10;
        } else {
            return pageSize;
        }
    }
}
