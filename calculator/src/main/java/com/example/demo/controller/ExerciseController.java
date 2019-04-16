package com.example.demo.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.ExerciseService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author:Liangll
 * @Description:
 * @Date: 10:37 2019/4/5
 */
@CrossOrigin
@Controller
@RequestMapping("/")
public class ExerciseController {

    @Resource
    ExerciseService exerciseService;

    /**
     * 产生练习题
     * @return
     */
    @ResponseBody
    @RequestMapping("/toCreate")
        public String CreateFile(String _n, String _max, String _min, String _maxOper, String _is_Bracket, String _is_Mul){
//    public String CreateFile(){

//        int n = 4;
//        int max = 100;
//        int min = 1;
//        int maxOper = 3;
//        int is_Bracket = 1;
//        int is_Mul = 4;
        int n = Integer.parseInt(_n);
        int max = Integer.parseInt(_max);
        int min = Integer.parseInt(_min);
        int maxOper = Integer.parseInt(_maxOper);
        int is_Bracket = Integer.parseInt(_is_Bracket);
        int is_Mul = Integer.parseInt(_is_Mul);
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
//                jsonObject.put("Answer", ans);
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
            String realPath = "C:\\Users\\19161\\Desktop\\sublime";
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

    /**
     * 跳转到文件上传处理页面
     * @return
     */
    @RequestMapping("file")
    public String file(){
        return "/test";
    }

    /**
     * 文件上传业务逻辑
     * @param file
     * @return
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "C:\\Users\\19161\\Desktop\\sublime\\calculator\\src\\main\\resources" ;
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("result.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            JSONArray jsonArray = new JSONArray();
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Exercise",line);
                jsonArray.add(jsonObject);
            }
            inputStream.close();
            return jsonArray.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
}
