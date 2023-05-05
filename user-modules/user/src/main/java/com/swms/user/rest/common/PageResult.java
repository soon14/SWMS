package com.swms.user.rest.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author sws
 * @Date 2021/3/24 14:00
 * @Description:
 */
@Data
public class PageResult<T> {
    private String total;

    private List<T> results;

    public static PageResult convert(IPage page) {
        PageResult result = new PageResult();
        result.setResults(page.getRecords());
        result.setTotal(String.valueOf(page.getTotal()));
        return result;
    }
}
