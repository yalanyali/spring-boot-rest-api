package rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
class LocalDateConfig {

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(object);
            }
        };
    }
}