package ro.ase.grupa1094.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ro.ase.grupa1094.Clase.Feedback;

@Dao
public interface FeedbackDAO {

    @Insert
    void insert(Feedback feedback);

    @Update
    void update(Feedback feedback);

    @Delete
    void delete(Feedback feedback);

    @Query("SELECT * FROM Feedback WHERE userId = :userId")
    List<Feedback> getFeedbackByUserId(int userId);

    @Query("SELECT * FROM Feedback WHERE id = :id")
    Feedback getById(long id);
}
