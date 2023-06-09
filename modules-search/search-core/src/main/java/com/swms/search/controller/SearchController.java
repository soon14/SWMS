package com.swms.search.controller;

import cn.zhxu.bs.MapSearcher;
import cn.zhxu.bs.util.MapUtils;
import com.swms.search.parameter.SearchParam;
import com.swms.search.utils.SearchUtils;
import com.swms.utils.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import javassist.CannotCompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("search")
@RestController
public class SearchController {

    @Autowired
    private MapSearcher beanSearcher;

    @PostMapping
    public Object search(HttpServletRequest request, @Validated @RequestBody SearchParam searchParam)
        throws CannotCompileException, ClassNotFoundException {
        return Response.builder().data(
            beanSearcher.search(SearchUtils.createClass(searchParam), MapUtils.flat(request.getParameterMap()))).build();
    }

}

