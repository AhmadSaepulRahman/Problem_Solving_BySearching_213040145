package tugas;

import java.util.*;

public class DonasiPerang {

    static class Graph {
        private Map<String, Map<String, Integer>> graph = new HashMap<>();

        public void tambahRute(String kotaAsal, String kotaTujuan, int jarak) {
            graph.putIfAbsent(kotaAsal, new HashMap<>());
            graph.get(kotaAsal).put(kotaTujuan, jarak);
        }

        public Map<String, Map<String, Integer>> getGraph() {
            return graph;
        }
    }

    public static List<String> cariRuteTerpendek(Map<String, Map<String, Integer>> graph, String kotaAsal, String kotaTujuan) {
        if (!graph.containsKey(kotaAsal) || !graph.containsKey(kotaTujuan)) {
            return null; // Tidak ada rute jika kota asal atau tujuan tidak ditemukan
        }

        Map<String, Integer> jarakMin = new HashMap<>();
        Map<String, String> kotaSebelumnya = new HashMap<>();
        Set<String> belumDikunjungi = new HashSet<>();

        for (String kota : graph.keySet()) {
            jarakMin.put(kota, Integer.MAX_VALUE);
            kotaSebelumnya.put(kota, null);
            belumDikunjungi.add(kota);
        }

        jarakMin.put(kotaAsal, 0);

        while (!belumDikunjungi.isEmpty()) {
            String kotaSekarang = null;
            for (String kota : belumDikunjungi) {
                if (kotaSekarang == null || jarakMin.get(kota) < jarakMin.get(kotaSekarang)) {
                    kotaSekarang = kota;
                }
            }

            if (kotaSekarang.equals(kotaTujuan)) {
                List<String> ruteTerpendek = new ArrayList<>();
                while (kotaSekarang != null) {
                    ruteTerpendek.add(kotaSekarang);
                    kotaSekarang = kotaSebelumnya.get(kotaSekarang);
                }
                Collections.reverse(ruteTerpendek);
                return ruteTerpendek;
            }

            belumDikunjungi.remove(kotaSekarang);

            for (String tetangga : graph.get(kotaSekarang).keySet()) {
                int jarakBaru = jarakMin.get(kotaSekarang) + graph.get(kotaSekarang).get(tetangga);
                if (jarakBaru < jarakMin.get(tetangga)) {
                    jarakMin.put(tetangga, jarakBaru);
                    kotaSebelumnya.put(tetangga, kotaSekarang);
                }
            }
        }

        return null; // Tidak ada rute yang ditemukan
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.tambahRute("Front Perang", "Barak Militer", 20);
        graph.tambahRute("Front Perang", "Pusat Logistik", 30);
        graph.tambahRute("Barak Militer", "Desa Terdekat", 15);
        graph.tambahRute("Pusat Logistik", "Barak Militer", 10);
        graph.tambahRute("Desa Terdekat", "Wilayah Musuh", 25);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Front Perang Asal: ");
        String kotaAsal = scanner.nextLine();
        System.out.print("Wilayah Musuh Tujuan: ");
        String kotaTujuan = scanner.nextLine();

        List<String> ruteTerpendek = cariRuteTerpendek(graph.getGraph(), kotaAsal, kotaTujuan);

        if (ruteTerpendek != null) {
            System.out.println("Rute terpendek dari " + kotaAsal + " ke " + kotaTujuan + " adalah: " + ruteTerpendek);
        } else {
            System.out.println("Tidak ada rute yang tersedia dari " + kotaAsal + " ke " + kotaTujuan + ".");
        }
    }
}
