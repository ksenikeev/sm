package ru.kpfu.icmit.clientsm;

/** Класс хранит информацию об абоненте системы */
public class Abonent {
    public int id;
    public String name;

    public Abonent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Переопределяем toString, чтобы в ComboBox отображались имена пользователей
    @Override
    public String toString() {return name;}
}
