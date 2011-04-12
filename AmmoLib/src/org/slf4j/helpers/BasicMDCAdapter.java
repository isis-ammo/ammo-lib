/* 
 * Copyright (c) 2004-2008 QOS.ch
 * All rights reserved.
 * 
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * 
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.spi.MDCAdapter;

/**
 * Basic MDC implementation, which can be used with logging systems that lack
 * out-of-the-box MDC support.
 * 
 * This code is largely based on logback's <a
 * href="http://svn.qos.ch/viewvc/logback/trunk/logback-classic/src/main/java/org/slf4j/impl/LogbackMDCAdapter.java">
 * LogbackMDCAdapter</a>.
 * 
 * @author Ceki Gulcu
 * @author Maarten Bosteels
 * 
 * @since 1.5.0
 */
public class BasicMDCAdapter implements MDCAdapter {

	@SuppressWarnings("unchecked")
	private InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();

	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHashMap() {
		return (HashMap<String, String>) inheritableThreadLocal.get();
	}
	@SuppressWarnings("unchecked")
	private void setHashMap(HashMap<String, String> hashMap) {
		inheritableThreadLocal.set(hashMap);
	}

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map.
   * Note that contrary to log4j, the <code>val</code> parameter can be null.
   * 
   * <p>
   * If the current thread does not have a context map it is created as a side
   * effect of this call.
   * 
   * @throws IllegalArgumentException
   *                 in case the "key" parameter is null
   */
public void put(String key, String val) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    HashMap<String,String> hashMap = this.getHashMap();
    if (hashMap == null) {
      hashMap = new HashMap<String, String>();
      this.setHashMap(hashMap);
    }
    hashMap.put(key, val);
  }

  /**
   * Get the context identified by the <code>key</code> parameter.
   */
  
public String get(String key) {
	HashMap<String,String> hashMap = this.getHashMap();
    if ((hashMap != null) && (key != null)) {
      return hashMap.get(key);
    } else {
      return null;
    }
  }

  /**
   * Remove the the context identified by the <code>key</code> parameter.
   */
  public void remove(String key) {
	HashMap<String,String> hashMap = this.getHashMap();
    if (hashMap != null) {
      hashMap.remove(key);
    }
  }

  /**
   * Clear all entries in the MDC.
   */
  public void clear() {
	HashMap<String,String> hashMap = this.getHashMap();
    if (hashMap != null) {
      hashMap.clear();
      // the InheritableThreadLocal.remove method was introduced in JDK 1.5
      // Thus, invoking clear() on previous JDK's will fail
      inheritableThreadLocal.remove();
    }
  }

  /**
   * Returns the keys in the MDC as a {@link Set} of {@link String}s The
   * returned value can be null.
   * 
   * @return the keys in the MDC
   */
  public Set<String> getKeys() {
	HashMap<String,String> hashMap = this.getHashMap();
    if (hashMap != null) {
      return hashMap.keySet();
    } else {
      return null;
    }
  }
  /**
   * Return a copy of the current thread's context map. 
   * Returned value may be null.
   * 
   */
  public Map<String,String> getCopyOfContextMap() {
	  HashMap<String,String> hashMap = this.getHashMap();
	  if (hashMap != null) {
		  return new HashMap<String,String>(hashMap);
	  } else {
		  return null;
	  }
  }

  @SuppressWarnings("unchecked")
  public void setContextMap(Map contextMap) {
	  HashMap<String,String> hashMap = this.getHashMap();
	  if (hashMap != null) {
		  hashMap.clear();
		  hashMap.putAll(contextMap);
	  } else {
		  hashMap = new HashMap<String,String>(contextMap);
		  this.setHashMap(hashMap);
	  }
  }

}
