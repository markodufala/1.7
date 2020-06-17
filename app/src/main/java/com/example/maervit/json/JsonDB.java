package com.example.maervit.json;

import android.content.Context;

import org.json.*;
import java.io.*;
import java.util.Iterator;

public class JsonDB {

    public JSONObject dbGetRootElement(String FileName, Context context) {

        JSONObject object = null;

        File file = new File(context.getFilesDir(), FileName);
        byte[] bytes = new byte[(int) file.length()];

        try {
            FileInputStream in = new FileInputStream(file);

            try {
                in.read(bytes);
            } finally {
                in.close();
            }
        }
        catch(Exception e){
            errorStr = e.getMessage();
        }
        String contents = new String(bytes);

        try{

            object = new JSONObject(contents);
        }
        catch(JSONException err){
            errorStr = err.getMessage();
        }
        return object;
    }


    public int dbGetInt(String key, JSONObject parent){
        int output = 0;
        try{
            output = parent.getInt(key);
        }catch(Exception e){}
        return output;
    }
    public long dbGetLong(String key, JSONObject parent){
        long output = 0;
        try{
            output = parent.getLong(key);
        }catch(Exception e){}
        return output;
    }
    public String dbGetString(String key, JSONObject parent){
        String output = null;
        try{
            output = parent.getString(key);
        }catch(Exception e){}
        return output;
    }
    public JSONObject dbGetJSONObject(String key, JSONObject parent){
        JSONObject output = null;
        try{
            output = parent.getJSONObject(key);
        }catch(Exception e){}
        return output;
    }
    public JSONObject dbGetJSONObject(int index, JSONArray parent){
        JSONObject output = null;
        try{
            output = parent.getJSONObject(index);
        }catch(Exception e){}
        return output;
    }
    public JSONArray dbGetJSONArray(String key, JSONObject parent){
        JSONArray output = null;
        try{
            output = parent.getJSONArray(key);
        }catch(Exception e){}
        return output;
    }
    public JSONObject dbFindObjectWithValue(JSONArray parent, String key, String value){
        JSONObject object = null;
        try {
            for (int i = 0; i < parent.length(); i++) {
                Iterator<String> keys = parent.getJSONObject(i).keys();

                while (keys.hasNext())
                    if (keys.next() == key) {
                        object = parent.getJSONObject(i);
                        break;
                    }

                if (object != null && object.getString(key) == value) return object;
            }
        }
        catch (Exception e){
            errorStr = e.getMessage();
            return null;
        }
        return null;
    }
    public JSONObject dbFindObjectWithValue(JSONArray parent, String key, int value){
        JSONObject object = null;
        try {
            for (int i = 0; i < parent.length(); i++) {
                Iterator<String> keys = parent.getJSONObject(i).keys();

                while (keys.hasNext())
                    if (keys.next() == key) {
                        object = parent.getJSONObject(i);
                        break;
                    }

                if (object != null && object.getInt(key) == value) return object;
            }
        }
        catch (Exception e){
            errorStr = e.getMessage();
            return null;
        }
        return null;
    }
    public JSONObject dbFindObjectWithValue(JSONArray parent, String key, long value){
        JSONObject object = null;
        try {
            for (int i = 0; i < parent.length(); i++) {
                Iterator<String> keys = parent.getJSONObject(i).keys();


                while (keys.hasNext()) {

                    if (keys.next().equals(key)) {
                        object = parent.getJSONObject(i);
                        break;
                    }
                }

                if (object != null && object.getLong(key) == value) return object;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public void dbAppendJSONObject(String FileName, JSONObject object, JSONArray parent, Context context){

        File file = new File(context.getFilesDir(), FileName);
        byte[] bytes = new byte[(int) file.length()];


        try {
            FileInputStream in = new FileInputStream(file);

            try {
                in.read(bytes);
            } finally {
                in.close();
            }
        }
        catch(Exception e){ }


        String JSONObjString = object.toString(), JSONArrString = parent.toString(), content = new String(bytes);

        int lastElementPos = JSONArrString.lastIndexOf('}');
        JSONArrString = (JSONArrString.substring(0, lastElementPos)) + ",\n" + JSONObjString + JSONArrString.substring(lastElementPos); //Append the object
        content.replace(parent.toString(), JSONArrString); //Replace the old with appended version


        try {
            FileOutputStream in = new FileOutputStream(file);

            try {
                in.write(content.getBytes());
            } finally {
                in.close();
            }
        }
        catch(Exception e){ }


    }
    public void dbRemoveJSONObject(String FileName, JSONObject object, JSONArray parent, Context context){
        File file = new File(context.getFilesDir(), FileName);
        byte[] bytes = new byte[(int) file.length()];


        try {
            FileInputStream in = new FileInputStream(file);

            try {
                in.read(bytes);
            } finally {
                in.close();
            }
        }
        catch(Exception e){ }

        try {
            String JSONObjString = object.toString(), JSONArrString = parent.toString(), content = new String(bytes);


            if (parent.getJSONObject(parent.length() - 1) != object)JSONObjString += ',';
            JSONArrString .replace(JSONObjString, "");
            try {
                FileOutputStream in = new FileOutputStream(file);

                try {
                    in.write(content.getBytes());
                } finally {
                    in.close();
                }
            }
            catch(Exception e){ }



        }
        catch(Exception e){}
    }


    public String dbGetError(){
        return errorStr;
    }

     private String errorStr = "";

}

