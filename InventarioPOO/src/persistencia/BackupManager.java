/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Inventario;

/**
 *
 * @author Angie
 */
public class BackupManager {
    private static final String BACKUP_FOLDER = "backups";
    
    private Gson gson;
    
    public BackupManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        File folder = new File(BACKUP_FOLDER);
        if (!folder.exists()) folder.mkdirs();
    }
    
    public static class Backup {
        private String nombre;
        private Date fecha;
        private long tamaño;

        public Backup(String nombre, Date fecha, long tamaño) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.tamaño = tamaño;
        }

        public String getNombre() { return nombre; }
        public Date getFecha() { return fecha; }
        public long getTamaño() { return tamaño; }
    }
    
    public void crearBackup(Inventario inventario) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
        String nombreArchivo = "backup_" + timestamp + ".json";
        File backupFile = new File(BACKUP_FOLDER, nombreArchivo);

        try (Writer writer = new FileWriter(backupFile)) {
            gson.toJson(inventario, writer);
        }
    }
    
    public List<Backup> listarBackups() {
        List<Backup> lista = new ArrayList<>();
        File folder = new File(BACKUP_FOLDER);
        File[] archivos = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (archivos != null) {
            for (File f : archivos) {
                lista.add(new Backup(f.getName(), new Date(f.lastModified()), f.length()));
            }
        }
        return lista;
    }
    
    public Inventario restaurarBackup(String nombreArchivo) throws IOException {
        File backupFile = new File(BACKUP_FOLDER, nombreArchivo);
        if (!backupFile.exists()) throw new IOException("Backup no encontrado");

        try (Reader reader = new FileReader(backupFile)) {
            return gson.fromJson(reader, Inventario.class);
        }
    }
    
    public void eliminarBackup(String nombreArchivo) throws IOException {
        File backupFile = new File(BACKUP_FOLDER, nombreArchivo);
        if (!backupFile.exists()) throw new IOException("Backup no encontrado");
        if (!backupFile.delete()) throw new IOException("No se pudo eliminar el backup");
    }
}
