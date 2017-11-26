package ru.kpfu.icmit.clientsm.fx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.kpfu.icmit.clientsm.Abonent;

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
        labelMessage.setVisible(false);
        textFieldLogin.setVisible(false);
        passwordField.setVisible(false);
        buttonLogin.setVisible(false);

        buttonNewMessages.setDisable(false);
        buttonSend.setDisable(false);

        comboBoxDst.getItems().add(new Abonent(1,"User1"));
        comboBoxDst.getItems().add(new Abonent(2,"User2"));
        comboBoxDst.getItems().add(new Abonent(3,"User3"));

        System.out.println("Button buttonLogin pressed. Login is "+textFieldLogin.getText());
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

    @FXML
    public void registgration(){
        System.out.println("Hiperlink registgration pressed");
    }
}
