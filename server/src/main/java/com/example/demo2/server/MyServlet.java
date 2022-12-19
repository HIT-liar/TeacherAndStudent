package com.example.demo2.server;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class MyServlet extends HttpServlet {
    protected static JSONObject getRequestJson(HttpServletRequest request) throws IOException {
        BufferedReader read = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = read.readLine()) != null) {
            sb.append(line);
        }
        // 获取客户端发来的请求，恢复其Json格式
        String req = sb.toString();
        JSONObject requestJson = new JSONObject(req);
        return requestJson;
    }
}
