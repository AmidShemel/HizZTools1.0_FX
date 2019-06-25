package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main extends Application {

    private static FileRedactor fileRedactor = new FileRedactor();
    public static String inputFilePatch;

    public static String tempDB = "tempDB.db";
    public static String projectsDB = "projectsDB.db";
    public static String simcoEdit = "simcoEdit.exe?";
    public static String esprit = "esprit.exe";

    public static void main(String[] args){
        inputFilePatch = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SaveData.deSerialize();
        optionsReader();
        System.out.println(simcoEdit);
        //Запуск редактору файла
        try {
            fileRedactor.fileRedactor();
        } catch (IOException e) {
            Dialog.infoBox(e.getMessage(), "Помилка");
        }
        //Запуск відредагованого файлу в CIMCOEdit.exe
        try {
            //Process process = new ProcessBuilder("D:\\WORK\\CIMCOEdit6\\CIMCOEdit.exe", fileRedactor.outputFileFoolPatch).start();
            Process process = new ProcessBuilder(simcoEdit, fileRedactor.outputFileFoolPatch).start();
            System.exit(0);
        } catch (IOException e) {
            Dialog.infoBox(e.getMessage(), "Помилка");
        }
    }

    // Зчитує файл settings.ini в якому вказані всі шляхи до допоміжних програм та файлів
    private static void optionsReader(){
        // "G:\IJ_Projects\WorkDB\settings.ini"  settings.ini  \src\settings.ini
        String line = "";
        try {
            BufferedReader optionReader = new BufferedReader (new FileReader("settings.ini"));
            while ((line = optionReader.readLine())!= null){
                switch (line){
                    case "[BDPATH]":
                        projectsDB = optionReader.readLine();
                        break;
                    case "[BDTEMPPATH]":
                        tempDB = optionReader.readLine();
                        break;
                    case "[SIMCOPATH]":
                        simcoEdit = optionReader.readLine();
                        break;
                    case "[ESPRITPATH]":
                        esprit = optionReader.readLine();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}