package ua.omniway.bot.telegram.handlers.attachments;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.AttachmentInfo;

public interface AttachmentListener {
    void onAttachmentInfoAdded(AttachmentInfo attachmentInfo, DefaultDbContext context);

    void onAttachmentInfoDeleted(AttachmentInfo attachmentInfo, Message message, DefaultDbContext context);
}
