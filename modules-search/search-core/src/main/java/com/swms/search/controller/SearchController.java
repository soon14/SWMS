package com.swms.search.controller;

import cn.zhxu.bs.MapSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import com.google.common.collect.Maps;
import com.swms.search.parameter.SearchParam;
import com.swms.search.utils.SearchUtils;
import com.swms.utils.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import javassist.CannotCompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

        return Response.builder().data(beanSearcher
            .search(SearchUtils.createClass(searchParam), SearchUtils.handleArrayParams(paramMap))).build();
    }

    @PostMapping("searchSelectResult")
    public Object searchSelectResult(HttpServletRequest request, @Validated @RequestBody SearchParam searchParam)
        throws CannotCompileException, ClassNotFoundException {
        SearchResult searchResult = beanSearcher.search(SearchUtils.createClass(searchParam), MapUtils.flat(request.getParameterMap()));
        return Response.builder().data(Maps.immutableEntry("options", searchResult.getDataList())).build();
    }

}

