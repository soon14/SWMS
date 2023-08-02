package com.swms.search.controller;

import cn.zhxu.bs.MapSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import com.google.common.collect.Maps;
import com.swms.search.parameter.SearchPageResult;
import com.swms.search.parameter.SearchParam;
import com.swms.search.utils.SearchUtils;
import com.swms.common.utils.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import javassist.CannotCompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("search")
@RestController
public class SearchController {

    @Autowired
    private MapSearcher beanSearcher;

    @PostMapping
    public Object search(HttpServletRequest request, @Validated @RequestBody Map<String, Object> requestMap)
        throws CannotCompileException, ClassNotFoundException {

        SearchParam searchParam = SearchParam.buildSearch(requestMap);

        Map<String, Object> paramMap = MapUtils.flat(request.getParameterMap());
        paramMap.putAll(requestMap);

        SearchResult<Map<String, Object>> search = beanSearcher
            .search(SearchUtils.createClass(searchParam), SearchUtils.handleArrayParams(paramMap));
        SearchPageResult searchPageResult = SearchPageResult.builder().items(search.getDataList())
            .total(search.getTotalCount().intValue()).build();
        return Response.builder().data(searchPageResult).build();
    }

    @PostMapping("searchSelectResult")
    public Object searchSelectResult(HttpServletRequest request, @Validated @RequestBody SearchParam searchParam)
        throws CannotCompileException, ClassNotFoundException {
        SearchResult<Map<String, Object>> searchResult = beanSearcher
            .search(SearchUtils.createClass(searchParam), MapUtils.flat(request.getParameterMap()));
        return Response.builder().data(Maps.immutableEntry("options", searchResult.getDataList())).build();
    }

}

