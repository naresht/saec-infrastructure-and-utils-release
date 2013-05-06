package com.bfds.validation.message;


import org.springframework.binding.message.Severity;

public interface ValidationMessageContext {

    /**
     * Get all messages in this context.
     * @return the messages
     */
    public Message[] getAllMessages();

    /**
     * Add a new message with {@link Severity#INFO} this context.
     * @param text the message text
     */
    public void info(String text);

    /**
     * Add a new message with {@link Severity#ERROR} this context.
     * @param text the message text
     */
    public void error(String text);

    /**
     * Add a new message with {@link Severity#WARNING} this context.
     * @param text the message text
     */
    public void warn(String text);

    /**
     * Add a new message with {@link Severity#FATAL} this context.
     * @param text the message text
     */
    public void fatal(String text);

    /**
     *  Add a new message with {@link Severity#INFO} this context.
     * @param format  A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args Arguments referenced by the format specifiers in the format string.
     * @see {@link String#format(String, Object...)}
     */
    public void info(String format, Object... args);

    /**
     *  Add a new message with {@link Severity#ERROR} this context.
     * @param format  A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args Arguments referenced by the format specifiers in the format string.
     * @see {@link String#format(String, Object...)}
     */
    public void error(String format, Object... args);

    /**
     *  Add a new message with {@link Severity#WARNING} this context.
     * @param format  A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args Arguments referenced by the format specifiers in the format string.
     * @see {@link String#format(String, Object...)}
     */
    public void warn(String format, Object... args);

    /**
     *  Add a new message with {@link Severity#FATAL} this context.
     * @param format  A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args Arguments referenced by the format specifiers in the format string.
     * @see {@link String#format(String, Object...)}
     */
    public void fatal(String format, Object... args);

    /**
     * Clear all messages added to this context.
     */
    public void clearMessages();

    /**
     * The default source when {@link Message#source} is null.
     * @param source
     */
    public void setDefaultSource(String source);

    /**
     * Retrieve {@link Message}s with the given severity
     * @param severity
     * @return  the messages with the given severity
     */
    public Message[] getMessagesBySeverity(Severity severity);
}
