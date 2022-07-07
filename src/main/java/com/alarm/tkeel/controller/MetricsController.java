package com.alarm.tkeel.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/01/15:49
 */
@RestController
@RequestMapping("/")
public class MetricsController {

    /**
     * 告警指标采集
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(path = "/metrics")
    public void   getMetrics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/prometheus?method=forward");
        requestDispatcher.forward(request,response);
    }

}


