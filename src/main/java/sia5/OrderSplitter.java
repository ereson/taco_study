package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.router.PayloadTypeRouter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderSplitter {

    public Collection<Object> splitOrderIntoParts(PurchaseOrder po) {
        ArrayList<Object> parts = new ArrayList<>();
        parts.add(po.getBillingInfo());
        parts.add(po.getLineItems());
        return parts;
    }

    @Bean
    @Splitter(inputChannel = "poChannel", outputChannel = "splitOrderChannel")
    public MessageRouter orderSplitter(){
        PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(BillingInfo.class.getName(), "billingInfoChannel");
        router.setChannelMapping(List.class.getName(), "lineItemsChannel");
        return router;
    }

    /**
     * 对传入lineItemChannel的列表进行更进一步的分割
     * @param lineItems
     * @return
     */
    @Splitter(inputChannel = "lineItemsChannel", outputChannel = "lineItemChannel")
    public List<LineItem> lineItemSplitter(List<LineItem> lineItems) {
        return lineItems;
    }
}
