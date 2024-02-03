package ao.it.chandsoft.whatsapp.controller;

import ao.it.chandsoft.whatsapp.dto.*;
import ao.it.chandsoft.whatsapp.enums.FileType;
import ao.it.chandsoft.whatsapp.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @PostMapping("/{channelId}/upload")
    public Object upload(@PathVariable String channelId,  @RequestParam("file") MultipartFile file) {
        return whatsAppService.upload(channelId, file);
    }

    @PostMapping("/{channelId}/text")
    public WhatsAppResponse sendText(@Validated @RequestBody Text text, @PathVariable String channelId) {
        return whatsAppService.send(channelId, text);
    }

    @PostMapping("/{channelId}/document")
    public WhatsAppResponse sendDocument(@RequestBody Document document, @PathVariable String channelId) {
        return whatsAppService.send(channelId, document);
    }

    @PostMapping("/{channelId}/image")
    public WhatsAppResponse sendImage(@RequestBody Image image, @PathVariable String channelId) {
        return whatsAppService.send(channelId, image);
    }

    @PostMapping("/{channelId}/video")
    public WhatsAppResponse sendVideo(@RequestBody Video video, @PathVariable String channelId) {
        return whatsAppService.send(channelId, video);
    }

    @PostMapping("/{channelId}/audio")
    public WhatsAppResponse sendAudio(@RequestBody Audio audio, @PathVariable String channelId) {
        return whatsAppService.send(channelId, audio);
    }

    @PostMapping("/{channelId}/sticker")
    public WhatsAppResponse sendSticker(@RequestBody Sticker sticker, @PathVariable String channelId) {
        return whatsAppService.send(channelId, sticker);
    }
}
