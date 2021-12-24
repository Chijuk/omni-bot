package ua.omniway.dao.ot;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentMeta {
    private String oid;
    private String fileName;
    private int fileSize;
}
