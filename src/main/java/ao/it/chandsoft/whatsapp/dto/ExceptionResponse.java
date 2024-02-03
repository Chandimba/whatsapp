package ao.it.chandsoft.whatsapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
public class ExceptionResponse implements Serializable {

    private int httpStatus;
    private String error;
    private String description;
    @Setter
    private Object details;

}
