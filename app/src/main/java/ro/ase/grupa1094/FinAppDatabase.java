
package ro.ase.grupa1094;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ro.ase.grupa1094.Clase.Cheltuiala;
import ro.ase.grupa1094.Clase.Utilizator;
import ro.ase.grupa1094.Clase.Venit;
import ro.ase.grupa1094.DAO.CheltuialaDAO;
import ro.ase.grupa1094.DAO.UtilizatorDAO;
import ro.ase.grupa1094.DAO.VenitDAO;

@Database(entities = {Venit.class, Cheltuiala.class, Utilizator.class}, version = 3, exportSchema = false)
public abstract class FinAppDatabase extends RoomDatabase {

    private static FinAppDatabase instance;
    public static final String databaseName = "finapp.db";

    public static FinAppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, FinAppDatabase.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract VenitDAO getVenitDAO();
    public abstract CheltuialaDAO getCheltuialaDAO();
    public abstract UtilizatorDAO getUtilizatorDAO();
}
