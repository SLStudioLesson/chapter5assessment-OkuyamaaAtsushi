package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * @param email メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length != 4) continue;
                if(!(values[2].equals(email) && values[3].equals(password))) continue;
                user = new User(Integer.parseInt(values[0]), values[1], values[2], values[3]);
                
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return user;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length != 4) continue;
                if(Integer.parseInt(values[0]) != code) continue;
                user = new User(Integer.parseInt(values[0]), values[1], values[2], values[3]);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
