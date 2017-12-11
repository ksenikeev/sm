package ru.kpfu.icmit.serversm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для чтения http пакета
 *
 */
public class HTTPReader {

	/**
	 * Метод читает из входного потока сокета (is) данные построчно.
	 * Использовать метод readHeadersNextString, который считывает
	 * очередную порцию данных из потока до достижения символов \r\n конца строки.
	 *
	 * Метод должен вернуть список из строк
	 */
	public static ArrayList<String> readHTTPHeader(InputStream is) throws Throwable {
		ArrayList<String> result = new ArrayList<>();
		while(true) {
			String s = readHeadersNextString(is);
			System.out.println(s);
			if(s == null || s.trim().length() == 0) {
				break;
			}
			result.add(s);
		}
		return result;
	}

	/**
	 * Метод читает тело HTTP запроса из потока сокета.
	 * К моменту вызова этого метода заголовок HTTP запроса должен быть уже вычитан из потока.
	 * Т.е. последующий набор данных в потоке относится к телу запроса (если оно должно быть)
	 */
	public static String readHTTPBody(InputStream is, int bodySize) {
		byte[] msg = new byte[bodySize];
		try {
			is.read(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String msg_as_text = new String(msg);

		System.out.println("data from client: "+msg_as_text);
		return msg_as_text;
    }

	/**
	 * Метод читает очередную строку из потока, конец строки - обязательно комбинация \r\n
	 */
	public static String readHeadersNextString(InputStream is) throws IOException{
		String res=null;
		// Контролируем правильный конец строки, если встретили \r , то далее должен быть \n
		boolean therIsCR=false;
		byte[] buf = new byte[1024];
		int n = 0;
		int r;
		while(true){
			if(n<1024){
				// Читаем очередной байт
				r = is.read();
				if (r==-1){
					System.out.println("поток прервался"+" ThreadId: " + Thread.currentThread().getId());
					// поток прервался
					return res;
				} else if (r == '\r'){
					// получили возврат каретки \r, надо проконтролировать какой будет следующий символ
					therIsCR=true;
				} else if (r == '\n' && therIsCR){
					// получили конец строки - присваиваем и выходим
					buf[n] = (byte)r;
					if (res==null){
						res = new String(buf,0,n);
					} else {
						res += new String(buf,0,n);
					}
					return res;
				} else if (r == '\n' && !therIsCR){
					// Если концу строки не предшествует возврат каретки, то бросаем исключение
					// TODO реализовать соответствующее исключение!
				}
				// записываем в буфер очередной прочитанный байт
				buf[n++] = (byte)r;
			} else {
				// Если буфер исчерпан - формируем из него строку и сбрасываем счетчики, чтобы продолжить чтение
				if (res==null){
					res = new String(buf,0,n);
				} else {
					res += new String(buf,0,n);
				}
				n=0;
			}
		}
	}

}
