package Restaurant.domain;

public class Restaurant {

    private int id;
    private String name;
    private int postal_code;
    private String adress;


    public Restaurant(String name, int postal_code, String adress) {
        this.name = name;
        this.postal_code = postal_code;
        this.adress = adress;
    }

    public Restaurant() {

    }


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name){this.name = name;}

    public int getPostal_code() {return postal_code;}

    public void setPostal_code(int postal_code) {this.postal_code = postal_code;}

    public String getAdress() {return adress;}

    public void setAdress(String adress) {this.adress = adress;}
}
