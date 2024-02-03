package ao.it.chandsoft.whatsapp.service.impl;

import ao.it.chandsoft.whatsapp.dto.Message;
import ao.it.chandsoft.whatsapp.dto.WhatsAppResponse;
import ao.it.chandsoft.whatsapp.enums.FileType;
import ao.it.chandsoft.whatsapp.exception.MediaFileException;
import ao.it.chandsoft.whatsapp.httpclient.WhatsAppClient;
import ao.it.chandsoft.whatsapp.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private final WhatsAppClient whatsAppClient;

    @Override
    public Object upload(String channelId, MultipartFile file) {
        String filename = file.getOriginalFilename();
        FileType fileType = FileType.getFileTypeByExtension(filename.substring(filename.lastIndexOf(".")));

        if(fileType.getMaxSizeB() < file.getSize()) {
            throw new MediaFileException("Media file size too big. Max file size we currently support: " + fileType.getMaxSizeMB() + fileType.getUnit());
        }

        return whatsAppClient.uploadFile(channelId, "whatsapp", file, file.getContentType());
    }

    public WhatsAppResponse send(String channelId, Message message) {
        String response = whatsAppClient.sendMessage(channelId, message.toJson());
        return whatsAppResponse(response);
    }

    private WhatsAppResponse whatsAppResponse(String jsonResponse) {
        return new WhatsAppResponse(
                jsonResponse.substring(jsonResponse.lastIndexOf(":\"") + 2, jsonResponse.lastIndexOf("\""))
        );
    }
}
