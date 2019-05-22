package vn.mrlongg71.assignment.Model;

public class Students  {
    int id;
    String tenSV;
    String date;
    int idclass;
    int iduser;

    public Students(int id, String tenSV, String date, int idclass, int iduser) {
        this.id = id;
        this.tenSV = tenSV;
        this.date = date;
        this.idclass = idclass;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdclass() {
        return idclass;
    }

    public void setIdclass(int idclass) {
        this.idclass = idclass;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
}
