package com.m2comm.headache.DTO;

public class ListDTO {

    int num;
    int age;
    String name;
    String add;

    public ListDTO(int num, int age, String name, String add) {
        this.num = num;
        this.age = age;
        this.name = name;
        this.add = add;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public int getNum() {
        return num;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAdd() {
        return add;
    }
}
