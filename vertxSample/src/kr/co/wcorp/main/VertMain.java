package kr.co.wcorp.main;

import kr.co.wcorp.world.VertWorld;

public class VertMain {

	public static void main(String[] args) {
		VertWorld vertWorld = VertWorld.getInstance();
		Thread thread =  new Thread(vertWorld);
		
		System.out.println("Vert x Thread RUN !!" + thread.getName());
		thread.start();
		
/*		while(true){
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
	}

}
