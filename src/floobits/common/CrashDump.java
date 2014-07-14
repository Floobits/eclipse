package floobits.common;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;

public class CrashDump implements Serializable {
    public String owner;
    public String workspace;
    public String dir;
    public String subject;
    public String username;
    public static String userAgent;
    public HashMap<String, String> message = new HashMap<String, String>();
    private static String editor = "";

    public static void setUA(String userAgent, String editor) {
        CrashDump.userAgent = userAgent;
        CrashDump.editor = editor;
    }
    public CrashDump(Throwable e, String owner, String workspace, String dir, String username) {
        this.owner = owner;
        this.workspace = workspace;
        this.dir = dir;
        this.username = username;
        message.put("sendingAt", String.format("%s", new Date().getTime()));
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        message.put("stack", sw.toString());
        message.put("description", e.getMessage());
        setContextInfo("%s died%s!");
    }

    public CrashDump(String description, String username) {
        this.username = username;
        message.put("sendingAt", String.format("%s", new Date().getTime()));
        message.put("description", description);
        setContextInfo("%s submitted an issues%s!");
    }

    protected void setContextInfo(String subjectText) {
        subject = String.format(subjectText, editor, username != null ? " for " + username : "");
    }
}
