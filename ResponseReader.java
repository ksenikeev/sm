package ru.kpfu.icmit.clientsm;

import java.io.InputStream;

public class ResponseReader {
	

	public ServerResponse read(InputStream is) {

		// Прием ответа
		// Создаем массив байт для приема длины сообщения
		byte[] response_size = new byte[4];
		// Принимаем от сервера 4 байта с длиной сообщения-ответа
		int r = is.read(response_size);
		// Преобразуем массив в целое число (в блоке try)
		int k = 0;
		try {
			k = byte4_2int(response_size);
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
		System.out.println("Длина сообщения от сервера " + k);
		// Готовим буфер (байтовый массив) для приема сообщения определенной
		// длины
		byte[] response_msg = new byte[k];
		// Считываем из потока сообщение в виде массива байт
		r = is.read(response_msg);
		System.out.println("Ответ от сервера " + new String(response_msg));
		return null;
	}
}
