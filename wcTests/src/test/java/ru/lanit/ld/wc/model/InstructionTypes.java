package ru.lanit.ld.wc.model;

import ru.lanit.ld.wc.appmanager.ApplicationManager;


import java.util.ArrayList;
import java.util.Collections;
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
        this.typeList=app.focusedUser.getUserApi().instructionTypesInfo();
    }

    public instructionType getType(int n) {
        return typeList.get(n);
    }

    public instructionType getAnyNoticeType() {
        /*int i=0;
        do {
            if(typeList.get(i).getUseControl()==true){
                i++;
            }else return typeList.get(i);

        } while(i <typeList.size()-1 );

        return null;*/
        //instructionType type = getInstructionType(false);

        return getInstructionType(false);


    }

    public instructionType getAnyTaskType() {
        //instructionType type = getInstructionType(true);

        return getInstructionType(true);
    }

    private instructionType getInstructionType(boolean withUseControl) {
        List<instructionType> types = new ArrayList<instructionType>();
        for (int i = 0; i < this.typeList.size(); i++) {
            if (this.typeList.get(i).getUseControl() == withUseControl) {
                types.add(this.typeList.get(i));
            }
        }
        Collections.shuffle(types);
        instructionType type = new instructionType();
        type = types.subList(0, 1).get(0);
        return type;
    }


}
