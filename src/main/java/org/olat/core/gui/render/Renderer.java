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

package org.olat.core.gui.render;

import java.util.Iterator;
import java.util.Map;

import org.olat.core.defaults.dispatcher.StaticMediaDispatcher;
import org.olat.core.gui.GUIInterna;
import org.olat.core.gui.GlobalSettings;
import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.ComponentRenderer;
import org.olat.core.gui.components.Container;
import org.olat.core.gui.components.velocity.VelocityContainer;
import org.olat.core.gui.render.intercept.InterceptHandlerInstance;
import org.olat.core.gui.translator.Translator;
import org.olat.core.logging.AssertException;
import org.olat.core.util.WebappHelper;

/**
 * @author Felix Jost
 */
public class Renderer {

	/**
	 * <code>showDebugInfo</code>
	 */
	// public static boolean showDebugInfo = false;
	/**
	 * true: use mini style, false: use traditional style
	 */
	// public static boolean debugMiniStyle = true;

	private URLBuilder urlBuilder;
	private Translator translator;
	private Container renderContainer;
	private RenderResult renderResult;
	private GlobalSettings globalSettings;

	/**
	 * @param renderContainer is used as a starting node for searching a component
	 * @param translator the translator to be used to translate
	 * @param ubu
	 * @param renderResult
	 * @param globalSettings
	 * @return an instance of the renderer
	 */
	public static Renderer getInstance(Container renderContainer, Translator translator, URLBuilder ubu, RenderResult renderResult, GlobalSettings globalSettings) {
		return new Renderer(renderContainer, translator, ubu, renderResult, globalSettings);
	}

	private Renderer(Container renderContainer, Translator translator, URLBuilder ubu, RenderResult renderResult, GlobalSettings globalSettings) {
		this.renderContainer = renderContainer;
		this.translator = translator;
		this.urlBuilder = ubu;
		this.renderResult = renderResult;
		this.globalSettings = globalSettings;
	}

	/**
	 * Note: use only rarely - e.g. for static redirects to login screen. Renders a uri which is mounted to the webapp/ directory of your webapplication.
	 * <p>
	 * For static references (e.g. images which cannot be delivered using css): use renderStaticURI
	 * 
	 * @param target
	 * @param URI e.g. myspecialdispatcher/somestuff
	 */
	public static void renderNormalURI(StringOutput target, String URI) {
		String root = WebappHelper.getServletContextPath();
		target.append(root); // e.g /olat
		target.append("/");
		target.append(URI);
	}

	/**
	 * Note: use only rarely - all non-generic js libs and css classes should be included using JsAndCssComponent, and all images should be referenced with the css
	 * background-image capability. <br>
	 * renders a uri which is mounted to the webapp/static/ directory of your webapplication.
	 * 
	 * @param target
	 * @param URI e.g. img/specialimagenotpossiblewithcss.jpg
	 */
	public static void renderStaticURI(StringOutput target, String URI) {
		// forward to static dispatcher that knows how to deliver the static files!
		StaticMediaDispatcher.renderStaticURI(target, URI);
	}

	/**
	 * please do not use anymore!
	 * 
	 * @param target
	 * @param command
	 */
	public void renderCommandURI(StringOutput target, String command) {
		urlBuilder.buildURI(target, new String[] { VelocityContainer.COMMAND_ID }, new String[] { command });
	}

	/**
	 * renders the HTMLHeader-Part which this component comp needs. e.g. the richtext component needs some css and javascript libraries an velocity-container is a special
	 * case: it should collect the information from all the children that are visible since all could be renderer. since the actual rendering of a component depends on
	 * the page and is not know beforehand, we could include some css/js which turns out not to be needed in this request, but it is cached by the browser anyway, so it
	 * should not matter to much. a little advice if you want to do it perfectly : program the controller in such a way that they make a component invisible if not needed
	 * 
	 * @param sb
	 * @param source
	 * @see org.olat.core.gui.render.ui.ComponentRenderer
	 */
	public void renderBodyOnLoadJSFunctionCall(StringOutput sb, Component source, RenderingState rstate) {
		if (source != null && source.isVisible()) {
			ComponentRenderer cr = findComponentRenderer(source);
			cr.renderBodyOnLoadJSFunctionCall(this, sb, source, rstate);
		}
	}

	/**
	 * to be called by VelocityRenderDecorator only
	 * 
	 * @param sb
	 * @param source
	 */
	public void renderBodyOnLoadJSFunctionCall(StringOutput sb, Component source) {
		RenderingState rstate = new RenderingState();
		renderBodyOnLoadJSFunctionCall(sb, source, rstate);
	}

	/**
	 * @param sb
	 * @param source
	 */
	public void renderHeaderIncludes(StringOutput sb, Component source, RenderingState rstate) {
		if (source != null && source.isVisible()) {
			ComponentRenderer cr = findComponentRenderer(source);

			URLBuilder cubu = urlBuilder.createCopyFor(source);
			cr.renderHeaderIncludes(this, sb, source, cubu, translator, rstate);
		}
	}

	public void renderHeaderIncludes(StringOutput sb, Component source) {
		RenderingState rstate = new RenderingState();
		renderHeaderIncludes(sb, source, rstate);
	}

	/**
	 * searches the rootcomponent of this renderer and all children recursively for a component by its name
	 * 
	 * @param componentName
	 * @return
	 */
	public Component findComponent(String componentName) {
		Component source = renderContainer.getComponent(componentName);
		return source;
	}

