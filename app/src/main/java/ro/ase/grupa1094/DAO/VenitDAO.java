package ro.ase.grupa1094.DAO;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ro.ase.grupa1094.Categoriee;
import ro.ase.grupa1094.Clase.Venit;

@Dao
public interface VenitDAO {

    @Insert
    void insertVenit(Venit venit);

    @Query("SELECT * FROM Venituri")
    List<Venit> getVenituri();

    @Query("SELECT * FROM Venituri WHERE id = :id")
    List<Venit> getVenituriById(Long id);

    @Query("SELECT * FROM Venituri WHERE userId = :userId")
    List<Venit> getVenituriByUserId(long userId);
    @Query("SELECT * FROM Venituri WHERE categorie = :categorie")
    List<Venit> getVenituriByCategorie(Categoriee categorie);

   @Update
    void updateVenit(Venit venit);
    @Query("UPDATE Venituri SET valoare = :valoareNoua,  descriere = :descriereNoua, categorie = :categorieNoua WHERE id = :id")
    void updateVenit(Long id, Categoriee categorieNoua, String descriereNoua,double valoareNoua);

    @Query("DELETE FROM Venituri WHERE id = :id")
    void deleteVenitById(Long id);

    @Query("SELECT * FROM Venituri WHERE valoare > :minValoare")
    List<Venit> getVenituriMaiMariDecat(double minValoare);
}

