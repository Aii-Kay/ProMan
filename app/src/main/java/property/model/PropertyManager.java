package property.model;

import java.io.*;
import java.util.*;


public class PropertyManager {
    private List<Property> listProperti = new ArrayList<>();
    private final String FILE_PATH = "app/src/main/java/property/data/properties.csv";

    public PropertyManager() {
        listProperti = new ArrayList<>();
        loadProperties();
    }
    
    public void addProperty(Property p) {
        listProperti.add(p);
        saveProperties();
    }

    public List<Property> getAvailableProperties() {
        return listProperti.stream()
                .filter(p -> p.getStatus() == PropertyStatus.TERSEDIA)
                .toList();
    }
    
    public List<Property> getAllProperties() {
        return listProperti;
    }

    public Property cariById(int id) {
        for (Property p : listProperti) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void markAsSold(int id) {
        Property p = cariById(id);
        if (p != null && p.getStatus() == PropertyStatus.TERSEDIA) {
            p.setStatus(PropertyStatus.TERJUAL);
            saveProperties();
        } else {
            System.out.println("Properti dengan ID " + id + " tidak ditemukan.");
        }
    }

    public void deleteProperty(int id) {
        Property p = cariById(id);
        if (p != null) {
            listProperti.remove(p);
            saveProperties();
            System.out.println("Properti dengan ID " + id + " berhasil dihapus.");
        } else {
            System.out.println("Properti tidak ditemukan.");
        }
    }


    public void loadProperties() {
        listProperti.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String nama = parts[1];
                    PropertyType type = PropertyType.valueOf(parts[2].toUpperCase());
                    double harga = Double.parseDouble(parts[3]);
                    PropertyStatus status = PropertyStatus.valueOf(parts[4].toUpperCase());

                    Property p = new Property(id, nama, type, harga, status);
                    listProperti.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data properti: " + e.getMessage());
        }
    }

    public void saveProperties() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Property p : listProperti) {
                String line = p.getId() + "," + p.getNama() + "," + p.getType() + "," + p.getHarga() + "," + p.getStatus();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data properti: " + e.getMessage());
        }
    }
}
