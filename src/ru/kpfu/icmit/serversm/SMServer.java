package ru.kpfu.icmit.serversm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 *
 */
public class SMServer extends Thread{
    Socket s;

    public SMServer(Socket s){
        this.s = s;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }
    
    public void run()  {
    	String bodyMessage="";
    	// Техническая информация из сокета
		System.out.println("NEW connection. ThreadId: " + currentThread().getId() +
			"\nsocket info: "+new Date()+"\n"+
			"address: "+s.getInetAddress().getHostAddress()+"\n"+
			"port: "+s.getLocalPort()+"\n"+
			"local address: "+s.getLocalAddress().getHostAddress()+"\n"+
			"local port: "+s.getLocalPort()+"\n"+
			"remote address: "+s.getRemoteSocketAddress().toString()+"\n");

    	// Подключаемся к потокам ввода-вывода ассоциированных с сокетом
    	try(InputStream is = s.getInputStream(); 
    			OutputStream os = s.getOutputStream();){

    		// Считываем заголовок запроса от клиента в список строк
    		ArrayList<String> lhs = HTTPReader.readHTTPHeader(is);

    		// Разбираем заголовок, если он не пуст
			HTTPClientHeader httpClientHeader=null;
			if (lhs!=null && lhs.size()>0) {
				httpClientHeader = HTTPClientHeader.parseHTTPHeader(lhs);

				// Если в заголовке есть ключ "Content-Length", то пытаемся прочитать тело
				if (httpClientHeader.contentLength>0){
					bodyMessage=HTTPReader.readHTTPBody(is,httpClientHeader.contentLength);
				}

				String data="";

				// !!!! ОБРАБОТКА ЗАПРОСА КЛИЕНТА !!!

				// Смотрим какой ресурс запросил клиент (из httpClientHeader.resourcePath)
				if (httpClientHeader.resourcePath.equals("/reguser")){

					//TODO реализовать обработку регистрации
				} else if (httpClientHeader.resourcePath.equals("/login")){

					//TODO реализовать обработку аутентификации
				} else if (httpClientHeader.resourcePath.equals("/sendmsg")){

					//TODO реализовать отправку сообщения
				} else if (httpClientHeader.resourcePath.equals("/getnewmsg")){
					//TODO реализовать отправку новых сообщений
				} else if (httpClientHeader.resourcePath.equals("/getallmsg")){
					//TODO реализовать отправку всех сообщений
				} else if (httpClientHeader.resourcePath.equals("/getusers")){
					//TODO реализовать отправку всех пользователей
				} else {
					//Если запрошен неизвестный ресурс возвращаем ошибку
					data ="HTTP/1.1 404 ResourceNotFound\r\n"+
							// general-header
							"Connection: close"+"\r\n"+
							"Date: "+new Date()+"\r\n"+
							// response-header
							"Server: ICMIT/0.0.1"+"\r\n"+
							// пустая строка отделяет заголовок от контента
							"\r\n" ;
				}

				//Пишем ответ клиенту в выходной поток сокета
                os.write(data.getBytes());
			} else {
				System.out.println("Заголовок пустой! ThreadId: " + currentThread().getId());
			}

                // завершаем соединение
                s.close();
            	System.out.println("socket close: "+new Date() +" ThreadId: " + currentThread().getId());
    	} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}   
    }    
}