package modelo;

public class Empleado {
    int id;
    String nom, dni, tel, estado, user, pass;

    public Empleado() {
    }

    public Empleado(int id, String nom, String dni, String tel, String estado, String user, String pass) {
        this.id = id;
        this.nom = nom;
        this.dni = dni;
        this.tel = tel;
        this.estado = estado;
        this.user = user;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
}
