package rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
class LocalDateTimeConfig {

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(object);
            }
        };
    }
}