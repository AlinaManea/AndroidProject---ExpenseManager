package ro.ase.grupa1094.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ro.ase.grupa1094.Categorie;
import ro.ase.grupa1094.Clase.Cheltuiala;

@Dao
public interface CheltuialaDAO {

    @Insert
    void insertCheltuiala(Cheltuiala cheltuiala);

    @Query("SELECT*FROM Cheltuieli")
    List<Cheltuiala> getCheltuieli();

    @Query("SELECT * FROM Cheltuieli WHERE id = :id")
    List<Cheltuiala> getCheltuieliById(Long id);

    @Query("SELECT * FROM Cheltuieli WHERE categorie = :categorie")
    List<Cheltuiala> getCheltuieliByCategorie(Categorie categorie);

    @Query("SELECT * FROM Cheltuieli WHERE userId = :idUser")
    List<Cheltuiala> getCheltuieliByUserId(long idUser);

    @Update
    void updateCheltuiala(Cheltuiala cheltuiala);
    @Query("UPDATE Cheltuieli SET valoare = :valoareNoua, descriere = :descriereNoua, categorie = :categorieNoua WHERE id = :id")
    void updateCheltuiala(Long id, Categorie categorieNoua, String descriereNoua,double valoareNoua);



    @Query("DELETE FROM Cheltuieli WHERE id = :id")
    void deleteCheltuialaById(Long id);

    @Query("SELECT * FROM Cheltuieli WHERE valoare < :maxValoare")
    List<Cheltuiala> getCheltuieliMaiMiciDecat(double maxValoare);


}

