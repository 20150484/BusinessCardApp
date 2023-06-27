package com.example.businesscard;

import static android.content.ContentValues.TAG;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import java.io.BufferedReader;
import java.io.DataOutputStream;

public class OCRGeneralAPIDemo {
    public static CardModel OcrGeneralAPIDemo(String base64) {
        String apiURL = "https://dky6vt1x0f.apigw.ntruss.com/custom/v1/22882/feeb2cffff6d3dcab6486066b91be0a521b17e94b9e9959eb525e99a452e6f72/document/name-card";
        String secretKey = "enFVZ2VGdkh1cUNVandvYnNjTFlidldwWXJsU2dmbW4=";
        CardModel model = new CardModel();
            try {
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setUseCaches(false);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                con.setRequestProperty("X-OCR-SECRET", secretKey);

                JSONObject json = new JSONObject();
                json.put("version", "V2");
                json.put("requestId", UUID.randomUUID().toString());
                json.put("timestamp", System.currentTimeMillis());
                JSONObject image = new JSONObject();
                image.put("format", "png");

                image.put("data", base64);
                image.put("name", "demo");
                JSONArray images = new JSONArray();
                images.put(image);
                json.put("images", images);
                String postParams = json.toString();

                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
//            Log.i(TAG, String.valueOf(response));

//                System.out.println(mkcard(String.valueOf(response)));
                model = mkcard(String.valueOf(response));
//            JSONArray jsonArr = (JSONArray) new JSONParser().parse(jsonObj.get("images").toString());
//            System.out.println(text.get("text"));
            } catch (Exception e) {
                System.out.println(e);
            }
            return model;
    }

    public static CardModel mkcard(String response) throws JSONException {
        CardModel model = new CardModel();
        JSONObject jsonObj = new JSONObject(String.valueOf(response));
        JSONArray jsonArr = (JSONArray) jsonObj.get("images");
        JSONObject jpg = (JSONObject) jsonArr.get(0);
        JSONObject nameCard = (JSONObject) jpg.get("nameCard");
        JSONObject result = (JSONObject) nameCard.get("result");

        if (result.toString().contains("name")) {
            JSONArray name = (JSONArray) result.get("name");
            JSONObject nameText = (JSONObject) name.get(0);
            model.name = (String) nameText.get("text");
        }

        if(result.toString().contains("company")) {
            JSONArray company = (JSONArray) result.get("company");
            JSONObject companyText = (JSONObject) company.get(0);
            model.company = (String) companyText.get("text");
        }

        if(result.toString().contains("department")) {
            JSONArray department = (JSONArray) result.get("department");
            JSONObject departmentText = (JSONObject) department.get(0);
            model.department = (String) departmentText.get("text");
        }

        if(result.toString().contains("address")) {
            JSONArray address = (JSONArray) result.get("address");
            JSONObject addressText = (JSONObject) address.get(0);
            model.address = (String) addressText.get("text");
        }

        if(result.toString().contains("position")) {
            JSONArray position = (JSONArray) result.get("position");
            JSONObject positionText = (JSONObject) position.get(0);
            model.position = (String) positionText.get("text");
        }

        if(result.toString().contains("mobile")) {
            JSONArray mobile = (JSONArray) result.get("mobile");
            JSONObject mobileText = (JSONObject) mobile.get(0);
            model.mobile = (String) mobileText.get("text");
        }

        if(result.toString().contains("tel")) {
            JSONArray tel = (JSONArray) result.get("tel");
            JSONObject telText = (JSONObject) tel.get(0);
            model.tel = (String) telText.get("text");
        }

        if(result.toString().contains("fax")) {
            JSONArray fax = (JSONArray) result.get("fax");
            JSONObject faxText = (JSONObject) fax.get(0);
            model.fax = (String) faxText.get("text");
        }

        if(result.toString().contains("email")) {
            JSONArray email = (JSONArray) result.get("email");
            JSONObject emailText = (JSONObject) email.get(0);
            model.email = (String) emailText.get("text");
        }

        if(result.toString().contains("homepage")) {
            JSONArray homepage = (JSONArray) result.get("homepage");
            JSONObject homepageText = (JSONObject) homepage.get(0);
            model.homepage = (String) homepageText.get("text");
        }

        return model;
    }
}