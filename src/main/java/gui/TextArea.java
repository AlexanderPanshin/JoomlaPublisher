package gui;
import gui.json.JacksonjsonProcessor;
import gui.json.JoomlaPost;
import gui.model.JConnPos;
import gui.model.Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;


public class TextArea extends JFrame {
    public JoomlaPost joomlaPost;
    public TextArea() {

        super("Joomla poster test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//Ключ и сайт
        final JTextField domainName = new JTextField();
        final JPasswordField passwordField = new JPasswordField();
        final JTextField namePost = new JTextField();
        final JButton buttonTest = new JButton("Тест соединения");
        JLabel testConn = new JLabel("Соединение не установленно ...",SwingConstants.RIGHT);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(4,2));
        northPanel.add(new JLabel("Домен сайта: ", SwingConstants.RIGHT));
        northPanel.add(domainName);
        northPanel.add(new JLabel("Key API: ", SwingConstants.RIGHT));
        northPanel.add(passwordField);
        northPanel.add(new JLabel("Название статьи",SwingConstants.RIGHT));
        northPanel.add(namePost);
        northPanel.add(testConn);
        northPanel.add(buttonTest);


//Ключ и сайт



        JEditorPane area2 = new JEditorPane();
        area2.setContentType("text/html;Charset=windows-1251");
        area2.setText("Тут <b> текст для </b>публикации !");


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());



        // Добавим поля в окно
        JPanel contents = new JPanel();
        JPanel textArea =  new JPanel();
        //нижння панель
        JPanel downAra = new JPanel();
        downAra.setLayout(new GridLayout(3,1));
        JLabel downLabel = new JLabel("Не опуликованно ...");
        JButton downButton = new JButton("Опубликовать!");
        downButton.setEnabled(false);
        downButton.setToolTipText("Кнопка будет активна в случаи успешного завершения теста");
        //нижняя панель

        textArea.setLayout(new GridLayout(1,1));
        setJMenuBar(menuBar);
        //категории
        //test teper eto bar
        JMenu menuHtml = new JMenu("Вид");
        ButtonGroup htmlgroup = new ButtonGroup();
        JRadioButton itemHtml = new JRadioButton("Текст");
        itemHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = area2.getText();
                area2.setContentType("text/html;Charset=CP1251");
                area2.setText(text);
            }
        });
        menuHtml.add(itemHtml);
        htmlgroup.add(itemHtml);
        itemHtml = new JRadioButton("Код");
        itemHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = area2.getText();
                area2.setContentType("text/plain;Charset=Cp1251");
                area2.setText(text);
            }
        });
        menuHtml.add(itemHtml);
        htmlgroup.add(itemHtml);
        //test teper eto bar
        JPanel categoriLine = new JPanel(new GridLayout(1,3));
        JComboBox<String> jb = craterCategoryMenu();
        JLabel categoriLabel = new JLabel("Выберите категорию  --->");
        jb.setEnabled(false);
        jb.setToolTipText("Кнопка будет активна в случаи успешного завершения теста");
        categoriLine.add(categoriLabel);
        categoriLine.add(jb);
        menuBar.add(menuHtml);

        //категории
        textArea.add(northPanel);
        contents.setLayout(new BorderLayout());

        contents.add(textArea,BorderLayout.PAGE_START);
        contents.add(new JScrollPane(area2),BorderLayout.CENTER);

        //низ добавляем
        downAra.add(categoriLine);
        downAra.add(downLabel);
        downAra.add(downButton);
        //слушатель категорий
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                for(Map.Entry<Integer,String> entry : Model.mapCatefory.entrySet()){
                    if(entry.getValue().equals(item)){
                        joomlaPost.setCatid(entry.getKey());
                    }
                }
            }
        });
        //слушатель категорий
        contents.add(downAra,BorderLayout.PAGE_END);
        setContentPane(contents);
        //слушатель кнопки отправить
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Model.getLoad){
                    joomlaPost.setTitle(namePost.getText());
                    joomlaPost.setArticletext(area2.getText());
                    String nameCategori = (String) jb.getSelectedItem();
                    if (!nameCategori.equals("Пусто ...")){
                        for(Map.Entry<Integer,String> entry: Model.mapCatefory.entrySet()){
                            if(nameCategori.equals(entry.getValue())){
                                joomlaPost.setCatid(entry.getKey());
                                Model.jconn.setJsonBody(JacksonjsonProcessor.toJson(joomlaPost));
                                Model.jconn.goPost();
                                downLabel.setText("Опубликованно!");
                            }
                        }
                    }
                }else {
                    downLabel.setText("Соединение не протестированно ...");
                }

            }
        });
        //слушатель кнопки отправит
//Слушатель кнопки тест

        buttonTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!domainName.getText().equals("") & passwordField.getPassword().length!=0 & !namePost.getText().equals("")) {
                    joomlaPost = new JoomlaPost(area2.getText(),namePost.getText());
                    Model.jconn = new JConnPos(new String(passwordField.getPassword()),domainName.getText());
                    Integer respone;
                    testConn.setText((respone = Model.jconn.selectCatId()).toString());
                    jb.removeAllItems();
                    for(String s: Model.arr){
                        jb.addItem(s);
                    }
                    if (respone == 5){
                        Model.getLoad = true;
                        downButton.setEnabled(true);
                        jb.setEnabled(true);
                    }
                } else {
                    testConn.setText("Заполните все поля!");
                }
            }
        });
//слуштель кнопки тест


        // Выводим окно на экран
        setSize(500, 400);
        setVisible(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            ArrayList<Integer> typedKeys = new ArrayList<>();
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    typedKeys.add(e.getKeyCode());
                }else  typedKeys.clear();

                if (e.getKeyCode() == KeyEvent.VK_N && e.getID() == KeyEvent.KEY_PRESSED) {
                    if(typedKeys.contains(KeyEvent.VK_INSERT)){
                        System.out.println("Test");
                        typedKeys.clear();
                }
//тут действие при нажатии
                    return true;
                }else {
//тут можно не чего и не писать .
                    return false;
                }
            }
        });


    }
    private JMenu createFileMenu()
    {
        // Создание выпадающего меню
        JMenu file = new JMenu("Файл");
        // Пункт меню "Открыть" с изображением
        JMenuItem open = new JMenuItem("Открыть");
        // Пункт меню из команды с выходом из программы
        JMenuItem exit = new JMenuItem("Выход");
        // Добавим в меню пункта open
        file.add(open);
        // Добавление разделителя
        file.addSeparator();
        file.add(exit);

        return file;
    }
    private JComboBox<String> craterCategoryMenu(){

        JComboBox<String> jComboBox = new JComboBox<String>(Model.mass);
        return jComboBox;
    }
}
