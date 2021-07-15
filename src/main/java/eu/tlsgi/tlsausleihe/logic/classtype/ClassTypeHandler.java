package eu.tlsgi.tlsausleihe.logic.classtype;

import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ClassTypeHandler {

    private ArrayList<ClassType> classTypeArrayList;

    public ClassTypeHandler() {
        this.classTypeArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_fachbereiche")))));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ClassType classType = new ClassType(jsonObject.getInt("fachbereichID"), jsonObject.getString("bezeichnung"), jsonObject.getString("raum"));

                this.classTypeArrayList.add(classType);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public ClassType getClassTypeByID(int id) {
        ClassType classType = null;
        for(ClassType c : this.classTypeArrayList){
            if(c.getClassTypeID() == id){
                classType = c;
            }
        }

        return classType;
    }

    public void addClassType(ClassType classType) {
        this.classTypeArrayList.add(classType);
    }

    public ArrayList<ClassType> getClassTypeArrayList() {
        return classTypeArrayList;
    }
}
