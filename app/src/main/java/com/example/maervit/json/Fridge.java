//=======================================
//
//  Fridge.java, (c) Team MaRViT 2020
//
//  This class is implementation of fridge
//  It's also sitting between JDB and fridge
//  Activity
//
//=======================================



//=======================================
//  IMPORTANT: TODO LIST:
//  Line 33: implement error handling
//  MainActivity: implement
//
//
//
//=======================================
package com.example.maervit.json;

import android.content.Context;
import org.json.*;

import java.text.SimpleDateFormat;


public class Fridge {




    public Fridge(Context context){

        JDB = new JsonDB();
        try {
        dbUserRoot = new JSONObject("{\n" +
                "    \"user\": {\n" +
                "            \"id\": 42069,\n" +
                "            \"fname\": \"Demo\",\n" +
                "            \"lname\": \"McDemovic\",\n" +
                "            \"dateformat\": \"dd/MM/yyyy\"    \n" +
                "        },\n" +
                "\n" +
                "    \"fridge\":   [\n" +
                "                    {\n" +
                "                        \"id\": 0,\n" +
                "                        \"adddate\": \"11/9/2000\",\n" +
                "                        \"expdate\": \"11/9/2020\"\n" +
                "                    },\n" +
                "\n" +
                "                    {\n" +
                "                        \"id\": 1,\n" +
                "                        \"adddate\": \"2/4/2019\",\n" +
                "                        \"expdate\": \"11/9/2020\"\n" +
                "                    }\n" +
                "    ]\n" +
                "}");//JDB.dbGetRootElement("user.json", context);
        dbProductsRoot = new JSONObject("{\n" +
                "   \"content\": [\n" +
                "\n" +
                "        {\n" +
                "            \"product_id\": 0,\n" +
                "            \"product_name\": \"Lorem ipsum\",\n" +
                "            \"country_origin\": \"dolor sit amet\",\n" +
                "            \"desc\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\",\n" +
                "            \"co2footprint\":6502\n" +
                "        },\n" +
                "\n" +
                "\n" +
                "        {\n" +
                "            \"product_id\": 1,\n" +
                "            \"product_name\": \"Lorem ipsum\",\n" +
                "            \"country_origin\": \"dolor sit amet\",\n" +
                "            \"desc\": \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat\",\n" +
                "            \"co2footprint\":65186\n" +
                "        }\n" +
                "\n" +
                "    ]\n" +
                "}");//JDB.dbGetRootElement("products.json",context);

        }catch(Exception e){}

        JSONObject userData = JDB.dbGetJSONObject("user",dbUserRoot);
        fridgeData = JDB.dbGetJSONArray("fridge", dbUserRoot);
        productData = JDB.dbGetJSONArray("content", dbProductsRoot);
        dateFormat = JDB.dbGetString("dateformat", userData);
        this.context = context;
    }


    public FridgeItem[] GetItemsInFridge() {

        FridgeItem[] items = new FridgeItem[fridgeData.length()];
        try {
            for (int i = 0; i < items.length; i++) {
                items[i] = new FridgeItem();
                items[i].uObject = JDB.dbGetJSONObject(i , fridgeData);
                long productID = JDB.dbGetLong("id", items[i].uObject);
                JSONObject object = JDB.dbFindObjectWithValue(productData, "product_id", productID);

                System.out.println(object);

                items[i].dateAdded = new SimpleDateFormat(dateFormat).parse(JDB.dbGetString("adddate", items[i].uObject));
                items[i].dateExpiration = new SimpleDateFormat(dateFormat).parse(JDB.dbGetString("expdate", items[i].uObject));

                items[i].productName = JDB.dbGetString("product_name",object);
                items[i].countryOrigin = JDB.dbGetString("country_origin",object);
                items[i].description = JDB.dbGetString("desc",object);
                items[i].CO2Footprint = JDB.dbGetInt("co2footprint",object);

                System.out.println(items[i].dateAdded);

            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new FridgeItem[1];
        }
        return items;
    }


    public void AddItem(Long id, String DateAdded, String EXPDate ) {
       String JSON = "";


       JSON += "{\n";
       JSON += "    \"id\": " + id.toString() + ",\n";
       JSON += "    \"adddate\": " + DateAdded  + ",\n";
       JSON += "    \"expdate\": " + EXPDate + ",\n";
       JSON += "}\n";

       try {
           JDB.dbAppendJSONObject("src/main/json/user.json", new JSONObject(JSON), fridgeData, context);
       }
       catch(Exception e){}

        dbUserRoot = JDB.dbGetRootElement("src/main/json/user.json", context); //Reload user data

    }

    public void RemoveItem(FridgeItem item) {
        JDB.dbRemoveJSONObject("src/main/json/user.json", item.uObject,fridgeData, context);
        dbUserRoot = JDB.dbGetRootElement("src/main/json/user.json", context); //Reload user data
    }

    private JsonDB JDB;
    private JSONObject dbUserRoot, dbProductsRoot;
    private String dateFormat;
    public JSONArray fridgeData, productData;
    private Context context;








}


