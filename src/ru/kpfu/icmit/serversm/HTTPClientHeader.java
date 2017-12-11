package ru.kpfu.icmit.serversm;

import com.sun.deploy.util.SessionState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс описывает заголовок HTTP запроса от клиента
 *
 Пример заголовка:

 POST /reguser HTTP/1.1
 Host: 127.0.0.1:3128
 Content-Type: application/json
 Content-Length: 52

 */
public class HTTPClientHeader {

	// Поля HTTP заголовка
	public String method ;
	public String resourcePath="";
	public String  protocol;
	public String host;
	public int contentLength = 0;
	public String connection ="close";
	public String contentType = "application/json";

	/**
	 * Метод парсит HTTP заголовок, переданный в аргумент как список строк.
	 * Заполняются члены класса, если в заголовке находится соответствующий ключ.
	 * @author Иванов
	 */
	public static HTTPClientHeader parseHTTPHeader(ArrayList<String> httpHeader){
		HTTPClientHeader httpClientHeader = new HTTPClientHeader();
		String s = httpHeader.get(0);
		String [] as = s.split(" ");
		httpClientHeader.method = as[0];
		httpClientHeader.resourcePath = as[1];
		httpClientHeader.protocol = as[2];
		HashMap<String,String> kv = new HashMap<>();
		for(int i = 1;i < httpHeader.size();i++){
			String s1 = httpHeader.get(i);
			int k = s1.indexOf(':');
			if (k>0) {
				String ss1 = s1.substring(0, k).toUpperCase();
				String ss2 = s1.substring(k + 1).trim();
				kv.put(ss1, ss2);
			}
		}
		httpClientHeader.host = kv.get("Host");
		if (kv.get("Content-Length".toUpperCase()) != null) {
			httpClientHeader.contentLength = Integer.parseInt(kv.get("Content-Length".toUpperCase()).trim());
		}
		httpClientHeader.connection = kv.get("Connection".toUpperCase());
		httpClientHeader.contentType = kv.get("Content-Type".toUpperCase());
		return httpClientHeader;
	}

	public static void main(String[] args) {

		System.out.println("Host: 127.0.0.1:3128\r\n".indexOf(':'));
		System.out.println("Host: 127.0.0.1:3128\r\n".substring(0,"Host: 127.0.0.1:3128\r\n".indexOf(':')));
		System.out.println("Host: 127.0.0.1:3128\r\n".substring("Host: 127.0.0.1:3128\r\n".indexOf(':')+1).trim());
		ArrayList<String> aaaa1 = new ArrayList();
		aaaa1.add("POST /login HTTP/1.1\r\n");
		aaaa1.add("Host: 127.0.0.1:3128\r\n");
		aaaa1.add("Content-Type: application/json\r\n");
		aaaa1.add("Content-Length: 36\r\n");
		HTTPClientHeader ever = HTTPClientHeader.parseHTTPHeader(aaaa1);
		System.out.println(ever.resourcePath);
		System.out.println(ever.contentLength);
	}
}

// это делаю я
