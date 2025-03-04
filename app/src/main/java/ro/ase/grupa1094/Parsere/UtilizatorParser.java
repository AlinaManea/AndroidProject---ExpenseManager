package ro.ase.grupa1094.Parsere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Clase.Utilizator;

public class UtilizatorParser {

    private static final String ID = "id";
    private static final String NUME = "nume";
    private static final String PAROLA = "parola";

    public static List<Utilizator> parsareJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareUtilizatori(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Utilizator> parsareUtilizatori(JSONArray jsonArray) throws JSONException {
        List<Utilizator> utilizatori = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            utilizatori.add(parsareUtilizator(jsonArray.getJSONObject(i)));
        }
        return utilizatori;
    }

    private static Utilizator parsareUtilizator(JSONObject jsonObject) throws JSONException {
        long id = jsonObject.getLong(ID);
        String nume = jsonObject.getString(NUME);
        String parola = jsonObject.getString(PAROLA);

        Utilizator utilizator = new Utilizator(nume, parola);
        utilizator.setId(id);

        return utilizator;
    }
}
