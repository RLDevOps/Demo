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
package org.olat.core.commons.modules.bc.meta;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Compares the title of two MetaInfo objects based on a given collator
 * <P>
 * Initial Date: Jul 9, 2009 <br>
 * 
 * @author gwassmann
 */
public class MetaTitleComparator implements Comparator<MetaInfo> {
	private final Collator collator;

	public MetaTitleComparator(Collator collator) {
		this.collator = collator;
	}

	public MetaTitleComparator(Locale locale) {
		this.collator = Collator.getInstance(locale);
	}

	@Override
	public int compare(MetaInfo m1, MetaInfo m2) {
		if (m1 == null) return -1;
		if (m2 == null) return 1;

		String t1 = m1.getTitle();
		String t2 = m2.getTitle();

		if (t1 == null) return -1;
		if (t2 == null) return 1;

		// delegate to collator
		return collator.compare(t1, t2);
	}
}
