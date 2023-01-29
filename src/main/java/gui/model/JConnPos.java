package gui.model;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JConnPos {
    private static String TOKEN_KEY = "X-Joomla-Token";
    private static String TYPE_KEY = "Content-Type";
    private static String TYPE_VALUE = "application/json;charset=UTF-8";
    private static String LANGUAGE ="\"language\":\"*\"";
    private String tokenValue;
    private String siteName;

    private String jsonBody ;

    public JConnPos(String tokenValue, String siteName) {
        this.tokenValue = tokenValue;
        this.siteName =siteName;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public void goPost(){//Это отправляет пост запрос
        try {
            URL url = new URL(siteName+"/api/index.php/v1/content/articles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Language", "ru-RU");

            conn.setRequestProperty(TYPE_KEY,TYPE_VALUE);
            conn.setRequestProperty(TOKEN_KEY,tokenValue);
            conn.setDoOutput(true);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            out.write(jsonCreater());
            out.flush();
            out.close();
            System.out.println(conn.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String jsonCreater(){ //Этот метод создает строку JSON
        StringBuilder sb = new StringBuilder(jsonBody);
        System.out.println(sb);
        return sb.toString();
    }

    public Integer selectCatId(){

        try {
            URL url = new URL(siteName+"/api/index.php/v1/content/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty(TYPE_KEY,TYPE_VALUE);
            conn.setRequestProperty(TOKEN_KEY,tokenValue);
            int responseCode = conn.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String [] arrGr = response.toString().split("\"attributes\":\\{");
                ArrayList<String> arrClear = new ArrayList<>();
                for(int i = 0; i<arrGr.length;i++){
                    if(i!=0) {
                        //System.out.println(arrGr[i]);
                        String [] temp = arrGr[i].split(",");
                        arrClear.addAll(Arrays.asList(temp));
                    }
                }
                Model.mapCatefory = hashMapCreater(clerDublId(arrClear));
                Model.mapCatefory.forEach((key, value) ->{
                    System.out.println(key+"---"+value );
                });

                // print result
                //System.out.println(response.toString());
            } else {
                System.out.println("GET request did not work.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 5;

    }
    public ArrayList<String> clerDublId(ArrayList<String> list){
        ArrayList<String> returnList = new ArrayList<>();
        for(int i = 0; i < list.size();i++){
            if(list.get(i).contains("\"id\":\"")){
                list.remove(i);
            }
        }
        returnList = (ArrayList<String>) list.clone();
        return returnList;
    }
    public Map<Integer,String> hashMapCreater(ArrayList<String> list){
        Map<Integer,String> map = new HashMap<>();
        Integer counter = 0;
        Integer tempInt = 0;
        String tempStr = "";
        for(int i = 0; i < list.size();i++){

            if(list.get(i).contains("\"id\":")){
                tempInt = Integer.parseInt(list.get(i).substring(5,list.get(i).length()));
                counter++;
            }
            if (list.get(i).contains("\"title\":\"")){
                tempStr = list.get(i).substring(9,list.get(i).length()-1);
                counter++;
            }
            if (counter%2 == 0 & counter != 0){
                map.put(tempInt,tempStr);
            }
        }
        for(Map.Entry<Integer,String> entry : map.entrySet()){
            Model.arr.add(entry.getValue());
        }
        return map;
    }
}
