
package edu.vu.isis.ammo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * When the Ammo request is not initialized correctly this exception is thrown.
 */
public class IncompleteRequest extends Exception {
    private static final long serialVersionUID = 3488702590494199738L;
    public static final Logger logger = LoggerFactory.getLogger("class.IncompleteRequest");
    

    public IncompleteRequest(Exception ex) {
        super(ex);
    }

    public IncompleteRequest(String msg) {
        super(msg);
    }

}
