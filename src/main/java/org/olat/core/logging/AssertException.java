/**
 * OLAT - Online Learning and Training<br>
 * http://www.olat.org
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Copyright (c) 1999-2006 at Multimedia- & E-Learning Services (MELS),<br>
 * University of Zurich, Switzerland.
 * <p>
 */

package org.olat.core.logging;

/**
 * Description:<br>
 * used when a bug is detected e.g. if (pos > array.length) throw new AssertException
 * 
 * @author Felix Jost
 */
public class AssertException extends OLATRuntimeException {

	/**
	 * Includes throwable.
	 * 
	 * @param logMsg
	 * @param cause
	 */
	public AssertException(String logMsg, Throwable cause) {
		super(AssertException.class, logMsg, cause);
	}

	/**
	 * Generic signature.
	 * 
	 * @param logMsg
	 */
	public AssertException(String logMsg) {
		this(logMsg, new Exception("assertionstackgenerator"));
	}

}
