package com.liu.tomcat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @desc 客户端
 * @author Liuweian
 * @createTime 2019年2月22日 下午4:48:45
 */
public class Client {
	public final static String ADDRESS = "127.0.0.1";
	public final static int PORT = 8878;
	
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			socket = new Socket(ADDRESS, PORT);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			
			writer.println("GET /index.html HTTP/1.1");

			StringBuffer response = new StringBuffer();
			String readResult = null;
			while ((readResult = reader.readLine()) != null) {
				response.append(readResult);
			}
			System.out.println("【客户端】接受到的信息：" + response.toString());
			
			
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(reader != null){
				try {
					reader.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(writer != null){
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			socket = null;				
		}
	}
}
