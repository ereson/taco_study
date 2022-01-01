package com.ereson.toca.web;

import com.ereson.toca.Order;
import com.ereson.toca.User;
import com.ereson.toca.data.OrderRepository;
import com.ereson.toca.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    private OrderRepository orderRepo;

    private OrderProps orderProps;

    @Autowired
    private UserRepository userRepository;

    //通过构造器注入
    public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    /**
     * 显示最近的订单
     * @param user
     * @param model
     * @return
     */
    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
    //@AuthenticationPrincipal注解可以获取当前登录人的信息

        //从orderProps中获取页面大小
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user,pageable));

        return "orderList";
    }

    //注入principal对象
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user){
        if (errors.hasErrors()) {
            return "orderForm";
        }

        //获取user,并设置user
        /**User user = userRepository.findByUsername(principal.getName());
        order.setUser(user);*/
        //getPrincipal返回object对象
        /**User user = (User) authentication.getPrincipal();
        order.setUser(user);*/
        order.setUser(user);

        //另一种方法或得当前认证的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = (User) authentication.getPrincipal();

        orderRepo.save(order);
        //重置session，不然order信息会一直保存在session中
        sessionStatus.setComplete();
        log.info("Order submitted:" + order);
        return "redirect:/";
    }

    /**
     * put类型是对大规模对数据进行替换
     * @param order
     * @return
     */
    @PutMapping("/{orderId}")
    public Order putOrder(@RequestBody Order order) {
        return orderRepo.save(order);
    }

    /**
     * patch请求对小规模对数据进行替换，但是我们可以自己进行处理
     * @param orderId
     * @param patch
     * @return
     */
    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Order order = orderRepo.findById(orderId).get();
        if (patch.getName() != null) {
            order.setName(patch.getName());
        }
        if (patch.getStreet() != null) {
            order.setStreet(patch.getStreet());
        }
        if (patch.getCity() != null) {
            order.setCity(patch.getCity());
        }
        if (patch.getState() != null) {
            order.setState(patch.getState());
        }
        if (patch.getZip() != null) {
            order.setZip(patch.getZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }

        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e){}
    }
}
