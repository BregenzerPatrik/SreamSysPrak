package command;

import java.util.ArrayList;

public class UsedNamesAggregate {
    private static ArrayList<String> usedNames=new ArrayList<>();
    public static boolean isNameUsed(String name){
        return usedNames.contains(name);
    }
    public static void addName(String name){
        if(!usedNames.contains(name)){
            usedNames.add(name);
        }
    }
    public static void deleteName(String name){
        if(usedNames.contains(name)){
            usedNames.remove(name);
        }
    }
}
