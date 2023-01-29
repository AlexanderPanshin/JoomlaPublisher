package gui;

import javax.swing.*;

public class JoomReg {
    public static String otvetJ4() {
        int i = JOptionPane.showConfirmDialog(null, "Включить модуль дабавление новости Joomla?Joomla V4", "Garmony Parser", 1);
        if (i == 0) {
            return "да";
        }
        if (i == 1) {
            return "нет";
        } else return "выход";
    }
    public static String tokenJoomla(){
        String otvet = JOptionPane.showInputDialog(null,"Введите Токен API","Garmony Parser",1);
        return otvet;
    }
    public static String urlJoomla(){
        String otvet = JOptionPane.showInputDialog(null,"Введите адрес сайта","Garmony Parser",1);
        return otvet;
    }
}
