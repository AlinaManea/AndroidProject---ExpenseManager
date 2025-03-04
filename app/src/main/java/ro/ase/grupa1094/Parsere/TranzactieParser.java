package ro.ase.grupa1094.Parsere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Tip;
import ro.ase.grupa1094.Clase.Tranzactie;

public class TranzactieParser {

        private static final String TIP = "tip";
        private static final String DESCRIERE = "descriere";
        private static final String VALOARE = "valoare";

        public static List<Tranzactie> parsareJson(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                return parsareTranzactii(jsonArray);
            } catch (JSONException e) {
                throw new RuntimeException("Eroare la parsarea JSON-ului", e);
            }
        }

        private static List<Tranzactie> parsareTranzactii(JSONArray jsonArray) throws JSONException {
            List<Tranzactie> tranzactii = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                tranzactii.add(parsareTranzactie(jsonArray.getJSONObject(i)));
            }
            return tranzactii;
        }

        private static Tranzactie parsareTranzactie(JSONObject jsonObject) throws JSONException {
            String tipString = jsonObject.getString(TIP);
            Tip tip = Tip.valueOf(tipString);
            String descriere = jsonObject.getString(DESCRIERE);
            int valoare = jsonObject.getInt(VALOARE);
            return new Tranzactie(tip, descriere, valoare);
        }
    }

