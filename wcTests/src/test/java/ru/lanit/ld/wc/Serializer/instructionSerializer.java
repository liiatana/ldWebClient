package ru.lanit.ld.wc.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.lanit.ld.wc.model.Instruction;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class instructionSerializer implements JsonSerializer<Instruction> {

    //private String nullValue;

    @Override
    public JsonElement serialize(Instruction src, Type type, JsonSerializationContext context) {


        JsonObject mainInstruction = new JsonObject();
        mainInstruction.addProperty("receiverID", src.receiverID[0]);
        mainInstruction.addProperty("text", src.text);
        mainInstruction.addProperty("subject", src.subject);
        mainInstruction.addProperty("comment", src.comment);

        if (src.fileIds == null) {
            mainInstruction.add("fileIds", null);
        } else {
            mainInstruction.addProperty("fileIds", src.fileIds[0]);
        }

        mainInstruction.addProperty("execAuditorID", src.execAuditorID);
        mainInstruction.addProperty("initiatorID", src.initiatorID);
        mainInstruction.addProperty("reportReceiverID", src.reportReceiverID);
        if (src.startDate == null) {
            mainInstruction.add("startDate", null);
        } else {
            mainInstruction.addProperty("startDate",String.format("%sZ",src.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000")))  );
        }


        //mainInstruction.addProperty("startDate", src.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        if (src.executionDate == null) {
            mainInstruction.add("executionDate", null);
        } else {
            mainInstruction.addProperty("executionDate", String.format("%sZ",src.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }

        if (src.execInterval == 0) {
            mainInstruction.add("execInterval", null);
        } else {
            mainInstruction.addProperty("execInterval", src.execInterval);
        }



        JsonObject jsonInstruction = new JsonObject();
        jsonInstruction.addProperty("instructionTypeId", src.instructionTypeId);

        if (src.documentId == 0) {
            jsonInstruction.add("documentId", null);
        } else {
            jsonInstruction.addProperty("documentId", src.documentId);
        }


        jsonInstruction.addProperty("sendType", src.sendType);
        jsonInstruction.addProperty("reportToExecutive", src.reportToExecutive);
        jsonInstruction.addProperty("withExecutive", src.withExecutive);
        jsonInstruction.addProperty("control", src.control);

        jsonInstruction.add("mainInstruction", mainInstruction);
        return jsonInstruction;
    }



}
