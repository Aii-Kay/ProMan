package property.model;

public class Property {
    private int id;
    private String nama;
    private PropertyType type;
    private double harga;
    private PropertyStatus status;
    private String imagePath;

    public Property(int id, String nama, PropertyType type, double harga, PropertyStatus status, String imagePath) {
        this.id = id;
        this.nama = nama;
        this.type = type;
        this.harga = harga;
        this.status = status;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public PropertyType getType() {
        return type;
    }
    
    public void setType(PropertyType type) {
        this.type = type;
    }
    
    public double getHarga() {
        return harga;
        
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }

    public PropertyStatus getStatus() {
        return status;
    }
    
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

}
