package com.xxx.securityjwt.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        PrintWriter writer=null;
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                writer.flush();
                writer.close();
            }
        }
    }
}