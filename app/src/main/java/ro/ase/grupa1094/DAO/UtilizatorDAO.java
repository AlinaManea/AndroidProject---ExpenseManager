package ro.ase.grupa1094.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ro.ase.grupa1094.Clase.Utilizator;

@Dao
public interface UtilizatorDAO {

    @Insert
    void insertUtilizator(Utilizator utilizator);

    @Query("SELECT * FROM Utilizatori WHERE nume = :nume AND parola = :parola")
    Utilizator getUtilizatorByCredentials(String nume, String parola);

    @Query("SELECT * FROM Utilizatori")
    List<Utilizator> getAllUtilizatori();

    @Query("DELETE FROM Utilizatori WHERE id = :id")
    void deleteUtilizatorById(long id);
}
