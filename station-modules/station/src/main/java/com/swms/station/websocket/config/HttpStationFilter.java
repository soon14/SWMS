package com.swms.station.websocket.config;

import com.swms.station.websocket.utils.HttpContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理http header的值
 *
 * @author niekang
 */
@Component
@WebFilter(filterName = "HttpStationFilter", urlPatterns = "/*")
@Slf4j
public class HttpStationFilter implements Filter {

    @Override
    public void destroy() { // nothing to do
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String stationCode = request.getParameter(HttpContext.STATION_CODE);
        if (StringUtils.isEmpty(stationCode)) {
            stationCode = ((HttpServletRequest) request).getHeader(HttpContext.STATION_CODE);
        }

        if (StringUtils.isNotEmpty(stationCode)) {
            HttpContext.setStationCode(stationCode);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            HttpContext.removeStationCode();
        }
    }

}
