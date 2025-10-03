package DataBuild;

import pojo.Location;
import pojo.MapPayload;

import java.util.ArrayList;
import java.util.Arrays;

public class TestDataBuild{
    public MapPayload AddPlacePayload(String name, String lang, String add){
        MapPayload mp = new MapPayload();
        mp.setAccuracy(50);
        mp.setAddress(add);
        mp.setLanguage(lang);
        mp.setName(name);
        Location l = new Location();
        l.setLat(20.123);
        l.setLng(21.123);
        mp.setLocation(l);
        mp.setPhone_number("1234567890");
        mp.setWebsite("www.google.com");
        mp.setTypes(new ArrayList<>(Arrays.asList("shoe park","shop")));
        return mp;
    }

    public String deletePlacePayload(String place_id){
        return "{\\r\\n    \\\"place_id\\\":\\\""+place_id+"\\\"\\r\\n}\\r\\n";
    }
}