package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Json2Menu {

    public JSONObject jsonObject, temp;

    public String menuName, menuDescription, menuPrice, menuImage;
    public int menuID;

    public Json2Menu(){}


    public Json2Menu (JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            temp= json.optJSONArray("Product").optJSONObject(0);
            menuName= temp.optString("prodDescription");
            menuPrice = json.optString("prodPrice");
            menuDescription = json.optString("Description");
            menuID = json.optInt("id");
            menuImage = json.optString("image");
        }
        else{
            menuName = "";
            menuDescription = "";
            menuPrice = "";
            menuImage = "";
            menuID = 0;
        }
    }

    Boolean isNull(){
        if (jsonObject == null)
            return true;
        else
            return false;
    }

}
