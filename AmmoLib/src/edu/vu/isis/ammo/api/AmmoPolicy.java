package edu.vu.isis.ammo.api;


/**
 * This API supersedes the AmmoDispatcher calls.
 * Those methods will still work but they are deprecated.
 * 
 * This abstract class supplies factory methods
 * @author phreed
 *
 */
abstract public class AmmoPolicy implements IAmmoPolicy {
	
	public abstract static class  NetLink implements IAmmoPolicy.NetLink {
		static public NetLink make(String name) { return null; }
	}

	public abstract static class  Gateway implements IAmmoPolicy.Gateway {
		static public Gateway make(String name) { return null; }	
	}
	
	public abstract static class  NetworkController implements IAmmoPolicy.NetworkController {
		static public NetworkController make(String name) { return null; }
	}
}
