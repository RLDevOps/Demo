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
 * Copyright (c) since 2004 at Multimedia- & E-Learning Services (MELS),<br>
 * University of Zurich, Switzerland.
 * <p>
 */
package org.olat.portfolio.ui.filter;

import org.olat.core.gui.control.Event;
import org.olat.portfolio.model.EPFilterSettings;

/**
 * Description:<br>
 * TODO: rhaag Class Description for PortfolioFilterChangeEvent
 * <P>
 * Initial Date: 15.07.2010 <br>
 * 
 * @author rhaag
 */
public class PortfolioFilterEditEvent extends Event {

	private final EPFilterSettings filterSettings;

	public PortfolioFilterEditEvent(final EPFilterSettings filterList) {
		super("portfoliofilteredit");
		this.filterSettings = filterList;
	}

	public EPFilterSettings getFilterList() {
		return filterSettings;
	}

}
