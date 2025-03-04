package ro.ase.grupa1094.Parsere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Categoriee;
import ro.ase.grupa1094.Clase.Venit;

public class VenitParser {
    private static final String CATEGORIE = "categorie";
    private static final String DESCRIERE = "descriere";
    private static final String VALOARE = "valoare";
    private static final String USER_ID = "userId";

    public static List<Venit> parsareJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareVenituri(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Venit> parsareVenituri(JSONArray jsonArray) throws JSONException {
        List<Venit> venituri = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            venituri.add(parsareVenit(jsonArray.getJSONObject(i)));
        }
        return venituri;
    }

    private static Venit parsareVenit(JSONObject jsonObject) throws JSONException {
        String categorieString = jsonObject.getString(CATEGORIE);
        Categoriee categorie = Categoriee.valueOf(categorieString);
        String descriere = jsonObject.getString(DESCRIERE);
        double valoare = jsonObject.getDouble(VALOARE);
        long userId = jsonObject.getLong(USER_ID);

        Venit venit = new Venit(categorie, descriere, valoare);
        venit.setUserId(userId);

        return venit;
    }
}
