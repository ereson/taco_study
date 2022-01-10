package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChannelAdapter {

    @Bean
    @InboundChannelAdapter(poller = @Poller(fixedRate = "1000"), channel = "numberChannel")
    public MessageSource<Integer> numberSource(AtomicInteger source) {
        return () -> {
            return new GenericMessage<>(source.getAndIncrement());
        };
    }


    @Bean
    @InboundChannelAdapter(channel = "file-channel", poller=@Poller(fixedDelay = "1000"))
    public MessageSource fileReadingMessageSource() {
        FileReadingMessageSource sourceReader = new FileReadingMessageSource();
        sourceReader.setDirectory(new File(INPUT_DIR));
        sourceReader.setFilter(new SimplePatternFileListFilter(FILE.PATTERN));
        return sourceReader;
    }
}
