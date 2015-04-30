package kr.co.wcorp.world;

import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.VoidHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

public class VertWorld implements Runnable{

	private static VertWorld instance = null;
	
	public static VertWorld getInstance(){
		if(instance == null){
			instance = new VertWorld();
			
		}
		return instance;
	}
	
	public void start(){
		Vertx vertx = VertxFactory.newVertx();
		
		 final HttpClient client = vertx.createHttpClient().setHost("www.google.co.kr").setPort(80);
		
		 HttpServer httpServer = vertx.createHttpServer();

	        httpServer.setAcceptBacklog(10240);
	        httpServer.setTCPNoDelay(true);
	        httpServer.setReuseAddress(true);
	        httpServer.setUsePooledBuffers(true);
	        httpServer.setSendBufferSize(10*1024);
	        httpServer.setReceiveBufferSize(10*1024);
	        httpServer.setSoLinger(0);
	        httpServer.setTrafficClass(0x08);
		 
		 httpServer.requestHandler(new Handler<HttpServerRequest>() {
		      public void handle(final HttpServerRequest req) {
		        System.out.println("Proxying request: " + req.uri());
		        System.out.println("Headers are: ");
		          for (Map.Entry<String, String> entry : req.headers()) {
		            System.out.println("entry Information :" + entry.getKey() + ":" + entry.getValue());
		          }
		          for (Map.Entry<String, String> params :req.params()){
		        	  System.out.println("params Information :" + params.getKey() + ":" + params.getValue());
		          }
		        
		        final HttpClientRequest cReq = client.request(req.method(), req.uri(), new Handler<HttpClientResponse>() {
		          public void handle(HttpClientResponse cRes) {
		            System.out.println("Proxying response: " + cRes.statusCode());
		            req.response().setStatusCode(cRes.statusCode());
		            req.response().headers().set(cRes.headers());
		            req.response().setChunked(true);
		            cRes.dataHandler(new Handler<Buffer>() {
		              public void handle(Buffer data) {
		                System.out.println("Proxying response body:" + data.getByteBuf().toString());
		                req.response().write(data);
		              }
		            });
		            cRes.endHandler(new VoidHandler() {
		              public void handle() {
		                req.response().end();
		              }
		            });
		          }
		        });
		        cReq.headers().set(req.headers());
		        cReq.setChunked(true);
		        req.dataHandler(new Handler<Buffer>() {
		          public void handle(Buffer data) {
		            System.out.println("Proxying request body:" + data.toString());
		            cReq.write(data);
		          }
		        });
		        req.endHandler(new VoidHandler() {
		          public void handle() {
		            System.out.println("end of the request");
		            cReq.end();
		          }
		        });
		      }
		      /*public void handle(HttpServerRequest req) {
		          System.out.println("Got request: " + req.uri());
		          System.out.println("Headers are: ");
		          for (Map.Entry<String, String> entry : req.headers()) {
		            System.out.println(entry.getKey() + ":" + entry.getValue());
		          }
		          req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
		          req.response().end("<html><body><h1>Hello from vert.x!</h1></body></html>");
		        }*/
		    }).listen(8080);
		 
		 
	}

	@Override
	public void run() {
			start();
			while(true){
				try {
					System.out.println("run");
					Thread.sleep(Integer.MAX_VALUE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
}
