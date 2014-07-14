package floobits.common.protocol.json.receive;

import floobits.common.protocol.buf.Buf;
import floobits.common.protocol.Base;

public class SetBuf implements Base {
    public String name = "set_buf";
    public Integer id;
    public String buf;
    public String md5;
    public String encoding;

    public SetBuf(Buf buf) {
        this.md5 = buf.md5;
        this.id = buf.id;
        this.buf = buf.serialize();
        this.encoding = buf.encoding.toString();
    }
}
