package it.uniroma3.wcb.runner;

/**
 * Used by web server to start application.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi j-hard
 *
 */
import java.io.File;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;

public class WCBWebServer {

	public static final Logger logger = getLogger();

	public static final int PORT = 8083;
        //launch fromservice
        
	private String resourceBase = "../../webapp";
	
	private String extraClasspath = "../../configs";
 
        //launch from IDE
        /*
        private String resourceBase = "src/main/webapp";
	
	private String extraClasspath = "configs";
        */
	private String webappCtx = "/";

	public WCBWebServer(){
		super();
                File f = new File (resourceBase);
                
                if(!f.exists()||!f.isDirectory()){
                    resourceBase = "src/main/webapp";	
                    extraClasspath = "configs";
                }
                f = new File (extraClasspath);
                
                if(!f.exists()||!f.isDirectory()){
                    if(extraClasspath.equalsIgnoreCase("../../configs")){
                        extraClasspath = "configs";
                    }
                    else{
                        extraClasspath = "../../configs";
                    }
                }
		this.setResourceBase(resourceBase);
	}

	public static void main(String[] args) throws Exception {
                
		WCBWebServer ws = new WCBWebServer();
		ws.start(args);
	}

	public void start(String[] args) throws Exception {

		// now lets start the web server
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

		// Handler handler = server.getHandler();
		// if(handler instanceof WebAppContext){
		// System.out.println("found wac");
		// context.getSessionHandler().getSessionManager().setMaxInactiveInterval(60*30);
		// }

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
