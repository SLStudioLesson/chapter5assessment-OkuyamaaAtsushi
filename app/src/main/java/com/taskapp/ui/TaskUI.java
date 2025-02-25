package com.taskapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.taskapp.exception.AppException;
import com.taskapp.logic.TaskLogic;
import com.taskapp.logic.UserLogic;
import com.taskapp.model.User;

public class TaskUI {
    private final BufferedReader reader;

    private final UserLogic userLogic;

    private final TaskLogic taskLogic;

    private User loginUser;

    public TaskUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        userLogic = new UserLogic();
        taskLogic = new TaskLogic();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param reader
     * @param userLogic
     * @param taskLogic
     */
    public TaskUI(BufferedReader reader, UserLogic userLogic, TaskLogic taskLogic) {
        this.reader = reader;
        this.userLogic = userLogic;
        this.taskLogic = taskLogic;
    }

    /**
     * メニューを表示し、ユーザーの入力に基づいてアクションを実行します。
     *
     * @see #inputLogin()
     * @see com.taskapp.logic.TaskLogic#showAll(User)
     * @see #selectSubMenu()
     * @see #inputNewInformation()
     */
    public void displayMenu() {
        System.out.println("タスク管理アプリケーションにようこそ!!");

        inputLogin();

        // メインメニュー
        boolean flg = true;
        while (flg) {
            try {
                System.out.println("以下1~3のメニューから好きな選択肢を選んでください。");
                System.out.println("1. タスク一覧, 2. タスク新規登録, 3. ログアウト");
                System.out.print("選択肢：");
                String selectMenu = reader.readLine();

                System.out.println();

                switch (selectMenu) {
                    case "1":
                        taskLogic.showAll(loginUser);
                        selectSubMenu();
                        break;
                    case "2":
                        inputNewInformation();
                        break;
                    case "3":
                        System.out.println("ログアウトしました。");
                        flg = false;
                        break;
                    default:
                        System.out.println("選択肢が誤っています。1~3の中から選択してください。");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    /**
     * ユーザーからのログイン情報を受け取り、ログイン処理を行います。
     *
     * @see com.taskapp.logic.UserLogic#login(String, String)
     */
    public void inputLogin() {
        boolean flg = true;
        while(flg){
            try{
                System.out.print("メールアドレスを入力してください：");
                String email = reader.readLine();
                System.out.print("パスワードを入力してください：");
                String password = reader.readLine();

                loginUser = userLogic.login(email, password);
                flg = false;
            } catch (IOException e){
                e.printStackTrace();
            } catch (AppException e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * ユーザーからの新規タスク情報を受け取り、新規タスクを登録します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#save(int, String, int, User)
     */
    public void inputNewInformation() {
        boolean flg = true;
        while(flg){
            try{
                System.out.print("タスクコードを入力してください：");
                String taskCode = reader.readLine();
                if(!isNumeric(taskCode)){
                    System.out.println("コードは半角の数字で入力してください");
                    System.out.println();
                    continue;
                }
                System.out.print("タスク名を入力してください：");
                String taskName = reader.readLine();
                if(taskName.length() > 10){
                    System.out.println("タスク名は10文字以内で入力してください");
                    System.out.println();
                    continue;
                }
                System.out.print("担当するユーザーのコードを選択してください：");
                String userCode = reader.readLine();
                if(!isNumeric(userCode)){
                    System.out.println("コードは半角の数字で入力してください");
                    System.out.println();
                    continue;
                }

                taskLogic.save(Integer.parseInt(taskCode), taskName, Integer.parseInt(userCode), loginUser);
                System.out.println(taskName + "の登録が完了しました。");
                

                flg = false;
                

            } catch (IOException e){
                e.printStackTrace();
            } catch (AppException e){
                System.out.println(e.getMessage());
            }

        }
        System.out.println();
    }

    /**
     * タスクのステータス変更または削除を選択するサブメニューを表示します。
     *
     * @see #inputChangeInformation()
     * @see #inputDeleteInformation()
     */
    public void selectSubMenu() {
        boolean flg = true;
        while (flg) {
            try {
                System.out.println("以下1~3のメニューから好きな選択肢を選んでください。");
                System.out.println("1. タスクのステータス変更, 2. タスク削除, 3. メインメニューに戻る");
                System.out.print("選択肢：");
                String selectMenu = reader.readLine();

                System.out.println();

                switch (selectMenu) {
                    case "1":
                        inputChangeInformation();
                        break;
                    case "2":
                        inputDeleteInformation();
                        break;
                    case "3":
                        displayMenu();
                        flg = false;
                        break;
                    default:
                        System.out.println("選択肢が誤っています。1~3の中から選択してください。");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    /**
     * ユーザーからのタスクステータス変更情報を受け取り、タスクのステータスを変更します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#changeStatus(int, int, User)
     */
    public void inputChangeInformation() {
        boolean flg = true;
        while(flg){
            try{
                System.out.println("ステータスを変更するタスクコードを入力してください：");
                String taskCode = reader.readLine();
                if(!isNumeric(taskCode)){
                    System.out.println("コードは半角の数字で入力してください");
                    System.out.println();
                    continue;
                }
                System.out.println("1. 着手中, 2. 完了");
                System.out.print("選択肢：");
                String selectNumber = reader.readLine();
                if(!isNumeric(selectNumber)){
                    System.out.println("コードは半角の数字で入力してください");
                    System.out.println();
                    continue;
                }
                if(Integer.parseInt(selectNumber) != 1 && Integer.parseInt(selectNumber) != 2){
                    System.out.println("ステータスは1・2の中から選択してください");
                    System.out.println();
                    continue;
                }

                taskLogic.changeStatus(Integer.parseInt(taskCode), Integer.parseInt(selectNumber), loginUser);
                flg = false;
            } catch (IOException e){
                e.printStackTrace();
            } catch (AppException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * ユーザーからのタスク削除情報を受け取り、タスクを削除します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#delete(int)
     */
    public void inputDeleteInformation() {
        Boolean flg = true;
        while(flg){
            try{
                System.out.print("削除するタスクコードを入力してください：");
                String taskCode = reader.readLine();
                if(!isNumeric(taskCode)){
                    System.out.println("コードは半角の数字で入力してください");
                    System.out.println();
                    continue;
                }
                taskLogic.delete(Integer.parseInt(taskCode));
                flg = false;

            } catch (IOException e){
                e.printStackTrace();
            } catch (AppException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 指定された文字列が数値であるかどうかを判定します。
     * 負の数は判定対象外とする。
     *
     * @param inputText 判定する文字列
     * @return 数値であればtrue、そうでなければfalse
     */
    public boolean isNumeric(String inputText) {
        return inputText.chars().allMatch(c -> Character.isDigit((char) c));
    }
}