package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class FileWriterIntergrationConfig {

    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer(){
        return text -> text.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("tmp/sia5/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

    /**
     * 显式的构建这些bean
     * @return
     */
    @Bean
    public MessageChannel textInChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }


}
