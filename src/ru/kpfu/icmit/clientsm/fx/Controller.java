package ru.kpfu.icmit.clientsm.fx;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.kpfu.icmit.clientsm.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-контроллер реализует поведение элементов, описанных в clientfx.fxml
 */
public class Controller {

    /* Объявляем типы и имена полей, соответвующие графическим элементам (кнопкам, тектовым полям, ...) и их
        описанию в clientfx.fxml
        аннотация @FXML позволяет установить соответствие между переменной и ее описанием в clientfx.fxml
    */
    @FXML
    Label labelMessage;
    @FXML
    Label labelStatus;
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

    // Состояние кнопки buttonLogin
    private boolean btnStatusLogin = true;
    // Токен для идентификации пользователя при общении с сервером (128 битное число в строковом представлении)
    public String token;
    private HTTPSender httpSender;

    //Id клиента, который нам присвоен в БД, должен возвращаться сервером в случае успешной аутентификации
    VBox content;
    // Список абонентов
    private ArrayList<Abonent> abns;

     /**
     *  Метод initialize() вызывается автоматически после загрузки макета и инициализации элементов
     *  Создаем элемент графического интерфейса - контейнер VBox для отображения сообщений
     *  блокируем кнопки (до момента успешной аутентификации)
    */
    @FXML
    private void initialize() {
        content = new VBox();
        scrollPanelNews.setContent(content);

        buttonNewMessages.setDisable(true);
        buttonSend.setDisable(true);
        // Инициализируем класс для отправки данных
        httpSender = new HTTPSender("127.0.0.1", "3128");
        System.out.println("Initialze is complete!");
    }

    /**
     * Аутентификация абонента
     * @autor Bikusheva
     */
    @FXML
    public void login(){
        ServerResponse resp=null;
        if (btnStatusLogin){
            //аутентификация
            resp = httpSender.sendLoginInfo(textFieldLogin.getText(),passwordField.getText());
        } else {
            //регистрация
            resp = httpSender.sendRegistration(textFieldLogin.getText(), passwordField.getText());
        }
        // Если от сервера пришла ошибка
        if (resp.responseCode!=200){
            labelStatus.setText("Ошибка аутентификации (регистрации)  !"+resp.responseDescription);
            System.out.println("Login (Registration) (http) error: "+resp.responseDescription);
            //покидаем метод
            return;
        }
        // При положительном ответе сервер должен вернуть токен для дальнейшего общения с ним
        // в JSON объекте в resp.content
        Gson gson = new Gson();
        Token t = gson.fromJson(resp.content,Token.class);
        if (t.status.equals("success")){
            token = t.token;
            System.out.println("Login/Registration success, token = "+token);
        } else{
            System.out.println("Login/Registration error: "+t.description);
            return;
        }

        // Меняем состояние элементов интерфейса
        labelMessage.setVisible(false);
        textFieldLogin.setVisible(false);
        passwordField.setVisible(false);
        buttonLogin.setVisible(false);

        buttonNewMessages.setDisable(false);
        buttonSend.setDisable(false);

        // После успешной аутентификации или логина запрашиваем список абонентов
        abns = httpSender.getAbonentList(token);
        for (Abonent a: abns) {
            comboBoxDst.getItems().add(a);
        }
    }

    // Запрос новых сообщений
    @FXML
    public void getNewMessage(){
        List<ClientMessage> cml = httpSender.getMessages(token);

        // Добавление сообщения в область просмотра сообщений
        for( ClientMessage cm : cml) {
            Label ltmp = new Label();
            ltmp.setStyle("-fx-background-color: Lime;");
            ltmp.setText(cm.datemsg+", "+
                    cm.from.name+": " + cm.content);
            content.getChildren().add(0, ltmp);
            Messages.messages.add(cm);
        }
        System.out.println("Button buttonNewMesage pressed");
    }

    // Отправка сообщения
    @FXML
    public void sendMessage(){
        // Пример добавления "отправленного" сообщения в область просмотра сообщений
        Label ltmp = new Label();
        ltmp.setStyle("-fx-background-color: Cyan;");
        ltmp.setText(textAreaMessage.getText());
        content.getChildren().add(0,ltmp);
        comboBoxDst.getValue();
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
