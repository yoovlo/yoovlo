package org.yoovlo.shr.java.req;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import org.yoovlo.shr.java.T;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.yoovlo.shr.java.T;
/*
mod parameter is commentplate for future versions
*/
public class HttpServletResponse_w_utils extends HttpServletResponseWrapper
{
	/*
	private Map<String, String[]> allParameters = null;
	*/
	//public final Map<String, String[]> modifiableParameters;

	/**
	 * Create a new request wrapper that will merge additional parameters into
	 * the request object without prematurely reading parameters from the
	 * original request.
	 * 
	 * @param request
	 * @param additionalParams
	 */
	/*
	public HttpServletResponse_w_utils(final HttpServletResponse response, 
		final Map<String, String[]> additionalParams)
	{
		super(response);
		modifiableParameters = new TreeMap<String, String[]>();
		modifiableParameters.putAll(additionalParams);
	}
	*/
	public HttpServletResponse_w_utils(final HttpServletResponse response) {
		super(response);
		//modifiableParameters = new TreeMap<String,String[]>();
	}
	public HttpServletResponse_w_utils(final HttpServletResponse response,final Req_info lri) {
		super(response);
		ri=lri;
	}
	public Req_info ri;
	@Override
	public void sendRedirect(String target) {
		ri.redir_set(target);
		//return target
	}
	/*
	public Req_info req_info;
	@Override
	public StringBuffer getRequestURL()
	{
		if(req_info.req.in_url!=null) {
			T.t("in_url:"+req_info.req.in_url);
			return new StringBuffer(req_info.req.in_url);
		}
		return super.getRequestURL();
	}

	@Override
	public String getParameter(final String name)
	{
		String[] strings = getParameterMap().get(name);
		if (strings != null)
		{
			return strings[0];
		}
		return super.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap()
	{
		if (allParameters == null)
		{
			allParameters = new TreeMap<String, String[]>();
			allParameters.putAll(super.getParameterMap());
			allParameters.putAll(modifiableParameters);
		}
		//Return an unmodifiable collection because we need to uphold the interface contract.
		T.t("getParameterMap "+allParameters.toString(),"req");
		return Collections.unmodifiableMap(allParameters);
	}

	@Override
	public Enumeration<String> getParameterNames()
	{
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(final String name)
	{
		return getParameterMap().get(name);
	}
	*/
}