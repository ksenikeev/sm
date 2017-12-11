package ru.kpfu.icmit.clientsm.fx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.kpfu.icmit.clientsm.Abonent;
import ru.kpfu.icmit.clientsm.Registration;
import ru.kpfu.icmit.clientsm.ServerResponse;

import java.util.ArrayList;

/**
 * Класс-контроллер реализует поведение элементов, описанных в clientfx.fxml
 */
public class Controller {

    /* Объявляем типы и имена полей, соответвующие описанию в clientfx.fxml */
    @FXML
    Label labelMessage;
    @FXML
    TextField textFieldLogin;

    @FXML
    PasswordField passwordField;

    @FXML
    Button buttonLogin;

    @FXML
    Button buttonNewMessages;

    @FXML
    ScrollPane scrollPanelNews;

    @FXML
    ComboBox comboBoxDst;

    @FXML
    TextArea textAreaMessage;

    @FXML
    Button buttonSend;

    @FXML
    Hyperlink hyperReg;

    private boolean btnStatusLogin = true;

    public String token;

    /* Дополнительные члены класса, необходимые в работе */

    //Id клиента, который нам присвоен в БД, должен возвращаться сервером в случае успешной аутентификации
    private int selfId;
    VBox content;

    int i=0;

    /*  */
    public Controller(){
        System.out.println("Constructor of class Controller is complete!");
    }

    /**
     *  Метод initialize() вызывается автоматически после загрузки макета и инициализации элементов
     *  Создаем контейнер VBox для отображения сообщений
     *  блокируем кнопки (до момента успешной аутентификации)
    */
    @FXML
    private void initialize() {
        content = new VBox();
        scrollPanelNews.setContent(content);

        buttonNewMessages.setDisable(true);
        buttonSend.setDisable(true);

        System.out.println("Initialze is complete!");
    }

    //TODO - реализовать полный функционал
    @FXML
    public void login(){
        ServerResponse resp=null;
        if (btnStatusLogin){
            //Действия связанные с аутентификацией
        } else {
            //Действия связанные с авторизацией
            resp = Registration.sendRegInfo(textFieldLogin.getText(),passwordField.getText());
            if (resp.responseCode!=200){
                System.out.println("Registration (http) error: "+resp.responseDescription);
                return;
            }
            Gson gson = new Gson();
            Token t = gson.fromJson(resp.content,Token.class);
            if (t.status.equals("success")){
                token = t.token;
                System.out.println("Registration success, token = "+token);
            } else{
                System.out.println("Registration error: "+t.description);
                return;
            }
        }

        ArrayList<Abonent> abn = Registration.getAbonentList(token);

        labelMessage.setVisible(false);
        textFieldLogin.setVisible(false);
        passwordField.setVisible(false);
        buttonLogin.setVisible(false);

        buttonNewMessages.setDisable(false);
        buttonSend.setDisable(false);

        comboBoxDst.getItems().add(new Abonent(1,"User1"));
        comboBoxDst.getItems().add(new Abonent(2,"User2"));
        comboBoxDst.getItems().add(new Abonent(3,"User3"));

        System.out.println("Button buttonLogin pressed. Login is "+textFieldLogin.getText()+passwordField.getText());
    }

    @FXML
    public void getNewMessage(){

        // Пример добавления "полученного" сообщения в область просмотра сообщений
        Label ltmp = new Label();
        ltmp.setStyle("-fx-background-color: Lime;");
        ltmp.setText("Сообщение "+(i++));
        content.getChildren().add(0,ltmp);
        System.out.println("Button buttonNewMesage pressed");
    }

    @FXML
    public void sendMessage(){
        System.out.println("Button sendMessage pressed "+textAreaMessage.getText());
        // Пример добавления "отправленного" сообщения в область просмотра сообщений
        Label ltmp = new Label();
        ltmp.setStyle("-fx-background-color: Cyan;");
        ltmp.setText(textAreaMessage.getText());
        content.getChildren().add(0,ltmp);
    }

    /**
     * Выбрана ссылка регистрация
     */
    @FXML
    public void registgration(){
        if (btnStatusLogin) {
            hyperReg.setText("Логин");
            buttonLogin.setText("Зарегистрировать");
        } else {
            hyperReg.setText("Регистрация");
            buttonLogin.setText("Войти");
        }
        btnStatusLogin = !btnStatusLogin;
        System.out.println("Hiperlink registgration pressed");
    }

    class Token {
        String status;
        String token;
        String description;
    }

    public static void main(String[] a){
        Gson gson = new Gson();
        Token t = gson.fromJson("{\"status\":\"error\",\"description\":\"описание_ошибки\"}",Token.class);
        System.out.println(t.token+" "+t.status+" "+t.description);
    }
}
