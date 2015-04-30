package kr.co.wcorp.world;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.net.NetClient;
import org.vertx.java.core.net.NetSocket;

public class VertClientWorld{

	private static VertClientWorld instance = null;
	
	private VertClientWorld(){
		
	}
	
	public static VertClientWorld getInstance(){
		if(instance == null){
			instance = new VertClientWorld();
			
		}
		return instance;
	}
	
	public boolean start(String host, int port, final String str){
		Vertx vertx = VertxFactory.newVertx();
		NetClient netClient = vertx.createNetClient();
		netClient.setConnectTimeout(3000);
		netClient.connect(port, host, new AsyncResultHandler<NetSocket>() {
		      public void handle(AsyncResult<NetSocket> asyncResult) {
		        if (asyncResult.succeeded()) {
		        	NetSocket socket = asyncResult.result();
		              socket.dataHandler(new Handler<Buffer>() {
		            public void handle(Buffer buffer) {
		              System.out.println("");
		              System.out.println("Net client receiving: " + buffer);
	
		            }
		          });

	              socket.write(new Buffer(str));
		        } else {
		          asyncResult.cause().printStackTrace();
		        }
		      }
		    });
		return true;
	}
	

	
}
