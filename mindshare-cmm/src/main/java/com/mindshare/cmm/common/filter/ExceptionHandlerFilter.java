package com.mindshare.cmm.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindshare.cmm.common.exception.ApiException;
import com.mindshare.cmm.common.exception.ErrorResponse;
import com.mindshare.cmm.common.utils.ErrorLogUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
            response.setStatus(e.getErrorCode().getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            try {
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            } catch (IOException ioe) {
                ErrorLogUtils.printError(log, ioe);
            }
        }
    }

}
