package com.liumapp.demo.certificate.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import model.BaseRequest;

/**
 * @author liumapp
 * @file SendHttpUtils.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/3
 */
public class SendHttpUtils {
    private static  int invokeCount = 0; // 调用次数
    private static  String bizKey = "Azg9PX"; //bizKey
    public static String sendRequest(String url,BaseRequest obj,int invokeTime,int timeout){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        //计算idCode
        String json = gson.toJson(obj);
        Map<String, String> params = gson.fromJson(json, type);
        String sign = MD5SignUtil.getMd5Sign(params,bizKey);
        params.put("sign", sign);
        String result = sendRequest(url, params, invokeTime, timeout);
        return result;
    }

    public static String sendRequest(String url, Map<String, String> params, int invokeTime, int timeout) {
        String result = null;
        while(invokeCount < invokeTime)
        {
            byte[] resultByte;
            try {
                resultByte = sendPostByURLConnection(url, params,timeout);
                result = new String(resultByte);
                if(null != resultByte)
                {
                    Type type = new TypeToken<Map<String, String>>(){}.getType();
                    Map<String, String> map = new GsonBuilder().create().fromJson(result, type);
                    if(!"0".equals(map.get("resultCode")))
                    {
                        invokeCount ++ ;
                    }else
                    {
                        break;
                    }
                }else
                {
                    invokeCount ++ ;
                }
            } catch (Exception e) {
                invokeCount ++ ;
                e.printStackTrace();
            }
        }
        return result;
    }

    private static  byte[] sendPostByURLConnection(String path,
                                                   Map<String, String> params,int timeout) throws Exception {

        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        byte[] entitydata = sb.toString().getBytes();// 得到实体的二进制数据
        URL url = new URL(path);
        HttpURLConnection conn = null;
        if (url.getProtocol().toLowerCase().equals("https")) {
            //信任所有证书
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
            https.setHostnameVerifier(DO_NOT_VERIFY);
            conn = https;
        }else{
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod("POST");
        conn.setConnectTimeout(timeout);
        /* 设置缓存 */
        conn.setUseCaches(false);
        conn.setDefaultUseCaches(false);
        conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
        conn.setDoInput(true);

        // 至少要设置的两个请求头
        // Content-Type: application/x-www-form-urlencoded //内容类型
        // Content-Length: 38 //实体数据的长度
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");//
        // 长度是实体的二进制长度
        conn.setRequestProperty("Content-Length",
                String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();

        if (conn.getResponseCode() == 200) {
            try {
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    byte[] buf = new byte[64];
                    int len;
                    // 集中到一起然后再集体写入
                    ByteArrayOutputStream bouf = new ByteArrayOutputStream();
                    while (true) {
                        len = inputStream.read(buf);
                        if (len == -1) {
                            break;
                        }
                        bouf.write(buf, 0, len);
                    }
                    return bouf.toByteArray();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(conn.getResponseCode() == 404){
            System.out.println("please check CA request URL : "+path +" \t " + conn.getResponseCode());
        }else
        {
            System.out.println("please check CA Server :" + conn.getResponseCode());
        }
        return null;

    }





    /**
     * 设置不验证主机
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };



    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
