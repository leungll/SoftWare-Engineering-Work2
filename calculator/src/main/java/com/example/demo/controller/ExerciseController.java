package com.example.demo.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.ExerciseService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author:Liangll
 * @Description:
 * @Date: 10:37 2019/4/5
 */

@RestController
@RequestMapping("/")
public class ExerciseController {

    @Resource
    ExerciseService exerciseService;

    /**
     * 产生练习题
     * @return
     */
    @RequestMapping("/toCreate")
    public String CreateFile(){
        int n = 10;
        int max = 10 ;
        int min = 0 ;
        int maxOper = 5;
        int is_Bracket = 0;
        int is_Mul = 2;
        JSONArray jsonArray = new JSONArray();
        try {
            File file = new File("../result.txt");
            //如果根目录中存在名为result.txt文件，则删除
            if (file.exists()) {
                file.delete();
            }
            //创建文件
            file.createNewFile();
            //写文件
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write("2017011581");
            //换行
            bw.newLine();
            for (int i = 0; i < n; i++) {
                String[] strArr = new String[2];
                strArr= exerciseService.create(min, max, maxOper, is_Mul, is_Bracket);
                String ex = strArr[0];
                String ans = strArr[1];
                //将题目（无答案）写进文件
                bw.write(ex);
                bw.newLine();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Exercise", ex);
                jsonObject.put("Answer", ans);
                jsonArray.add(jsonObject);
            }
            bw.flush();
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }

    /**
     * 文件下载
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public String downloadFile(HttpServletResponse response) {
        String fileName = "result.txt";
        if (fileName != null) {
            String realPath = "E:\\";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
