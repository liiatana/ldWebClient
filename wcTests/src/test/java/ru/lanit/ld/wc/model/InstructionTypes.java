package ru.lanit.ld.wc.model;

import ru.lanit.ld.wc.appmanager.ApplicationManager;


import java.util.List;

public class InstructionTypes {
    public List<instructionType> typeList;

    public InstructionTypes(List<instructionType> typeList) {
        this.typeList = typeList;
    }

    public InstructionTypes() {
    }

    public List<instructionType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<instructionType> typeList) {
        this.typeList = typeList;
    }

    public void load (ApplicationManager app){
        this.typeList=app.UserList.users.get(0).getUserApi().instructionTypesInfo();
    }

    public instructionType getType(int n) {
        return typeList.get(n);
    }
}
