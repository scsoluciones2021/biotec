package rpt.repository;

import java.io.File;

import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Carrusel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarruselRepository {
    static String[] listaCarrusel(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        String[] listaRetorno = new String [fList.length];
        int x = 0;
        for (File file : fList){
            if (file.isFile()){
                listaRetorno[x]=file.getAbsolutePath();
            }
            x++;
        }
        return listaRetorno;
    }
}
