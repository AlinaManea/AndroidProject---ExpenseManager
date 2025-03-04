package ro.ase.grupa1094.Parsere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Categorie;
import ro.ase.grupa1094.Clase.Cheltuiala;

public class CheltuialaParser {
    private static final String CATEGORIE = "categorie";
    private static final String DESCRIERE = "descriere";
    private static final String VALOARE = "valoare";
    private static final String USER_ID = "userId";

    public static List<Cheltuiala> parsareJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareCheltuieli(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Cheltuiala> parsareCheltuieli(JSONArray jsonArray) throws JSONException {
        List<Cheltuiala> cheltuieli = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            cheltuieli.add(parsareCheltuiala(jsonArray.getJSONObject(i)));
        }
        return cheltuieli;
    }

    private static Cheltuiala parsareCheltuiala(JSONObject jsonObject) throws JSONException {
        String categorieString = jsonObject.getString(CATEGORIE);
        Categorie categorie = Categorie.valueOf(categorieString);
        String descriere = jsonObject.getString(DESCRIERE);
        double valoare = jsonObject.getDouble(VALOARE);
        long userId = jsonObject.getLong(USER_ID);

        Cheltuiala cheltuiala = new Cheltuiala(categorie, descriere, valoare);
        cheltuiala.setUserId(userId);

        return cheltuiala;
    }
}
