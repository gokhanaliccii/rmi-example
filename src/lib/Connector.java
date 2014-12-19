package lib;

import java.rmi.Remote;

public interface Connector extends Remote {
	public String who();
}
