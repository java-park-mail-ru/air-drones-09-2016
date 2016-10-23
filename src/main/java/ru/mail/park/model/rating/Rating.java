package ru.mail.park.model.rating;

/**
 * Created by admin on 23.10.16.
 */
public class Rating {

    private int idRating;
    private int idUser;
    private int rating;

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
