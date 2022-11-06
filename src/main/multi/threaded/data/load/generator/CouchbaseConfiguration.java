package main.multi.threaded.data.load.generator;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;

import java.time.Duration;

public class CouchbaseConfiguration {

	public static String hostName = "couchbases://cb.qe65anopcr7rtkj.cloud.couchbase.com";
	public static String userName = "abhijeet";
	public static String password = "Password@P1";

	// Local connection
/*	public static String hostName = "127.0.0.1";
	public static String userName = "Administrator";
	public static String password = "password";*/

	// Cluster
	public static Cluster cluster = null;


	// Bucket
	public static Bucket shoppingCartBucket = null;

	// Scopes
	public static Scope shoppingScope = null;

	// Collections
	public static Collection usersColl = null;
	public static Collection productsColl = null;
	public static Collection cartsColl = null;
	public static Collection ordersColl = null;

	// Bucket and Scope variables
	public static String bucketName = "ShoppingCart";
	public static String shoppingScopeName = "Shopping";

	// Collections name variables
	public static String usersCollName = "users";
	public static String productsCollName = "products";
	public static String cartsCollName = "carts";
	public static String ordersCollName = "orders";

	static
	{
		cluster = Cluster.connect(hostName, userName, password);
		shoppingCartBucket = cluster.bucket(bucketName);
		shoppingCartBucket.waitUntilReady(Duration.parse("PT10S"));
		shoppingScope = shoppingCartBucket.scope(shoppingScopeName);
//		usersColl = shoppingScope.collection(usersCollName);
//		productsColl = shoppingScope.collection(productsCollName);
//		cartsColl = shoppingScope.collection(cartsCollName);
		ordersColl = shoppingScope.collection(ordersCollName);
	}
}
