package sia5;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @MessagingGateway告诉Spring Integration要在运行时生成该接口的实现
 * defaultRequestChannel 表明方法调用时返回的消息要发送至给定的消息通道
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
