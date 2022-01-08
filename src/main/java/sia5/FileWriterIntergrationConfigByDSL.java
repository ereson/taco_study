package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileWriterIntergrationConfigByDSL {

    /**
     * IntegrationFlows 初始化构建api
     * @return
     */
    @Bean
    public IntegrationFlow fileWriterFlow(){
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .<String, String> transform(t -> t.toUpperCase())
                .channel(MessageChannels.direct("fileWriterChannel"))
                .handle(Files.outboundAdapter(new File("tmp/sia5/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
