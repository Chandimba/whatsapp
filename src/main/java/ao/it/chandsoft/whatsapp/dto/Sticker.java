package ao.it.chandsoft.whatsapp.dto;

import java.util.Objects;

public class Sticker extends FileMessage implements Message {

    @Override
    public String toJson() {
        return super.toJson().replace("_TYPE_", "sticker").replace("_OPTIONAL_", "");
    }
}
