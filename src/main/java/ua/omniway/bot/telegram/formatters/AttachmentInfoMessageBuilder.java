package ua.omniway.bot.telegram.formatters;

import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.services.app.L10n;

import java.util.List;
import java.util.stream.Collectors;

public class AttachmentInfoMessageBuilder {
    private static final int ONE_KB = 1_024;
    private static final int ONE_MB = 1_048_576;
    private static final int ONE_GB = 1_073_741_824;

    private AttachmentInfoMessageBuilder() {
    }

    public static String attachmentInfoSummary(String lang, List<AttachmentInfo> attachments) {

        if (attachments.isEmpty()) return "";
        final List<AttachmentInfo> readyAttachments = attachments.stream().filter(AttachmentInfo::isReady).collect(Collectors.toList());
        final int sizeSum = readyAttachments.stream().mapToInt(AttachmentInfo::getFileSize).sum();
        return String.format(L10n.getString("commands.attachments.message.attachmentsInfo", lang),
                readyAttachments.size(), formatAttachmentSize(sizeSum));
    }

    public static String formatAttachmentSize(int size) {
        if (size < ONE_KB) return size + " B";
        if (size < ONE_MB) return String.format("%.1f KB", size / 1024d);
        if (size < ONE_GB) return String.format("%.1f MB", size / (1024d * 1024d));
        return size + " B";
    }
}
