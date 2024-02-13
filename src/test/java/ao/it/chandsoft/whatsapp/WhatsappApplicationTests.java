package ao.it.chandsoft.whatsapp;

import ao.it.chandsoft.whatsapp.controller.WhatsAppController;
import ao.it.chandsoft.whatsapp.dto.Message;
import ao.it.chandsoft.whatsapp.dto.Text;
import ao.it.chandsoft.whatsapp.dto.WhatsAppResponse;
import ao.it.chandsoft.whatsapp.service.WhatsAppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WhatsappApplicationTests {

    @Autowired
    private WhatsAppService whatsAppService;
    @Autowired
    private WhatsAppController whatsAppController;
    private String phonenumber;
    private String channelID;

    @BeforeEach
    public void setup() {
        phonenumber = "244945263402";
        channelID = "142459815608774";
    }

    @Test
    public void sendTextMessage() {
        Message message = new Text(phonenumber, "The spartan never surrender");

        WhatsAppResponse response = whatsAppService.send(channelID, message);

        Assertions.assertNotNull(response, "Message text not send");
        Assertions.assertNotNull(response.messageId(), "Message text id not send");

    }

}
