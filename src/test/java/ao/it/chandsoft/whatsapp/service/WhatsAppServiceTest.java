package ao.it.chandsoft.whatsapp.service;

import ao.it.chandsoft.whatsapp.dto.Message;
import ao.it.chandsoft.whatsapp.dto.Text;
import ao.it.chandsoft.whatsapp.dto.WhatsAppResponse;
import ao.it.chandsoft.whatsapp.httpclient.WhatsAppClient;
import ao.it.chandsoft.whatsapp.service.impl.WhatsAppServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class WhatsAppServiceTest {

    @InjectMocks
    private WhatsAppServiceImpl whatsAppService;

    @Mock
    private WhatsAppClient whatsAppClient;

    private String phonenumber;
    private String channelID;
    private String messageId;
    private String whatsAppClienteResponse;


    @BeforeEach
    public void setup() {
        phonenumber = "244945263402";
        channelID = "142459815608774";
        messageId = "wamid.HBgMMjQ0OTQ1MjYzNDAyFQIAERgSN0ZEM0U4QTY0QzEwODY0REUxAA==";
        whatsAppClienteResponse = """
                {
                	"messaging_product":"whatsapp",
                	"contacts":[
                		{
                			"input":"244945263402",
                			"wa_id":"244945263402"
                		}
                	],
                	"messages":[
                		{
                			"id":"wamid.HBgMMjQ0OTQ1MjYzNDAyFQIAERgSQjEwRjIyMTNENTBEMDRFOTdEAA=="
                		}
                	]
                }
                """;
    }

    @Test
    public void sendTextMessage() {
        Message message = new Text(phonenumber, "The spartan never surrender");

        Mockito.when(whatsAppClient.sendMessage(channelID, message.toJson()))
                .thenReturn(whatsAppClienteResponse);

        WhatsAppResponse response = whatsAppService.send(channelID, message);

        Assertions.assertNotNull(response, "Message text not send");
        Assertions.assertNotNull(response.messageId(), "Message text id not send");

    }

}
