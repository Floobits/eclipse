package floobits.common.protocol.json.receive;

import floobits.common.protocol.Base;

public class DeleteBuf implements Base {
    public Integer id;
    public String name = "delete_buf";
    public Boolean unlink = false;

    public DeleteBuf(Integer id, Boolean unlink) {
        this.id = id;
        this.unlink = unlink;
    }
}
