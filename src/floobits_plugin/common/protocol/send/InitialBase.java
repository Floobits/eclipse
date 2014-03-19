package floobits.common.protocol.send;

import com.intellij.openapi.application.ApplicationInfo;
import floobits.common.Constants;
import floobits.common.protocol.Base;

public abstract class InitialBase implements Base {
    public String platform = System.getProperty("os.name");
    public String version = Constants.version;
    String client = ApplicationInfo.getInstance().getVersionName();
}
