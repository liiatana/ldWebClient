package ru.lanit.ld.wc.model;


import java.util.*;

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

    /*public instructionType getType(int n) {
        return typeList.get(n);
    }*/

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

    public instructionType getControlTypeWithoutCheck(boolean lookForPossitive) {
        // private int checkReportTypeNegative;
        //    private int checkReportTypePositive;

        List<instructionType> types1 = new ArrayList<instructionType>();
        int operation;

        for (int i = 0; i <= this.getTypeList().size() - 1; i++) {
            operation = lookForPossitive ? this.getTypeList().get(i).getCheckReportTypePositive() : this.getTypeList().get(i).getCheckReportTypeNegative();
            if (operation == 1) {
                types1.add(this.getTypeList().get(i));
            }
        }

        if (types1.size() > 0) {
            Collections.shuffle(types1);
            return types1.get(0);
        } else return null;


    }

    public instructionType getControlTypeWithTextCheck(boolean lookForPossitive) {

        List<instructionType> types1 = new ArrayList<instructionType>();
        int operation;

        for (int i = 0; i <= this.getTypeList().size() - 1; i++) {
            operation = lookForPossitive ? this.getTypeList().get(i).getCheckReportTypePositive() : this.getTypeList().get(i).getCheckReportTypeNegative();
            if (operation == 2 || operation == 5) {
                types1.add(this.getTypeList().get(i));
            }
        }
        Collections.shuffle(types1);

        return types1.get(0);

    }


    public instructionType getInstructionTypeIdByName(String typeName) {
        int i = 0;

        do {
            if ( typeName.equals(this.typeList.get(i).getName()) ) {
                return this.typeList.get(i);
            }
            i++;
        } while (i < this.typeList.size());

        return null;

    }

    public instructionType getAnyWithRedirectedAsControl(boolean isAsControl) {
        List<instructionType> types = new ArrayList<instructionType>();

        for (int i = 0; i <= this.getTypeList().size() - 1; i++) {
            if (isAsControl)
            {
                if (this.typeList.get(i).getUseControl()&& this.typeList.get(i).isRedirectAsControl()){
                    types.add(this.typeList.get(i));
                }
            }else{
                if (this.typeList.get(i).getUseControl()&& !this.typeList.get(i).isRedirectAsControl()){
                    types.add(this.typeList.get(i));
                }
            }
        }
        Collections.shuffle(types);

        if (types.size() > 0) {
            Collections.shuffle(types);
            return types.get(0);
        } else return null;


    }

    public Set<String> getInstructionTypesListAsString(boolean isExpectedOnlyControlTypes) {


        Set<String> collect1 = new HashSet<>();

        for (int i = 0; i <= this.getTypeList().size() - 1; i++) {
            if (!isExpectedOnlyControlTypes)
            {
                collect1.add(this.getTypeList().get(i).getName().toUpperCase());
            }else{
                if (this.getTypeList().get(i).getUseControl()){
                    collect1.add(this.getTypeList().get(i).getName().toUpperCase());
                }
            }
        }

      return collect1;
    }

    public instructionType getType(boolean instructionTypeFlag) {

        instructionType type = instructionTypeFlag ? this.getAnyNoticeType() : this.getAnyTaskType();
        return type;
    }

    /*public InstructionTypes AllTasks() {
        InstructionTypes tasks=new InstructionTypes();
        Predicate<? super instructionType> task=instructionType::getUseControl;
        tasks=this.typeList.stream().filter(task).map();

    }*/

}
