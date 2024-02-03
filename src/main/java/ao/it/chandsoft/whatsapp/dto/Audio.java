package ao.it.chandsoft.whatsapp.dto;

import java.util.Objects;

public class Audio extends FileMessage implements Message {

    @Override
    public String toJson() {
        return super.toJson().replace("_TYPE_", "audio").replace("_OPTIONAL_", "");
    }
}
