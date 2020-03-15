
package com.qs.tririga.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qs.tririga.chatbot.TririgaChatbotService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author karuppusamykaleeswaran
 */
public class MoveRequestService {
  
    public static String createUser(JsonObject args) throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClientBuilder.create().disableCookieManagement().build();   
            HttpPost request = new HttpPost("http://107.15.110.77/oslc/so/testConsultantCF");
            String auth = "kalee" + ":" + "password";
            byte[] encodedAuth = Base64.encodeBase64(
            auth.getBytes(StandardCharsets.ISO_8859_1));
            String authHeader = "Basic " + new String(encodedAuth);
            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            Map<String, String> colours = new HashMap<>();
             colours.put("spi:action", "Create Draft");
             colours.put("spi:triFirstNameTX", args.get("firstName").getAsString());
             colours.put("spi:triLastNameTX", args.get("lastName").getAsString());
             colours.put("spi:triUserNameTX", args.get("username").getAsString());
             colours.put("spi:triCreateUserBL", "TRUE");
             Gson gson = new Gson();
            String output = gson.toJson(colours);
            HttpEntity builder = EntityBuilder.create().setText(output).setContentType(ContentType.APPLICATION_JSON).build();
            request.setEntity(builder);
            CloseableHttpResponse httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity);
            return response;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ex) {
                    Logger.getLogger(TririgaChatbotService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
        public static String getMoveRequest() throws IOException {
        CloseableHttpClient httpClient = null;
            httpClient = HttpClientBuilder.create().disableCookieManagement().build();   
            HttpGet request = new HttpGet("http://107.15.110.77/oslc/spq/testMoveQC?oslc.select=*");
            String auth = "kalee" + ":" + "password";
            byte[] encodedAuth = Base64.encodeBase64(
            auth.getBytes(StandardCharsets.ISO_8859_1));
            String authHeader = "Basic " + new String(encodedAuth);
            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            CloseableHttpResponse httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity);
            Gson gson = new Gson();
            JsonObject response1 = new JsonObject();
            Map<String,Object> map = gson.fromJson(response, Map.class);
            String key  = null;
            ArrayList list = (ArrayList) map.get("rdfs:member");
            if(list!=null){
              Map map1 = (Map) list.get(list.size()-1);
              if(map1!=null){
                  key = (String) map1.get("spi:triIdTX");
              }
            }
            return key;
    }
    
    
    public static JsonObject createMoveRequest(JsonObject args) throws IOException {
        CloseableHttpClient httpClient = null;
            httpClient = HttpClientBuilder.create().disableCookieManagement().build();   
            HttpPost request = new HttpPost("http://107.15.110.77/oslc/so/testMoveCF");
            String auth = "kalee" + ":" + "password";
            byte[] encodedAuth = Base64.encodeBase64(
            auth.getBytes(StandardCharsets.ISO_8859_1));
            String authHeader = "Basic " + new String(encodedAuth);
            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            Map<String, String> colours = new HashMap<>(); 
             colours.put("spi:action", "Create Draft");
             colours.put("spi:triRequestedForTX", args.get("requestedFor").getAsString());
             colours.put("spi:triRequestedByTX", args.get("requestedBy").getAsString());
             colours.put("spi:triBuildingTX", args.get("building").getAsString());
             colours.put("spi:triCustomerOrgTX",args.get("orgName").getAsString() );
             colours.put("spi:triEffectiveFromDA", args.get("fromDate").getAsString() );
             Gson gson = new Gson();
            String output = gson.toJson(colours);
            HttpEntity builder = EntityBuilder.create().setText(output).setContentType(ContentType.APPLICATION_JSON).build();
            request.setEntity(builder);
            CloseableHttpResponse httpResponse = httpClient.execute(request);
             HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity);
            JsonObject response1 = new JsonObject();
            
            if(httpResponse.getStatusLine().getStatusCode()==201){
                response1.addProperty("key", getMoveRequest());
            }
            response1.addProperty("code", httpResponse.getStatusLine().getStatusCode());
            return response1;     
    }  
}
