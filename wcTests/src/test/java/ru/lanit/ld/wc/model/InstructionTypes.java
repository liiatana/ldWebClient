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
        return getInstructionType(false,false);
    }

    public instructionType getAnyTaskType(boolean withClericalOperation) {

         return getInstructionType(true,withClericalOperation);
    }

   /* public instructionType getAnyClericalTaskType() {
        return getInstructionType(true,true);
    }*/


    private instructionType getInstructionType(boolean withUseControl,boolean clerical) {
        List<instructionType> types = new ArrayList<instructionType>();
        for (int i = 0; i < this.typeList.size(); i++) {
            if (this.typeList.get(i).getUseControl() == withUseControl) {
                if(!clerical){
                    types.add(this.typeList.get(i));
                }else if(this.typeList.get(i).getOperationID()>0) {
                    types.add(this.typeList.get(i));
                }

            }
        }
        Collections.shuffle(types);
        instructionType type = new instructionType();
        type = types.subList(0, 1).get(0);
        return type;
    }

    /*public instructionType getAnyClericalTaskType() {
        List<instructionType> types = new ArrayList<instructionType>();
        for (int i = 0; i < this.typeList.size(); i++) {
            if (this.typeList.get(i).getUseControl() == true && this.typeList.get(i).getOperationID()>0) {
                types.add(this.typeList.get(i));
            }
        }
        Collections.shuffle(types);
        instructionType type = new instructionType();
        type = types.subList(0, 1).get(0);
        return type;

    }*/

}
