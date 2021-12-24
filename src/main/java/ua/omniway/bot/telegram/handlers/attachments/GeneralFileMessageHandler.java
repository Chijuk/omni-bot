package ua.omniway.bot.telegram.handlers.attachments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.FileMessageHandler;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.services.app.AttachmentInfoService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import java.util.List;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.formatAttachmentSize;

@Slf4j
@Component
public class GeneralFileMessageHandler implements FileMessageHandler {
    private final AttachmentInfoService attachmentService;
    private final List<AttachmentListener> listeners;

    @Autowired
    public GeneralFileMessageHandler(AttachmentInfoService attachmentService,
                                     @Autowired(required = false) List<AttachmentListener> listeners) {
        this.attachmentService = attachmentService;
        this.listeners = listeners;
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    public List<AttachmentListener> getListeners() {
        return listeners;
    }

    @Override
    public boolean onFileSent(AbstractTelegramBot bot, Update update, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {}", context.getUser().getId(), this.getClass().getSimpleName());
        final Message message = update.getMessage();
        final String lang = context.getDbUser().getSetting().getLanguage();
        boolean attachmentIsOversize = false;
        int attachmentSize = 0;
        if (message.isReply()) {
            try {
                AttachmentInfo attachment = attachmentService.findByReplyMessageId(message.getReplyToMessage().getMessageId());
                if (attachment != null) {
                    if (message.hasDocument()) {
                        final Document document = message.getDocument();
                        attachmentSize = document.getFileSize();
                        if (isSizeAccepted(document.getFileSize())) {
                            attachment.setOid(document.getFileId());
                            attachment.setFileSize(document.getFileSize());
                            attachment.setFileName(document.getFileName());
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (message.hasPhoto()) {
                        final List<PhotoSize> photo = message.getPhoto();
                        photo.sort((o1, o2) -> o2.getFileSize() - o1.getFileSize());
                        final PhotoSize largestPhoto = photo.get(0);
                        attachmentSize = largestPhoto.getFileSize();
                        if (isSizeAccepted(largestPhoto.getFileSize())) {
                            attachment.setOid(largestPhoto.getFileId());
                            attachment.setFileSize(largestPhoto.getFileSize());
                            attachment.setFileName(System.currentTimeMillis() + ".jpg");
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (message.hasVideo()) {
                        final Video video = message.getVideo();
                        attachmentSize = video.getFileSize();
                        if (isSizeAccepted(video.getFileSize())) {
                            attachment.setOid(video.getFileId());
                            attachment.setFileSize(video.getFileSize());
                            attachment.setFileName(video.getFileName());
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (message.hasAudio()) {
                        final Audio audio = message.getAudio();
                        attachmentSize = audio.getFileSize();
                        if (isSizeAccepted(audio.getFileSize())) {
                            attachment.setOid(audio.getFileId());
                            attachment.setFileSize(audio.getFileSize());
                            attachment.setFileName(audio.getFileName());
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (message.hasVoice()) {
                        final Voice voice = message.getVoice();
                        attachmentSize = voice.getFileSize();
                        if (isSizeAccepted(voice.getFileSize())) {
                            attachment.setOid(voice.getFileId());
                            attachment.setFileSize(voice.getFileSize());
                            attachment.setFileName(System.currentTimeMillis() + "." + voice.getMimeType().split("/")[1]);
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (message.hasVideoNote()) {
                        final VideoNote videoNote = message.getVideoNote();
                        attachmentSize = videoNote.getFileSize();
                        if (isSizeAccepted(videoNote.getFileSize())) {
                            attachment.setOid(videoNote.getFileId());
                            attachment.setFileSize(videoNote.getFileSize());
                            attachment.setFileName(System.currentTimeMillis() + ".mp4");
                        } else {
                            attachmentIsOversize = true;
                        }
                    }
                    if (attachmentIsOversize) {
                        sendMessageOnOversize(bot, update.getMessage().getChatId(), context.getDbUser().getSetting().getLanguage(), attachmentSize);
                    } else {
                        attachmentService.saveInfo(attachment);
                        log.info("Added file OID: {}", attachment.getOid());
                        if (listeners != null) {
                            listeners.forEach(attachmentListener -> attachmentListener.onAttachmentInfoAdded(attachment, context));
                        }
                    }
                    return true;
                } else {
                    bot.execute(SendMessage.builder()
                            .chatId(String.valueOf(update.getMessage().getChatId()))
                            .text(L10n.getString("bot.error.wrongReplyMessage", lang))
                            .parseMode(ParseMode.HTML)
                            .build());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                bot.execute(SendMessage.builder().chatId(Long.toString(message.getChatId()))
                        .text(L10n.getString("bot.error.general", lang))
                        .build());
            }
        }
        return false;
    }

    private void sendMessageOnOversize(AbstractTelegramBot bot, Long chatId, String lang, int attachmentSize) throws TelegramApiException {
        log.warn("Attachment has unaccepted size: {}", formatAttachmentSize(attachmentSize));
        bot.execute(SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(String.format(L10n.getString("bot.error.attachmentOversize", lang), formatAttachmentSize(attachmentSize)))
                .parseMode(ParseMode.HTML)
                .build());
    }
}
