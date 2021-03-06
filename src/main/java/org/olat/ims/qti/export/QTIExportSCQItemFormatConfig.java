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

package org.olat.ims.qti.export;

public class QTIExportSCQItemFormatConfig implements QTIExportItemFormatConfig {
	private boolean responseCols;
	private boolean positionsOfResponsesCol;
	private boolean pointCol;
	private boolean timeCols;

	public QTIExportSCQItemFormatConfig(final boolean resCols, final boolean posOfResCol, final boolean pointCol, final boolean timeCols) {
		this.responseCols = resCols;
		this.positionsOfResponsesCol = posOfResCol;
		this.pointCol = pointCol;
		this.timeCols = timeCols;
	}

	@Override
	public boolean hasResponseCols() {
		return responseCols;
	}

	@Override
	public boolean hasPositionsOfResponsesCol() {
		return positionsOfResponsesCol;
	}

	@Override
	public boolean hasPointCol() {
		return pointCol;
	}

	@Override
	public boolean hasTimeCols() {
		return timeCols;
	}

	@Override
	public void setPointCol(final boolean pointColConfigured) {
		this.pointCol = pointColConfigured;
	}

	@Override
	public void setPositionsOfResponsesCol(final boolean positionsOfResponsesColConfigured) {
		this.positionsOfResponsesCol = positionsOfResponsesColConfigured;
	}

	@Override
	public void setResponseCols(final boolean responseColsConfigured) {
		this.responseCols = responseColsConfigured;
	}

	@Override
	public void setTimeCols(final boolean timeColsConfigured) {
		this.timeCols = timeColsConfigured;
	}

}
