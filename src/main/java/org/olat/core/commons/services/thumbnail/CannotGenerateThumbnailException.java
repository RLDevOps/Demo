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
 * Copyright (c) frentix GmbH<br>
 * http://www.frentix.com<br>
 * <p>
 */

package org.olat.core.commons.services.thumbnail;

/**
 * Description:<br>
 * <P>
 * Initial Date: 30 mar. 2010 <br>
 * 
 * @author srosse, stephane.rosse@frentix.com
 */
public class CannotGenerateThumbnailException extends Exception {

	private static final long serialVersionUID = 5750928357208513434L;

	public CannotGenerateThumbnailException() {
		super();
	}

	public CannotGenerateThumbnailException(String message) {
		super(message);
	}

	public CannotGenerateThumbnailException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotGenerateThumbnailException(Throwable cause) {
		super(cause);
	}
}
