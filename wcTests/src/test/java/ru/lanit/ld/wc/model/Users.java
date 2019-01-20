package ru.lanit.ld.wc.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.appmanager.RestApiHelper;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Users {
    public List<UserInfo> users;

    public Users() {
    }


    public void load(ApplicationManager app) throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(app.properties.getProperty("data.usersFilePath"))))) {
            String line = reader.readLine();
            String json = "";
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            //gson.fromJson(json, GroupData.class);//писать в кач-ве второго параметра GroupData.class можно,
            // но это будет только один объект, а нам нужен список таких объектов. А написать List нельзя((( не работаеть..
            // поэтому не шибюко понятное действие должно выглядеть так:
            List<UserInfo> usrs = gson.fromJson(json, new TypeToken<List<UserInfo>>() {
            }.getType());

            this.users = usrs;
        }


        BASE64Encoder encoder = new BASE64Encoder();

        for (UserInfo user : this.users) {
            user.withAuth("Basic " + encoder.encode(String.format("%s:%s", user.getLogin(), user.getPassword()).getBytes()));
            //RestApiHelper uapi = new RestApiHelper(user, app);
            user.withUserApi(new RestApiHelper(user, app));
            JsonElement parsed = user.getUserApi().me();
            user.withId(parsed.getAsJsonObject().get("effectiveUserId").getAsInt()) // id пользователя
                    .withUserName(parsed.getAsJsonObject().get("name").getAsString()) //ФИО
                    .withisAdmin(parsed.getAsJsonObject().get("isAdmin").getAsBoolean()); //если он админ
            user.withUserTypes(user.getUserApi().instructionTypesInfo());

        }



    }


    /*public UserInfo anyUser() {

        List<UserInfo> usersCollection = new ArrayList<UserInfo>();
        for (UserInfo user : this.users) {
            usersCollection.add(user);
        }
        Collections.shuffle(usersCollection);
        Users newlist = new Users();
        newlist.users = usersCollection.subList(0, 1);
        return newlist.users.get(0);

    }*/

    public Users anyUser(int numberOfReceivers) {

        List<UserInfo> usersCollection = new ArrayList<UserInfo>();
        for (UserInfo user : this.users) {
            usersCollection.add(user);
        }
        Collections.shuffle(usersCollection);
        Users newlist = new Users();
        newlist.users = usersCollection.subList(0, numberOfReceivers);
        return newlist;

    }

    public int[] Ids() {

        int[] ids = new int[this.users.size()];
        for (int i = 0; i < this.users.size(); i++) {
            ids[i] = this.users.get(i).getId();
        }
        return ids;

    }

    public UserInfo getUserById(int Id) {
        int i=0;

        do {
            if( this.users.get(i).getId()==Id) {return this.users.get(i);}
            i++;
        }while( i< this.users.size());

        return null;
    }

}