package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class folderList {

    List<Instruction> folderList= new ArrayList<>();

    public folderList(JsonElement jsonList) {
        for (int i = 0; i <= jsonList.getAsJsonObject().size() - 1; i++) {
            Instruction inst = new Instruction(jsonList);
            folderList.add(i, inst);
        }
    }

    public int getInstructionNumInFolder(int instructionID) {
        int i=0;
        do
            if(this.folderList.get(i).getInstructionId() ==instructionID){
                return i;
            }
            else i++;
        while (i<=this.folderList.size())    ;
        return -1;
    }
}
