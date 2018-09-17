package com.actuator.config;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class ChatbotSend {
     //釘釘機器人
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=xxxxxxxxxxxxxxxxxxx";

    public static void send(String msg) throws Exception{

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\":\""+msg+"\"}, \r\n" + 
        		"    \"at\": {\r\n" + 
        		"        \"atMobiles\": [\r\n" + 
        		"            \"158xxxx3541\", \r\n" + 
        		//"            \"152xxxx3694\", \r\n" + 
        		"        ], \r\n" + 
        		"        \"isAtAll\":false\r\n" + 
        		"    }}";
      
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
}