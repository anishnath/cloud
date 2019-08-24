package k8;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import io.fabric8.kubernetes.client.Callback;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.utils.InputStreamPumper;



public class Exec extends WebSocketServer {

	public Exec(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public Exec(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		conn.send("Welcome to the server!"); // This method sends a message to
												// the new client
		broadcast("new connection: " + handshake.getResourceDescriptor()); // This
																			// method
																			// sends
																			// a
																			// message
																			// to
																			// all
																			// clients
																			// connected
		System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		broadcast(conn + " has left the room!");
		System.out.println(conn + " has left the room!");
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {
		
		
		System.out.println( conn + " :  message = " + message  + "  " + new Date());
		
		message =  message +"\n";
	
		   ExecutorService executorService = Executors.newSingleThreadExecutor();
	        try (
	                KubernetesClient client = K8Deployer.getClient();
	                ExecWatch watch = client.pods().inNamespace("thisisatest1").withName("jxqqfduwrv-54f79477d-rwbkf") 
	                        .redirectingInput()
	                        .redirectingOutput()
	                        .redirectingError()
	                        .redirectingErrorChannel()
	                        .exec();
	                InputStreamPumper pump = new InputStreamPumper(watch.getOutput(), new SystemOutCallback(conn)))
	        {

	            executorService.submit(pump);
	            watch.getInput().write(message.getBytes());
	            Thread.sleep(5 * 1000);
	        } catch (Exception e) {
	            throw KubernetesClientException.launderThrowable(e);
	        } finally {
	            executorService.shutdownNow();
	        }
		
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		broadcast(message.array());
		System.out.println(conn + ":2 " + message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
		if (conn != null) {
			// some errors like port binding failed may not be assignable to a
			// specific websocket
		}
	}

	@Override
	public void onStart() {
		System.out.println("Server started!");
		setConnectionLostTimeout(0);
		setConnectionLostTimeout(100);
	}

	public static void main(String[] args) throws Exception {

		int port = 8887; // 843 flash policy port
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception ex) {
		}
		Exec s = new Exec(port);
		s.start();
		System.out.println("ChatServer started on port: " + s.getPort());

		BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String in = sysin.readLine();
			s.broadcast(in);
			if (in.equals("exit")) {
				s.stop(1000);
				break;
			}
		}

		//
		// try (final KubernetesClient client = K8Deployer.getClient();
		// ExecWatch watch =
		// client.pods().inNamespace("thisisatest1").withName("jxqqfduwrv-54f79477d-rwbkf")
		// .readingInput(System.in)
		// .writingOutput(System.out)
		// .writingError(System.err)
		// .withTTY()
		// .usingListener(new SimpleListener())
		// .exec()){
		//
		//
		//
		// Thread.sleep(10 * 1000);
		// }
	}

	private static class SystemOutCallback implements Callback<byte[]> {
		
		private WebSocket conn;
		SystemOutCallback(WebSocket conn)
		{
			this.conn = conn;
		}
		
		@Override
		public void call(byte[] data) {
			String message = new String(data);
			System.out.println("Message -- " + message);
			
			this.conn.send(message.getBytes());

		}
	}
	
	 private static class SystemOutCallback1 implements Callback<byte[]> {
	        @Override
	        public void call(byte[] data) {
	            System.out.print(new String(data));
	        }
	    }

}
