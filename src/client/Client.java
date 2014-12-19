package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lib.Connector;

public class Client {
	public static void main(String[] args) throws RemoteException,
			NotBoundException {

		Registry registry = LocateRegistry.getRegistry("localhost");
		Connector hello = (Connector) registry.lookup("server.Hello");
		System.out.println(hello.who());
	}
}
