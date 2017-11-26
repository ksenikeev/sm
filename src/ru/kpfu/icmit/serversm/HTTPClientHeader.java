package ru.kpfu.icmit.serversm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает заголовок HTTP запроса от клиента
 *
 Пример заголовка:

 POST /reguser HTTP/1.1
 Host: 127.0.0.1:3128
 Content-Type: application/json
 Content-Lenght: 52
 */
public class HTTPClientHeader {

	// Поля HTTP заголовка
	public String method;
	public String resourcePath="";
	public String protocol;
	public String host;
	public int contentLength = 0;
	public String connection ="close";
	public String contentType = "application/json";

	private String[] headerKeys={"Host","Content-Type","Content-Length"};

	/**
	 * Метод парсит HTTP заголовок, переданный в аргумент как список строк.
	 * Заполняются члены класса, если в заголовке находится соответствующий ключ.
	 */
	public static HTTPClientHeader parseHTTPHeader(ArrayList<String> httpHeader){
		HTTPClientHeader httpClientHeader = new HTTPClientHeader();
		//TODO необходимо реализовать

		return httpClientHeader;
	}
	
}
