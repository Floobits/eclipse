package floobits.common.protocol.receive;

import floobits.common.protocol.Base;

public class GetBufResponse implements Base {
    public Integer id;
    public String path;
    public String buf;
    public String encoding;
    public String md5;
}
