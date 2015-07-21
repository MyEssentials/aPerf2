package aperf.api.spawnlimit;

import aperf.APerf;
import aperf.api.SpawnLimitRegistrar;
import myessentials.chat.JsonMessageBuilder;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.Iterator;
import java.util.List;

public class SpawnLimitChatComponent implements IChatComponent {
    private final IChatComponent component;

    public SpawnLimitChatComponent(int id, ISpawnLimit limit) {
        String type = SpawnLimitRegistrar.INSTANCE.getType(limit.getClass());
        if (type == null) type = "Unknown";

        component = new JsonMessageBuilder()
                .setText(String.format("%3s", Integer.toString(id)))
                .addExtra().setText("|")
                .addExtra().setText(String.format("%6s", limit.isEnabled() ? "On" : "Off"))
                    .setClickEventRunCommand("/aperf e s t")
                .addExtra().setText("|")
                .addExtra().setText(String.format("%46s", type))
                .build();

        APerf.LOG.info(component.toString());
    }

    @Override
    public IChatComponent setChatStyle(ChatStyle p_150255_1_) {
        return component.setChatStyle(p_150255_1_);
    }

    @Override
    public ChatStyle getChatStyle() {
        return component.getChatStyle();
    }

    @Override
    public IChatComponent appendText(String p_150258_1_) {
        return component.appendText(p_150258_1_);
    }

    @Override
    public IChatComponent appendSibling(IChatComponent p_150257_1_) {
        return component.appendSibling(p_150257_1_);
    }

    @Override
    public String getUnformattedTextForChat() {
        return component.getUnformattedTextForChat();
    }

    @Override
    public String getUnformattedText() {
        return component.getUnformattedText();
    }

    @Override
    public String getFormattedText() {
        return component.getFormattedText();
    }

    @Override
    public List getSiblings() {
        return component.getSiblings();
    }

    @Override
    public IChatComponent createCopy() {
        return component.createCopy();
    }

    @Override
    public Iterator iterator() {
        return component.iterator();
    }
}
