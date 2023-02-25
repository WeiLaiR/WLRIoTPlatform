package com.wei.mailservice.utils;

import com.wei.mailservice.exception.CustomException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class BuildMail {
    //加载邮件html模板

    private static final Resource resource = new ClassPathResource("templates/mailtemplate.ftl");

    private static volatile InputStream inputStream = null;

    private static volatile BufferedReader fileReader = null;

    private static final StringBuffer buffer = new StringBuffer();

    private static final Resource resource1 = new ClassPathResource("templates/mailwarntemplate.ftl");

    private static volatile InputStream inputStream1 = null;

    private static volatile BufferedReader fileReader1 = null;

    private static final StringBuffer buffer1 = new StringBuffer();



    /**
     * 读取邮件模板
     * 替换模板中的信息
     *
     * @param title 内容
     * @return 处理后的模板
     */
    public static String buildContent(String title) {
        String line = "";
        try {
            if (inputStream == null) {
                synchronized (BuildMail.class) {
                    if (inputStream == null) {
                        inputStream = resource.getInputStream();
                    }
                }
            }
            if (fileReader == null) {
                synchronized (BuildMail.class) {
                    if (fileReader == null) {
                        fileReader = new BufferedReader(new InputStreamReader(inputStream));
                    }
                }
            }
            if (buffer.length() == 0) {
                while ((line = fileReader.readLine()) != null) {
                    buffer.append(line);
                }
            }
        } catch (Exception e) {
            System.out.println("读取模板失败");
            throw new CustomException(400, "读取模板失败");
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }




    /**
     * 读取邮件模板
     * 替换模板中的信息
     *
     * @param title 内容
     * @return 处理后的模板
     */
    public static String buildContent1(String title) {

        String line = "";
        try {
            if (inputStream1 == null) {
                synchronized (BuildMail.class) {
                    if (inputStream1 == null) {
                        inputStream1 = resource1.getInputStream();
                    }
                }
            }
            if (fileReader1 == null) {
                synchronized (BuildMail.class) {
                    if (fileReader1 == null) {
                        fileReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                    }
                }
            }
            if (buffer1.length() == 0) {
                while ((line = fileReader1.readLine()) != null) {
                    buffer1.append(line);
                }
            }
        } catch (Exception e) {
            System.out.println("读取模板失败");
            throw new CustomException(400, "读取模板失败");
        } finally {
            if (fileReader1 != null) {
                try {
                    fileReader1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream1 != null) {
                try {
                    inputStream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer1.toString(), title);
    }

}
