package property;

import property.model.*;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserManager userManager = new UserManager();
        PropertyManager propertyManager = new PropertyManager();

        boolean running = true;
        while (running) {
            System.out.println("===== SELAMAT DATANG =====");
            System.out.println("1. Login\n2. Register\n3. Keluar");
            System.out.print("Pilih opsi: ");

            String pilihanStr = input.nextLine();
            int pilihan;
            try {
                pilihan = Integer.parseInt(pilihanStr);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harus berupa angka.");
                continue;
            }

            switch (pilihan) {
                case 1 -> handleLogin(input, userManager, propertyManager);
                case 2 -> handleRegister(input, userManager);
                case 3 -> {
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    running = false;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
        input.close();
    }

    private static void handleRegister(Scanner input, UserManager userManager) {
        System.out.print("Masukkan role (Admin/Buyer): ");
        String role = input.nextLine();
        System.out.print("Masukkan username: ");
        String username = input.nextLine();
        System.out.print("Masukkan password: ");
        String password = input.nextLine();

        if (role.equalsIgnoreCase("Admin")) {
            userManager.registerUser(new Admin(username, password));
        } else if (role.equalsIgnoreCase("Buyer")) {
            userManager.registerUser(new Buyer(username, password));
        } else {
            System.out.println("Role tidak valid. Hanya Admin atau Buyer yang diperbolehkan.");
            return;
        }
        System.out.println("Registrasi berhasil! Silakan login.");
    }

    private static void handleLogin(Scanner input, UserManager userManager, PropertyManager propertyManager) {
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        if (userManager.login(username, password)) {
            User currentUser = Session.getCurrentUser();
            System.out.println("\nLogin berhasil sebagai " + currentUser.getRole());

            if (currentUser instanceof Admin) {
                adminMenu(input, propertyManager);
            } else if (currentUser instanceof Buyer) {
                buyerMenu(input, propertyManager);
            } 
            System.exit(0);
        } else if (userManager .getAllUsers().stream()
                .noneMatch(user -> user.getUsername().equals(username))) {
            System.out.println("User tidak terdaftar. Silakan registrasi terlebih dahulu.");
        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }
    }

    private static void adminMenu(Scanner input, PropertyManager pm) {
        while (true) {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Tambah Properti");
            System.out.println("2. Lihat Semua Properti");
            System.out.println("3. Hapus Properti");
            System.out.println("4. Logout");
            System.out.print("Pilih: ");
            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
                continue;
            }

            switch (choice) {
                case 1 -> tambahProperti(input, pm);
                case 2 -> tampilkanProperti(pm.getAllProperties());
                case 3 -> deleteProperty(input, pm);
                case 4 -> {
                    System.out.println("Logout...");
                    Session.clear();
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void buyerMenu(Scanner input, PropertyManager pm) {
        while (true) {
            System.out.println("\n--- MENU BUYER ---");
            System.out.println("1. Lihat Properti");
            System.out.println("2. Beli Properti");
            System.out.println("3. Logout");
            System.out.print("Pilih: ");
            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
                continue;
            }

            switch (choice) {
                case 1 -> tampilkanProperti(pm.getAllProperties());
                case 2 -> beliProperti(input, pm);
                case 3 -> {
                    System.out.println("Logout...");
                    Session.clear();
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahProperti(Scanner input, PropertyManager pm) {
        try {
            System.out.print("ID Properti: ");
            int id = Integer.parseInt(input.nextLine());

            if (pm.cariById(id) != null) {
                System.out.println("Properti dengan ID ini sudah ada. Gunakan ID lain.");
                return;
            }

            System.out.print("Nama Properti: ");
            String nama = input.nextLine();

            System.out.print("Path gambar properti: ");
            String imagePath = input.nextLine();

            Path sourcePath = Paths.get(imagePath);
            if (!Files.exists(sourcePath)) {
                System.out.println("File gambar tidak ditemukan.");
                return;
            }

            String fileName = sourcePath.getFileName().toString();

            Path imagesDir = Paths.get("images");
            if (!Files.exists(imagesDir)) {
                Files.createDirectories(imagesDir);
            }

            Path targetPath = imagesDir.resolve(fileName);
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);


            System.out.println("Tipe Properti:");
            for (PropertyType type : PropertyType.values()) {
                System.out.println("- " + type);
            }
            System.out.print("Masukkan tipe: ");
            PropertyType type = PropertyType.valueOf(input.nextLine().toUpperCase());

            System.out.print("Harga: ");
            double harga = Double.parseDouble(input.nextLine());

            Property p = new Property(id, nama, type, harga, PropertyStatus.TERSEDIA, imagePath);
            pm.addProperty(p);
            System.out.println("Properti berhasil ditambahkan.");

        } catch (IllegalArgumentException e) {
            System.out.println("Tipe properti tidak valid.");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan input. Coba lagi.");
        }
    }

    private static void deleteProperty(Scanner input, PropertyManager pm) {
        List<Property> all = pm.getAllProperties();
        if (all.isEmpty()) {
            System.out.println("Belum ada properti untuk dihapus.");
            return;
        }

        tampilkanProperti(all);

        System.out.print("Masukkan ID properti yang ingin dihapus: ");
        int id;
        try {
            id = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID tidak valid.");
            return;
        }

        pm.deleteProperty(id);
    }


    private static void tampilkanProperti(List<Property> list) {
        System.out.println("\n--- DAFTAR PROPERTI ---");
        for (Property p : list) {
            System.out.println("ID: " + p.getId()
                    + " | Nama: " + p.getNama()
                    + " | Tipe: " + p.getType()
                    + " | Harga: " + p.getHarga()
                    + " | Status: " + (p.getStatus() == PropertyStatus.TERJUAL ? "TERJUAL" : "TERSEDIA"));
        }
    }

    private static void beliProperti(Scanner input, PropertyManager pm) {
        List<Property> available = pm.getAvailableProperties();
        if (available.isEmpty()) {
            System.out.println("Tidak ada properti yang tersedia.");
            return;
        }

        tampilkanProperti(available);

        System.out.print("Masukkan ID properti yang ingin dibeli: ");
        int id;
        try {
            id = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID tidak valid.");
            return;
        }

        Property selected = pm.cariById(id);
        if (selected == null) {
            System.out.println("Properti tidak ditemukan.");
            return;
        }

        if (selected.getStatus() == PropertyStatus.TERJUAL) {
            System.out.println("Properti ini sudah terjual.");
            return;
        }

        System.out.println("Pilih metode pembayaran:\n1. Transfer Bank\n2. Kartu Kredit");
        int metode;
        try {
            metode = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
            return;
        }

        switch (metode) {
            case 1 -> {
                System.out.println("Pembayaran via transfer bank diproses...");
                delayPembayaran();
                System.out.println("Pembayaran telah diterima.");
            }
            case 2 -> {
                System.out.println("Pembayaran via kartu kredit diproses...");
                delayPembayaran();
                System.out.println("Pembayaran telah diterima.");
            }
            default -> {
                System.out.println("Metode tidak dikenal.");
                return;
            }
        }

        pm.markAsSold(id);
        System.out.println("Properti berhasil dibeli dan status diubah menjadi TERJUAL.");
    }

    private static void delayPembayaran() {
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
