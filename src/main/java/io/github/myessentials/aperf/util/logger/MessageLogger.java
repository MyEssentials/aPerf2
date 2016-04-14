package io.github.myessentials.aperf.util.logger;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.text.TextTemplate.arg;

/**
 * {@link Logger} implementation that directs log messages to a given {@link MessageChannel}
 */
public class MessageLogger implements Logger {
    private static final TextTemplate logLineTemplate = TextTemplate.of(
            "[", arg("level"), "] ", arg("msg").color(TextColors.RESET), " ", arg("throwable").color(TextColors.RED).optional()
    );

    private Logger rootLogger;
    private MessageChannel channel;

    public MessageLogger(Logger rootLogger, MessageChannel channel) {
        checkNotNull(rootLogger, "rootLogger");
        checkNotNull(channel, "channel");
        this.rootLogger = rootLogger;
        this.channel = channel;
    }

    @Override
    public String getName() {
        return rootLogger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return rootLogger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled()) {
            sendMessage("TRACE", msg);
        }

        rootLogger.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            sendMessage("TRACE", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled()) {
            sendMessage("TRACE", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()) {
            sendMessage("TRACE", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled()) {
            sendMessage("TRACE", msg, t);
        }

        rootLogger.trace(msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return rootLogger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (isTraceEnabled(marker)) {
            sendMessage("TRACE", msg);
        }

        rootLogger.trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (isTraceEnabled(marker)) {
            sendMessage("TRACE", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (isTraceEnabled(marker)) {
            sendMessage("TRACE", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        if (isTraceEnabled(marker)) {
            sendMessage("TRACE", MessageFormatter.arrayFormat(format, argArray).getMessage());
        }

        rootLogger.trace(marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (isTraceEnabled(marker)) {
            sendMessage("TRACE", msg, t);
        }

        rootLogger.trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return rootLogger.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        if (isDebugEnabled()) {
            sendMessage("DEBUG", msg);
        }

        rootLogger.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled()) {
            sendMessage("DEBUG", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled()) {
            sendMessage("DEBUG", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled()) {
            sendMessage("DEBUG", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (isDebugEnabled()) {
            sendMessage("DEBUG", msg, t);
        }

        rootLogger.debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return rootLogger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        if (isDebugEnabled(marker)) {
            sendMessage("DEBUG", msg);
        }

        rootLogger.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        if (isDebugEnabled(marker)) {
            sendMessage("DEBUG", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (isDebugEnabled(marker)) {
            sendMessage("DEBUG", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        if (isDebugEnabled(marker)) {
            sendMessage("DEBUG", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        if (isDebugEnabled(marker)) {
            sendMessage("DEBUG", msg, t);
        }

        rootLogger.debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return rootLogger.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        if (isInfoEnabled()) {
            sendMessage("INFO", msg);
        }

        rootLogger.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        if (isInfoEnabled()) {
            sendMessage("INFO", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if (isInfoEnabled()) {
            sendMessage("INFO", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        if (isInfoEnabled()) {
            sendMessage("INFO", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        if (isInfoEnabled()) {
            sendMessage("INFO", msg, t);
        }

        rootLogger.info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return rootLogger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        if (isInfoEnabled(marker)) {
            sendMessage("INFO", msg);
        }

        rootLogger.info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        if (isInfoEnabled(marker)) {
            sendMessage("INFO", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (isInfoEnabled(marker)) {
            sendMessage("INFO", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        if (isInfoEnabled(marker)) {
            sendMessage("INFO", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        if (isInfoEnabled(marker)) {
            sendMessage("INFO", msg, t);
        }

        rootLogger.info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return rootLogger.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        if (isWarnEnabled()) {
            sendMessage("WARN", msg);
        }

        rootLogger.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        if (isWarnEnabled()) {
            sendMessage("WARN", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.warn(format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (isWarnEnabled()) {
            sendMessage("WARN", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled()) {
            sendMessage("WARN", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (isWarnEnabled()) {
            sendMessage("WARN", msg, t);
        }

        rootLogger.warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return rootLogger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        if (isWarnEnabled(marker)) {
            sendMessage("WARN", msg);
        }

        rootLogger.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        if (isWarnEnabled(marker)) {
            sendMessage("WARN", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (isWarnEnabled(marker)) {
            sendMessage("WARN", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        if (isWarnEnabled(marker)) {
            sendMessage("WARN", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        if (isWarnEnabled(marker)) {
            sendMessage("WARN", msg, t);
        }

        rootLogger.warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return rootLogger.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        if (isErrorEnabled()) {
            sendMessage("ERROR", msg);
        }

        rootLogger.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled()) {
            sendMessage("ERROR", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled()) {
            sendMessage("ERROR", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled()) {
            sendMessage("ERROR", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (isErrorEnabled()) {
            sendMessage("ERROR", msg, t);
        }

        rootLogger.error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return rootLogger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        if (isErrorEnabled(marker)) {
            sendMessage("ERROR", msg);
        }

        rootLogger.error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        if (isErrorEnabled(marker)) {
            sendMessage("ERROR", MessageFormatter.format(format, arg).getMessage());
        }

        rootLogger.error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (isErrorEnabled(marker)) {
            sendMessage("ERROR", MessageFormatter.format(format, arg1, arg2).getMessage());
        }

        rootLogger.error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        if (isErrorEnabled(marker)) {
            sendMessage("ERROR", MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

        rootLogger.error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        if (isErrorEnabled(marker)) {
            sendMessage("ERROR", msg, t);
        }

        rootLogger.error(marker, msg, t);
    }

    private void sendMessage(String level, String msg) {
        sendMessage(level, msg, null);
    }

    private void sendMessage(String level, String msg, Throwable t) {
        channel.send(logLineTemplate.apply(ImmutableMap.of(
                "level", getLevelText(level),
                "msg", Text.of(msg),
                "throwable", Text.of(t)
        )).build());
    }

    private Text getLevelText(String level) {
        level = level.toUpperCase();

        switch(level) {
            case "TRACE": return Text.of(TextColors.BLUE, level);
            case "DEBUG": return Text.of(TextColors.BLUE, level);
            case "INFO": return Text.of(TextColors.GREEN, level);
            case "WARN": return Text.of(TextColors.YELLOW, level);
            case "ERROR": return Text.of(TextColors.RED, level);
        }

        return Text.of(level);
    }
}