	/**
	 * used by window.java for top rendering or subtree rendering
	 * 
	 * @param source
	 * @return
	 */
	public StringOutput render(Component source) {
		return render(source, null);
	}

	/**
	 * used by velocityrenderdecorator and method render(component) above
	 * 
	 * @param source
	 * @param args
	 * @return
	 */
	public StringOutput render(Component source, String[] args) {
		StringOutput sb = new StringOutput(2048);
		render(sb, source, args);
		return sb;
	}

	/**
	 * used by the renderer, and also by the panel and tabbedpane renderer to delegate rendering
	 * 
	 * @param sb
	 * @param source
	 * @param args
	 */
	public void render(StringOutput sb, Component source, String[] args) {
		GlobalSettings gset = getGlobalSettings();
		boolean ajaxon = gset.getAjaxFlags().isIframePostEnabled();
		// wrap with div's so javascript can replace this component by doing a document.getElementById(cid).innerHTML and so on.
		boolean domReplaceable = source.isDomReplaceable();
		boolean useSpan = source.getSpanAsDomReplaceable();
		boolean forceDebugDivs = gset.isIdDivsForced();

		if (source.isVisible()) {
			int lev = renderResult.getNestedLevel();
			if (lev > 42) throw new AssertException("components were nested more than 42 times, assuming endless loop bug: latest comp name: "
					+ source.getComponentName());
			Translator componentTranslator = source.getTranslator();

			// for ajax mode: render surrounding divs or spans as a positional
			// identifier for dom replacement
			if (domReplaceable && (ajaxon || forceDebugDivs)) {
				if (useSpan) {
					sb.append("<span id=\"o_c").append(source.getDispatchID()).append("\">");
				} else {
					sb.append("<div id=\"o_c").append(source.getDispatchID()).append("\">");
				}
			}

			ComponentRenderer cr = findComponentRenderer(source);
			URLBuilder cubu = urlBuilder.createCopyFor(source);

			// OLAT-1973
			if (GUIInterna.isLoadPerformanceMode()) {
				StringBuilder pathsb = new StringBuilder();
				Component cc = source;
				Container ccpar = cc.getParent();
				while (ccpar != null) { // omit content pane
					// find out name under which cc was registered in its parent - that is the relevant name, not the name of the component itself
					Map namedChildren = ccpar.getComponents();
					for (Iterator it_chd = namedChildren.keySet().iterator(); it_chd.hasNext();) {
						String chdName = (String) it_chd.next();
						Component chd = ccpar.getComponent(chdName);
						if (chd == cc) {
							// found -> append name
							pathsb.append(chdName).append('!');
							break;
						}
					}
					cc = ccpar;
					ccpar = cc.getParent();
				}
				cubu.setComponentPath(pathsb.toString());
			}

			renderResult.incNestedLevel();

			// ---- for gui debug mode, direct the rendering to a special componentrenderer
			InterceptHandlerInstance dhi = renderResult.getInterceptHandlerInstance();
			if (dhi != null) {
				cr = dhi.createInterceptComponentRenderer(cr);
			}

			try {
				int preRenderLength = sb.length();
				cr.render(this, sb, source, cubu, componentTranslator, renderResult, args);
				if (preRenderLength == sb.length()) {
					// Add bugfix for IE min-height on empty div problem: min-height does
					// not get applied when div contains an empty comment.
					// Affects IE6, IE7
					sb.append("<!-- empty -->");
				}
				source.setDirty(false);
			} catch (Exception e) {
				// in order to produce a decent error msg, we need to postpone the
				// exception
				renderResult.setRenderExceptionInfo("exception while rendering component '" + source.getComponentName() + "' (" + source.getClass().getName() + ") "
						+ source.getListenerInfo() + "<br />Message of exception: " + e.getMessage(), e);
			}
			renderResult.decNestedLevel();

			// close div for the javascript dom replacement

			if (ajaxon && domReplaceable) {
				if (useSpan) {
					sb.append("</span>");
				} else {
					sb.append("</div>");
				}
			}

		} else { // not visible
			if (domReplaceable && (ajaxon || forceDebugDivs)) {
				// render empty div's (or spans in special cases) as a place holder since this component may
				// be set to visible later on, and then needs to know its place in the
				// browser dom tree
				if (useSpan) {
					sb.append("<span id=\"o_c").append(source.getDispatchID()).append("\"></span>");
				} else {
					// Add bugfix for IE min-height on empty div problem: min-height does
					// not get applied when div contains an empty comment.
					// Affects IE6, IE7
					sb.append("<div id=\"o_c").append(source.getDispatchID()).append("\"><!-- empty --></div>");
				}
			}

		}
	}

	private ComponentRenderer findComponentRenderer(Component toRender) {
		ComponentRenderer cr = globalSettings.getComponentRendererFor(toRender);
		return cr;
	}

	/**
	 * returns e.g. "o_c123" where 123 is the component id
	 * 
	 * @param comp the component to get the id
	 */
	public static String getComponentPrefix(Component comp) {
		long did = comp.getDispatchID();
		return "o_c" + did;
	}

	/**
	 * @return URLBuilder
	 */
	public URLBuilder getUrlBuilder() {
		return urlBuilder;
	}

	/**
	 * @return Translator
	 */
	public Translator getTranslator() {
		return translator;
	}

	/**
	 * @return
	 */
	public GlobalSettings getGlobalSettings() {
		return globalSettings;
	}

	/**
	 * @return the current uri prefix, e.g. /demo/go (=the prefix for the window just being rendered)
	 */
	public String getUriPrefix() {
		return urlBuilder.getUriPrefix();
	}

}