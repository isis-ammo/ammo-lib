package edu.vu.isis.ammo.core.provider;


/**
 * Data Store Table definitions
 * 
 * The postal table records requests that data be sent out.
 * POSTed data is specifically named and distributed.
 * The retrieval and subscribe Relations record request that data be obtained.
 * RETRIEVAL data is obtained from a source.
 * SUBSCRIBEd data is obtained by topic.
 * The subscribe table is very similar to the interest table.
 * The interest table is for local interest the subscribe table is for remote interest.
 * 
 * The disposal table keeps track of the status of the delivery.
 * It is used in conjunction with the distribution policy.
 * The disposition table may have several entries for each request.
 * There is one row for each potential channel over which the 
 * request could be sent.
 * There will be one row for each potential channel from the policy.
 * As the channel is used it will be marked.
 * Once all clauses which may use a channel become true the 
 * clauses are removed.
 * The rule for disposition rows is cascade delete.
 */
public enum Relations {
	/** A record of the visibility of device/operator */
	PRESENCE(101, "presence"),
	/** A record of the interest which an operator has in a topic */
	CAPABILITY(102, "capability"),
	/** The state of the delivery channels */
	CHANNEL(103, "channel"),
	/** The base request */
	REQUEST(200, "request"),
	/** The base request disposal state */
	DISPOSAL(201, "disposal"),
	/** The postal request indicates that content is available for sharing */
	POSTAL(210, "postal"),
	/** The disposal status of postal requests */
	POSTAL_DISPOSAL(211, "postal_disposal"),
	/** The retrieval or pull request indicates that historical content is requested from a named source */
	RETRIEVAL(220, "retrieval"),
	/** The disposal status of retrieval requests */
	RETRIEVAL_DISPOSAL(221, "retrieval_disposal"),
	/** The subscribe request indicates that content posted is requested as it becomes available */
	SUBSCRIBE(230, "subscribe"),
	/** The disposal status of subscribe requests */
	SUBSCRIBE_DISPOSAL(231, "subscribe_disposal"),
	/** A record of acknowledgments */
	RECIPIENT(300, "recipient"),
	/** A record of invitations */
    INVITE(400, "invite");

	final public int nominal;
	final public String n;

	private Relations(int nominal, String name) {
		this.nominal = nominal;
		this.n = name;
	}

}
