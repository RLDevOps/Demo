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
package org.olat.ims.qti.process.elements;

import java.util.List;

import org.dom4j.Element;
import org.olat.ims.qti.container.ItemContext;
import org.olat.ims.qti.process.QTIHelper;

/**
 *
 */
public class QTI_resprocessing {

	/**
	 * ims qti 1.2.1 <!ELEMENT resprocessing (qticomment? , outcomes , respcondition+)> also <!ELEMENT respcondition (qticomment? , conditionvar , setvar* ,
	 * displayfeedback*)> <!ATTLIST respcondition %I_Continue; %I_Title; > mit <!ENTITY % I_Continue " continue  (Yes | No )  'No'"> <!ELEMENT conditionvar (not | and |
	 * or | unanswered | other | varequal | varlt | varlte | vargt | vargte | varsubset | varinside | varsubstring | durequal | durlt | durlte | durgt | durgte)+>
	 * <!ELEMENT outcomes (qticomment? , (decvar , interpretvar*)+)> <!ELEMENT decvar (#PCDATA)> <!ATTLIST decvar %I_VarName; vartype (Integer | String | Decimal |
	 * Scientific | Boolean | Enumerated | Set ) 'Integer' defaultval CDATA #IMPLIED minvalue CDATA #IMPLIED maxvalue CDATA #IMPLIED members CDATA #IMPLIED cutvalue CDATA
	 * #IMPLIED >
	 * 
	 * @param n
	 * @param userContext
	 */
	public void evalAnswer(final Element n, final ItemContext userContext) {

		// first initialize all variables as stated in decvar elements
		userContext.resetVariables();

		// now eval all respconditions as long as continue="Yes"
		final QTI_respcondition respCondition = QTIHelper.getQTI_respcondition();
		final List el_respconditions = n.selectNodes("respcondition");
		final int size = el_respconditions.size();
		boolean cont = true;
		int i = 0;
		boolean hasBeenTrue = false;
		final EvalContext ect = new EvalContext();
		ect.setHasBeenTrue(hasBeenTrue);
		while (cont && i < size) {
			final Element el_respcond = (Element) el_respconditions.get(i);
			final String str_continue = el_respcond.attributeValue("continue");
			cont = (str_continue == null) ? false : str_continue.equals("Yes"); // default: No
			// eval conditions and set vars accordingly
			final boolean res = respCondition.process(el_respcond, userContext, ect);
			if (!res) {
				cont = true; // continue flag only effective if previous didn't match
			}
			hasBeenTrue = hasBeenTrue || res;
			ect.setHasBeenTrue(hasBeenTrue);
			i++;
		}

	}

}
