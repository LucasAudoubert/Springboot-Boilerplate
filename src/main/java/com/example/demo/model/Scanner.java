package com.example.demo.model;

public class Scanner {

    public static void main(String[] args) {
        java.util.Scanner myObj = new java.util.Scanner(System.in);
        System.out.println("Your username:");

        String userName = myObj.nextLine();

        System.out.println("Your age");
        int age = myObj.nextInt();

        System.out.println("Your birthdate");
        int birthDate = myObj.nextInt();

        System.out.println("Are you sure you are over 18+?");
        boolean major = myObj.nextBoolean();

        System.out.printf("Your username is %s, Your age is %s and your birthdate is %s. Are you over 18+: %s.", userName, age, birthDate, major);
    }

}
