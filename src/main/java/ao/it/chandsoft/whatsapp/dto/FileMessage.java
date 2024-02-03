package ao.it.chandsoft.whatsapp.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class FileMessage {

    protected String phonenumber;
    protected String id;
    protected String link;

    //@Override
    public String toJson() {
        String TEXT = """
                {
                	"messaging_product": "whatsapp",
                	"to": "_TO_",
                	"type": "_TYPE_",
                	"_TYPE_": {
                	    "id": "_ID_",
                		"link": "_LINK_"_OPTIONAL_
                	}
                }
                """.replace("_TO_", phonenumber);

        if(Objects.nonNull(id)) {
            TEXT = TEXT.replace("_ID_", id)
                    .replace("\"_LINK_\"", "null");
        } else {
            TEXT = TEXT.replace("_LINK_", link)
                    .replace("\"_ID_\"", "null");
        }

        return TEXT;
    }
}
