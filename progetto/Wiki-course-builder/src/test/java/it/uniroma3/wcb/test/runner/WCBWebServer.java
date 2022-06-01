package it.uniroma3.wcb.test.runner;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;

/**
 * Used to start application from IDE environment.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class WCBWebServer {

	public static final Logger logger = getLogger();

	public static final int PORT = 8083;

	// Enable to execute from eclipse
	private String resourceBase = "src/main/webapp";
	private String extraClasspath = "configs";
	
	// Enable to execute from intellij
	//private String resourceBase = "Wiki-course-builder/src/main/webapp";
	//private String extraClasspath = "Wiki-course-builder/configs";
	
	private String webappCtx = "/";

	public WCBWebServer() throws Exception {
		super();
		this.setResourceBase(resourceBase);
	}

	public static void main(String[] args) throws Exception {

		WCBWebServer ws = new WCBWebServer();
		ws.start(args);
	}

	public void start(String[] args) throws Exception {

		// start the web server
		int port = PORT;
        if (args.length > 0) {
            String text = args[0];
            port = Integer.parseInt(text);
        }
        logger.info("Starting Web Server on port: {}", port);

        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setServer(server);

        WebAppContext context = new WebAppContext();
        context.setResourceBase(getResourceBase());
        context.setContextPath(getWebappContext());
        if (hasExtraClasspath()) {
            context.setExtraClasspath(getExtraClasspath());
        }

        context.setServer(server);
        server.setHandler(context);

        server.setConnectors(new Connector[]{connector});
        
		context.getServletContext().getContextHandler().setMaxFormContentSize(20000000);

		server.start();

		logger.info("Web Server successfully started!");
		logger.info(" - Context: {}", context.getContextPath());
		logger.info(" - Resource base: {}", context.getResourceBase());
		logger.info(" - Classpath: {}", context.getClassPath());
		logger.info(" - Extra Classpath: {}", context.getExtraClasspath());
	}

	public static Logger getLogger() {
        Throwable t = new Throwable();
        StackTraceElement directCaller = t.getStackTrace()[1];
        return org.slf4j.LoggerFactory.getLogger(directCaller.getClassName());
    }
	
	public void setResourceBase(String folder) {
		this.resourceBase = folder;
	}

	public String getResourceBase() {
		return this.resourceBase;
	}

	public void setExtraClasspath(String folders) {
		this.extraClasspath = folders;
	}

	public String getExtraClasspath() {
		return this.extraClasspath;
	}

	public boolean hasExtraClasspath() {
		return (this.extraClasspath != null);
	}

	public void setWebappContext(String ctx) {
		this.webappCtx = ctx;
	}

	public String getWebappContext() {
		return this.webappCtx;
	}
}
