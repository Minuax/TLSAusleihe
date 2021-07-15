package eu.tlsgi.tlsausleihe.logic.form;

import eu.tlsgi.tlsausleihe.utils.web.WebHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class FormHandler {

    private ArrayList<Form> formArrayList;

    public FormHandler() {
        this.formArrayList = new ArrayList<>();

        try {
            JSONArray formArray = new JSONArray(IOUtils.toString(WebHandler.sendHTTPRequest("http://fjg31.ddns.net/ausleihe/index.php", Collections.singletonList(new BasicNameValuePair("action", "get_schulklassen")))));

            for (int i = 0; i < formArray.length(); i++) {
                JSONObject jsonObject = formArray.getJSONObject(i);
                this.formArrayList.add(new Form(jsonObject.getInt("klassenID"), jsonObject.getString("bezeichnung")));
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        Collections.sort(this.formArrayList, (s1, s2) -> (s1.getBezeichnung()).compareToIgnoreCase((s2.getBezeichnung())));
    }

    public Form getFormByID(int formID){
        Form f = null;
        for(Form form : this.formArrayList){
            if(form.getId() == formID){
                f = form;
            }
        }

        return f;
    }

    public ArrayList<Form> getKlasseArrayList() {
        return formArrayList;
    }
}
