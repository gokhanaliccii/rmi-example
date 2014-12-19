package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import filter.BlurFilter;
import filter.InvertFilter;
import filter.SharpFilter;

public class Server {

	public static void main(String[] args) throws Exception {
		System.out.println("RMI server started");

		(new Server()).new FilterListener().listen();

		System.out.println("server.RMI Server is ready.");

	}

	private class FilterListener extends Thread {

		private boolean listening;

		public void listen() {
			this.listening = true;
			start();
		}

		public void finish() {
			this.listening = false;
		}

		@Override
		public void run() {
			InvertFilter invertFilter = new InvertFilter();
			BlurFilter blurFilter = new BlurFilter();
			SharpFilter sharpenFilter = new SharpFilter();

			try {
				LocateRegistry.createRegistry(1099);

				Naming.rebind(invertFilter.getFilter().getPath(), invertFilter);
				Naming.rebind(blurFilter.getFilter().getPath(), blurFilter);
				Naming.rebind(sharpenFilter.getFilter().getPath(),
						sharpenFilter);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			while (listening) {

			}

		}
	}

}
