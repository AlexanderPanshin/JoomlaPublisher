package gui;
import gui.json.JacksonjsonProcessor;
import gui.json.JoomlaPost;
import gui.model.JConnPos;
import gui.model.Model;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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


        area2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    //System.out.println( "We pressed enter" );
                    if( JoomlaPost.checkParentElementType( HTML.Tag.BODY,area2 ) ){
                        int caretPos = area2.getCaretPosition();
                        Element ep = doc.getParagraphElement( caretPos ).getParentElement();//Element ep = doc.getParagraphElement( caretPos ).getParentElement();
                        try {
                            e.consume();
                            doc.insertBeforeEnd(ep, "<br>");
                            area2.setCaretPosition(caretPos +2);//+1
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                Element elem =  doc.getParagraphElement(area2.getCaretPosition());
                int so = elem.getStartOffset();
                int eo = elem.getEndOffset();

                try{
                    //System.out.println(  area2.getText(so,eo-so).toString().length() );

                }catch( Exception ex){

                }


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        area2.setContentType("text/html;Charset=windows-1251");
        area2.getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
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

        textArea.setLayout(new GridLayout(2,1));
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
                System.out.println(text);
                area2.setContentType("text/html;Charset=UTF-8");
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

                System.out.println(text);
                area2.setContentType("text/plain;Charset=UTF-8");
                area2.setText(text);
            }
        });
        menuHtml.add(itemHtml);
        htmlgroup.add(itemHtml);
        //----

