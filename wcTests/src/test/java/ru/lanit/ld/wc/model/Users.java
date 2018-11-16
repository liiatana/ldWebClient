package ru.lanit.ld.wc.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;

import java.util.Base64;
import java.util.List;


public class Users {
    public List<UserInfo> users;

    public Users() {
    }


    public void load(String path) throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
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

        for (UserInfo user: this.users){
              user.withAuth("Basic "+ encoder.encode(String.format("%s:%s",user.getLogin(),user.getPassword()).getBytes()));
        }

    }



}