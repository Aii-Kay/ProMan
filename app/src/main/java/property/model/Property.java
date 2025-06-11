package property.model; // selesai

public class Property {
    private int id;
    private String nama;
    private PropertyType type;
    private double harga;
    private PropertyStatus status;

    public PropertyStatus getStatus() {
        return status;
    }
    
    public void setStatus(PropertyStatus status) {
        this.status = status;
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

}
