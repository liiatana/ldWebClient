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

    /*public void load (ApplicationManager app){
        this.typeList=app.focusedUser.getUserApi().instructionTypesInfo();
    }*/

    public instructionType getType(int n) {
        return typeList.get(n);
    }

    public instructionType getAnyNoticeType() {
        return getInstructionType(false, -1);
    }

    public instructionType getAnyTaskType(boolean withClericalOperation) {
        int withClerical = withClericalOperation ? 1 : 0; // assigns 1 to i.
        return getInstructionType(true, withClerical);
    }


    public instructionType getAnyTaskType() {
        return getInstructionType(true, -1);
    }


    private instructionType getInstructionType(boolean withUseControl, int withClerical) {
        List<instructionType> types = new ArrayList<instructionType>();
        for (int i = 0; i < this.typeList.size(); i++) {
            switch (withClerical) {
                case 1:
                    if (this.typeList.get(i).getOperationID() > 0 && this.typeList.get(i).getUseControl() == withUseControl) {
                        //types.remove(types.size() - 1);
                        types.add(this.typeList.get(i));
                    }
                    break;
                case 0:
                    if (this.typeList.get(i).getOperationID() == 0 && this.typeList.get(i).getUseControl() == withUseControl) {
                        types.add(this.typeList.get(i));
                    }
                    break;
                case -1:
                    if (this.typeList.get(i).getUseControl() == withUseControl) {
                        types.add(this.typeList.get(i));
                    }
                    break;

            }
        }
        Collections.shuffle(types);
        return new instructionType(types.subList(0, 1).get(0));
    }

    public String getInstructionTypeNameById(int instructionTypeId) {

        int i = 0;

        do {
            if (this.typeList.get(i).getId() == instructionTypeId) {
                return this.typeList.get(i).getName();
            }
            i++;
        } while (i < this.typeList.size());

        return null;
    }

}
