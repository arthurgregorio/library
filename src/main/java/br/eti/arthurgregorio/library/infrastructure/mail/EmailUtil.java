package br.eti.arthurgregorio.library.infrastructure.mail;

import br.eti.arthurgregorio.library.infrastructure.i18n.MessageSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Simple utilities to use when we are creating reports
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 10/09/2019
 */
public final class EmailUtil {

    /**
     * @return
     */
    public static Function<String, String> translateFunction() {
        return MessageSource::get;
    }

    /**
     * @return
     */
    public static Function<String, String> formatLocalDateFunction() {
        return value -> isNotBlank(value) ? LocalDate.parse(value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    /**
     * @return
     */
    public static String getGreeting() {

        final LocalTime now = LocalTime.now();

        if (now.isAfter(LocalTime.of(12, 0))) {
            return MessageSource.get("mail.good-evening");
        } else if (now.isBefore(LocalTime.of(12, 0))) {
            return MessageSource.get("mail.good-morning");
        } else {
            return MessageSource.get("mail.good-night");
        }
    }

    /**
     * @param localDate
     * @return
     */
    public static String toString(LocalDate localDate) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
    }

    /**
     * @param localTime
     * @return
     */
    public static String toString(LocalTime localTime) {
        return DateTimeFormatter.ofPattern("HH:mm").format(localTime);
    }
}
