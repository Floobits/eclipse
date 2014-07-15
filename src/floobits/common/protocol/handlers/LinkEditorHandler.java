package floobits.common.protocol.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import floobits.common.Constants;
import floobits.common.FlooUrl;
import floobits.common.FloorcJson;
import floobits.common.Settings;
import floobits.common.interfaces.IContext;
import floobits.common.protocol.Connection;
import floobits.common.protocol.json.send.FlooRequestCredentials;
import floobits.utilities.Flog;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class LinkEditorHandler extends BaseHandler {
    private Runnable runnable = null;
    protected String token;
    private String host;

    public LinkEditorHandler(IContext context, String host) {
        super(context);
        UUID uuid = UUID.randomUUID();
        token = String.format("%040x", new BigInteger(1, uuid.toString().getBytes()));
        this.host = host;
    }

    public LinkEditorHandler(IContext context, String host, Runnable runnable) {
        this(context, host);
        this.runnable = runnable;
    }

    public void go() {
        super.go();
        url = new FlooUrl(host, null, null, Constants.defaultPort, true);
        conn = new Connection(this);
        conn.start();
        openBrowser();
    }


    @Override
    public void _on_data(String name, JsonObject obj) {
        if (!name.equals("credentials")) {
            return;
        }
        FloorcJson floorcJson = FloorcJson.getFloorcJsonFromSettings();
        HashMap<String, String> auth_host = floorcJson.auth.get(host);
        if (auth_host == null) {
            auth_host = new HashMap<String, String>();
            floorcJson.auth.put(host, auth_host);
        }
        JsonObject credentials = (JsonObject) obj.get("credentials");
        for (Map.Entry<String, JsonElement> thing : credentials.entrySet()) {
            String key = thing.getKey();
            if (key.equals("name")) {
                continue;
            }
            auth_host.put(key, thing.getValue().getAsString());
        }

        if (Settings.isAuthComplete(auth_host)) {
            Settings.write(context, floorcJson);
            context.statusMessage(String.format("Your account, %s, was successfully retrieved.  You can now share a project or join a workspace.", auth_host.get("username")));
        } else {
            runnable = null;
            context.errorMessage("Something went wrong while receiving data, please contact Floobits support.");
        }

        context.shutdown();

        if (runnable == null) {
            return;
        }
        context.readThread(runnable);
    }

    protected void openBrowser() {
        if(!Desktop.isDesktopSupported()) {
            context.errorMessage("Floobits can't use a browser on this system.");
            context.shutdown();
            return;
        }
        try {
            Desktop.getDesktop().browse(new URI(String.format("https://%s/dash/link_editor/intellij/%s", host, token)));
        } catch (IOException error) {
            context.shutdown();
            Flog.warn(error);
        } catch (URISyntaxException error) {
            context.shutdown();
            Flog.warn(error);
        }
    }

    @Override
    public void on_connect() {
        Flog.warn("Connected.");
        conn.write(new FlooRequestCredentials(token));
    }
}
