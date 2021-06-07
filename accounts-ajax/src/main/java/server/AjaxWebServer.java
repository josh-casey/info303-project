package server;

import java.io.IOException;
import org.jooby.Jooby;
import org.jooby.Results;

public class AjaxWebServer extends Jooby {

	public AjaxWebServer() {
		// set the port that the web server will use
		port(8088);

		// stop the log filling with 404 errors for favicon requests
		get("/favicon.ico", () -> Results.noContent());

		// allow all assets in the web resources folder to be requested
		assets("/**");

		// make index.html the root page
		assets("/", "index.html");
	}

	public static void main(String[] args) throws IOException {
		System.out.println("\n*** AJAX Client Web Server ***");
		new AjaxWebServer().start();
	}

}