//LДобавляем поля со стилями
        JPanel stilMenu = new JPanel();
        stilMenu.setLayout(new BoxLayout(stilMenu, BoxLayout.X_AXIS));
        JPanel innerStilePanel  = new JPanel();
        JButton bold = new JButton("Жирный");

        JButton italik = new JButton("Курсив");

        JButton underlined = new JButton("Подчеркнутый");

        JButton readMore = new JButton("Разделитель");

        JButton addImage = new JButton("Добавить изображенеи");

        innerStilePanel.add(bold);
        innerStilePanel.add(italik);
        innerStilePanel.add(underlined);
        innerStilePanel.add(readMore);
        innerStilePanel.add(addImage);
        innerStilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        stilMenu.add(innerStilePanel);
        bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                int selectStart = area2.getSelectionStart();
                int selectEnd = area2.getSelectionEnd();


                String txt = area2.getSelectedText();
                if (selectStart != selectEnd) {
                    try {
                        doc.remove(selectStart, selectEnd-selectStart);
                        ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<b>"+txt+"</b>", 0, 0, HTML.Tag.B);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //italilk button
        italik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                int selectStart = area2.getSelectionStart();
                int selectEnd = area2.getSelectionEnd();

                String txt = area2.getSelectedText();
                if (selectStart != selectEnd) {
                    try {
                        doc.remove(selectStart, selectEnd-selectStart);
                        ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<i>"+txt+"</i>", 0, 0, HTML.Tag.I);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //underline button
        underlined.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                int selectStart = area2.getSelectionStart();
                int selectEnd = area2.getSelectionEnd();

                String txt = area2.getSelectedText();
                if (selectStart != selectEnd) {
                    try {
                        doc.remove(selectStart, selectEnd-selectStart);
                        ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<u>"+txt+"</u>", 0, 0, HTML.Tag.U);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //button insert more
        readMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                int selectStart = area2.getSelectionStart();
                int selectEnd = area2.getSelectionEnd();


                String txt = area2.getSelectedText();
                    try {
                        doc.remove(selectStart, selectEnd-selectStart);
                        ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<hr id=\"system-readmore\" /> ", 0, 0, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            }
        });
        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTMLDocument doc = (HTMLDocument) area2.getDocument();
                HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                int selectStart = area2.getSelectionStart();
                int selectEnd = area2.getSelectionEnd();
                //Множественный выбор
                String getUrlImage = "";
                String size = "";
                JOptionPane d = new JOptionPane();
                String selectPane[]={ "Загрузить с компьютера", "Указать (ссылку) URL", "Отмена"};
                int retour = d.showOptionDialog(TextArea.this, "Выберите тип загрузки", "Добавить изображение...", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selectPane, selectPane[1]);
                if(retour==0){
                    //тут мы загружаем с компа фтп и приблуды
                    JOptionPane.showMessageDialog(TextArea.this,"Кода пока нет");
                }
                if(retour == 1) {

                    getUrlImage = (String) JOptionPane.showInputDialog(TextArea.this, "Вставьте ссылку на изображение ...", "Введите URL", JOptionPane.INFORMATION_MESSAGE, null, null, "http://");
                    JLabel textPic = new JLabel("Введите желаемы размер или нажмите отмена.");
                    JLabel width = new JLabel("Ширнина  =  ");
                    JLabel height = new JLabel("Высота  =  ");
                    JTextField widthGet = new JTextField();
                    JTextField heightGet = new JTextField();
                    JPanel sizeImgOwer = new JPanel(new GridLayout(2,1));
                    JPanel textPanelImg = new JPanel(new GridLayout(1,1));
                    JPanel strokImg = new JPanel(new GridLayout(2,2));
                    textPanelImg.add(textPic);
                    strokImg.add(height);
                    strokImg.add(heightGet);
                    strokImg.add(width);
                    strokImg.add(widthGet);

                    sizeImgOwer.add(strokImg);
                    sizeImgOwer.add(textPanelImg);

                    int otvet = JOptionPane.showConfirmDialog(TextArea.this,sizeImgOwer,"Изменить размеры изображения ?",JOptionPane.OK_CANCEL_OPTION);
                    if(otvet == 0){
                        size =  "style=\"width:" + widthGet.getText() + "px;height:" + heightGet.getText() + "px;\"";
                    }


                }
                if(retour != 2) {
                    String txt = area2.getSelectedText();
                    try {
                        doc.remove(selectStart, selectEnd - selectStart);
                        ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<img src=\"" + getUrlImage + "\" alt=\"#\"" + size +">", 0, 0, HTML.Tag.IMG);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
//Конец полей со стилями
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
        textArea.add(stilMenu);
        contents.setLayout(new BorderLayout());

        contents.add(textArea,BorderLayout.PAGE_START);

        contents.add(new JScrollPane(area2),BorderLayout.CENTER);

        //низ добавляем
        downAra.add(categoriLine);
        downAra.add(downLabel);
        downAra.add(downButton);
        //проверка интера для БР



              JButton t111 =  new JButton("pppp111");
              t111.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      HTMLDocument doc = (HTMLDocument) area2.getDocument();
                      HTMLEditorKit ekit = (HTMLEditorKit) area2.getEditorKit();

                      int selectStart = area2.getSelectionStart();
                      int selectEnd = area2.getSelectionEnd();
                      Element startElem = doc.getParagraphElement(selectStart);
                      Element endElem = doc.getParagraphElement(selectEnd);

                      String txt = area2.getSelectedText();
                      if (selectStart != selectEnd) {
                          try {
                              doc.remove(selectStart, selectEnd-selectStart);
                              ekit.insertHTML((HTMLDocument) area2.getDocument(), selectStart, "<b>"+txt+"</b>", 0, 0, HTML.Tag.B);
                          } catch (Exception e1) {
                              e1.printStackTrace();
                          }
                      }
                  }
              });

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

setupMenuKey(this);
/// cлушатель кнопки тест



        // Выводим окно на экран
        setSize(600, 500);
        setVisible(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            final ArrayList<Integer> typedKeys = new ArrayList<>();
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

        return new JComboBox<String>(Model.mass);
    }

    //Актион для использования алт дабы попасть в меню
    private void setupMenuKey( JFrame frame) {
        Action menuAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRootPane rootPane = frame.getRootPane();
                JMenuBar jMenuBar = rootPane.getJMenuBar();
                JMenu menu = jMenuBar.getMenu(0);
                menu.doClick();
            }
        };

        JRootPane rootPane = frame.getRootPane();
        ActionMap actionMap = rootPane.getActionMap();

        final String MENU_ACTION_KEY = "expand_that_first_menu_please";
        actionMap.put(MENU_ACTION_KEY, menuAction);
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, 0, true), MENU_ACTION_KEY);
    }

    ///test


}
