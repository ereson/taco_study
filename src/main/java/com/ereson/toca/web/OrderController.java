package com.ereson.toca.web;

import com.ereson.toca.Order;
import com.ereson.toca.User;
import com.ereson.toca.data.OrderRepository;
import com.ereson.toca.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepository;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
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
}
