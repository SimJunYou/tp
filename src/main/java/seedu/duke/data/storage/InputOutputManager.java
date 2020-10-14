package seedu.duke.data.storage;

import seedu.duke.data.Module;
import seedu.duke.data.ModuleManager;
import seedu.duke.data.Task;
import seedu.duke.common.Constant;
import seedu.duke.data.TaskManager;
import seedu.duke.ui.TextUi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages all inputs and outputs (to and from files).
 * Encoder and Decoder are only used by InputOutputManager.
 * InputOutputManager also handles exceptions thrown by Encoder and Decoder. No exceptions are thrown from here.
 *
 * @author Sim Jun You
 */
public class InputOutputManager {
    static ArrayList<Task> loadedTasksList;
    static HashMap<String, Module> loadedModulesMap;
    static HashMap<String, Module> loadedNusModulesMap;

    static String root = System.getProperty("user.dir");
    static java.nio.file.Path dirPath = java.nio.file.Paths.get(root, "data");

    static String userModuleFileName = Constant.MOD_SAVE_FILE_NAME + Constant.FILE_EXT;
    static String userTaskFileName = Constant.TASK_SAVE_FILE_NAME + Constant.FILE_EXT;
    static String nusModuleFileName = Constant.NUSMOD_SAVE_FILE_NAME + Constant.FILE_EXT;
    static java.nio.file.Path userModuleFile = java.nio.file.Paths.get(String.valueOf(dirPath), userModuleFileName);
    static java.nio.file.Path userTaskFile = java.nio.file.Paths.get(String.valueOf(dirPath), userTaskFileName);
    static java.nio.file.Path nusModuleFile = java.nio.file.Paths.get(String.valueOf(dirPath), nusModuleFileName);

    /**
     * Creates the save directory if it has not been created.
     * Loads the user's module and task saves into memory.
     */
    public static void start() {
        File saveFolder = dirPath.toFile();
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        } else {
            loadUserSaves();
        }
    }

    /**
     * Loads user saves (modules, tasks) from the given files.
     */
    public static void loadUserSaves() {
        try {
            ModuleManager.load(Decoder.loadModules(userModuleFile.toString()));
            TaskManager.load(Decoder.loadTasks(userTaskFile.toString()));
        } catch (FileNotFoundException e) {
            // do nothing
        }
    }

    /**
     * Loads NUS Modules from the given file.
     */
    public static void loadNusModSave() {
        try {
            loadedNusModulesMap = Decoder.loadModules(nusModuleFile.toString());
        } catch (FileNotFoundException e) {
            // do something
        }
    }

    /**
     * Updates the user's save files. Does not save NUS Modules.
     */
    public static void save() {
        try {
            Encoder.saveModules(userModuleFile.toString());
            Encoder.saveTasks(userTaskFile.toString());
        } catch (ModuleManager.ModuleNotFoundException e) {
            // print module not found
        } catch (TaskManager.TaskNotFoundException e) {
            // print task not found
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the user's NUS Modules save file.
     */
    public static void saveNusMods() {
        try {
            Encoder.saveNusModules(nusModuleFile.toString());
        } catch (ModuleManager.ModuleNotFoundException e) {
            // print module not found
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}