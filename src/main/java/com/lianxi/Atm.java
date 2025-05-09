package com.lianxi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import com.lianxi.Account;

public class Atm {
    private ArrayList<Account> accounts = new ArrayList<>();//[]
    private Scanner sc = new Scanner(System.in);
    private Account loginAcc;//记录登陆后的账户

    //启动atm系统展示欢迎界面
    public void start() {
        while (true) {
            System.out.println("===欢迎您进入到了ATM系统");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择：");
            int command =sc.nextInt();
            switch (command) {
                case 1:
                    //用户登录
                    login();
                    break;
                case 2:
                    //用户开户
                    createAccount();
                    break;
                default:
                    System.out.println("没有这个操作~~");
            }
        }

    }

    //用户登录操作
    private void login(){
        System.out.println("==用户登录==");
        //1.判断账户中是否存在对象，如果不存在就不继续了
        if(accounts.size()==0){
            System.out.println("不存在用户");
            return;
        }
        while (true) {
            System.out.println("请您输入您的卡号");
            String cardId  =sc.next();
            //3.判断卡号是否存在
            Account acc = getAccountByCardId(cardId);
            if(acc==null){
                //说明不存在
                System.out.println("您输入的卡号不存在,请确认~~");
            }
            else{
                //卡号存在，提醒输入密码
                while (true) {
                    System.out.println("请输入登录密码： ");
                    String password =sc.next();
                    //4.判断密码是否正确
                    if(acc.getPassword().equals(password)){
                        //密码正确，登陆成功
                        loginAcc = acc;
                        System.out.println("恭喜您，"+acc.getUserName()+"成功登陆了系统，您的卡号是： " + acc.getCardId());
                        //展示登录后的操作界面
                        showUserCommand();
                        return;//跳出当前并回到页面
                    }
                    else{
                        System.out.println("您输入的密码不正确,请确认~~");
                    }
                }
            }
        }
    }

    //登陆后的操作界面
    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUserName()+"，您可以选择以下服务====");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、密码修改");
            System.out.println("6、退出");
            System.out.println("7、注销当前账户");
            int command =sc.nextInt();
            switch (command) {
                case 1:
                    //查询当前账户
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    break;
                case 3:
                    //取款
                    break;
                case 4:
                    //转账
                    break;
                case 5:
                    //密码修改
                    break;
                case 6:
                    //退出
                    System.out.println(loginAcc.getUserName()+"您退出系统成功");
                    return;
                case 7:
                    //注销
                    break;
                default:
                    System.out.println("该操作不存在，请重选~~");
            }
        }
    }

    //展示当前登录的账号信息

    private void showLoginAccount(){
        System.out.println("==当前您的账户信息如下：==");
        System.out.println("户主" + loginAcc.getUserName());
        System.out.println("性别" + loginAcc.getSex());
        System.out.println("卡号" + loginAcc.getCardId());
        System.out.println("余额" + loginAcc.getMoney());
        System.out.println("每次取现额度" + loginAcc.getLimit());

    }

    //完成开户操作
    private void createAccount(){
        //1.创建一个账户对象，用于封装用户开户信息
        Account acc =  new Account();

        //2.需要用户自己输入信息，赋值给账户对象
        System.out.println("请输入您的账户名称：");
        String name =sc.next();
        acc.setUserName(name);

        while (true) {
            System.out.println("请输入您的性别：");
            char sex =sc.next().charAt(0);
            if(sex == '男' ||sex == '女' ){
                acc.setSex(sex);
                break;
            }else{
                System.out.println("您输入的性别有误~只能是男或者女~");
            }
        }

        while (true) {
            System.out.println("请输入您的账户密码：");
            String passWord =sc.next();
            System.out.println("请确认您的账户密码：");
            String passWordOk =sc.next();
            if(passWord.equals(passWordOk)){
                acc.setPassword(passWord);
                break;
            }
            else{
                System.out.println("您输入的两次密码不不一致：");
            }
        }

        System.out.println("请输入您的取现额度：");
        double limit =sc.nextDouble();
        acc.setLimit(limit);

        //重点：我们需要为这个账户生成一个卡号（系统自动生成，8位数，不能和其他账户的卡号重复）
        String newCardId =createCardId();
        acc.setCardId(newCardId);


        //3.把这个对象，存入到账户集合中
        accounts.add(acc);
        System.out.println("恭喜您：" + acc.getUserName()+"开户成功，您的卡号是： "+ acc.getCardId());
    }

    //返回一个8位数卡号，而且这个卡号不能与其他账户的卡号重复
    private String createCardId(){
        while (true) {
            //1.定义一个String类型的变量记住一个8位数字作为卡号
            String cardId ="";

            //2.使用循环，循环8次，每次产生一个随机数给cardId连接起来
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                int data = r.nextInt(10);
                cardId += data ;
            }
            //3.判断新cardId是否与其他重复
            Account acc = getAccountByCardId(cardId);
            if(acc == null){
                //说明cardId没找到帐户对象
                return cardId;
            }
        }
    }


    //根据卡号查询对象
    private Account getAccountByCardId(String cardId){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc =  accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }

        }
        return null;
    }
}
