package ro.ase.grupa1094.Clase;

import java.io.Serializable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Feedback", foreignKeys = @ForeignKey(entity = Utilizator.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
public class Feedback implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String mesaj;
    private int rating;
    private String data;
    private int userId;

    public Feedback(String mesaj,  String data) {
        this.mesaj = mesaj;
        this.data = data;
        this.userId = 1;
        this.rating = 0;
    }
    public Feedback(String mesaj,  String data, int rating) {
        this.mesaj = mesaj;
        this.data = data;
        this.userId = 1;
        this.rating = rating;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId =userId;}

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", mesaj='" + mesaj + '\'' +
                ", rating=" + rating +
                ", data='" + data + '\'' +
                ", userId=" + userId + '}';
    }
}
