package com.swms.utils.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageHelper {

    private PageHelper() {

    }

    public static <T> PageResult<T> covertPage(Page<T> page) {
        return covertPage(page.getContent(), page.getTotalElements());
    }

    public static <T> PageResult<T> covertPage(List<T> elements, long total) {
        PageResult<T> result = new PageResult<>();
        result.setResults(elements);
        result.setTotal(total);
        return result;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResult<T> {
        private List<T> results;
        private long total;
    }
}
