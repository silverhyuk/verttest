package kr.co.wcorp.main;

import java.util.Scanner;

import kr.co.wcorp.world.VertClientWorld;


public class VertClientMain {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 9999;
		VertClientWorld vertClientWorld = VertClientWorld.getInstance();
		
			String sendMessage = "";
			Scanner in = new Scanner(System.in);
			System.out.println("==============================================");
			System.out.println("======== if you want exit, input text 'quit'");
			System.out.println("==============================================");
			System.out.print("sendMessage >>");
			while((sendMessage = in.nextLine()) != null)
			{
				
				if(sendMessage.equals("halt") || sendMessage.equals("quit")){
					break;
				}
				if(vertClientWorld.start(host,port,sendMessage)){
				}
/*				if(vertClientWorld.sendMessage(sendMessage) == false){
					break;
				}*/
				
				System.out.println("================================================================");
				System.out.print("sendMessage >>");
			}
			
			/*vertClientWorld.fini();*/
			
			
		
		
		System.exit(0);
		
	}

}
