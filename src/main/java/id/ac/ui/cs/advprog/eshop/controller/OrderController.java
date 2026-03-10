package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "createOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "orderHistoryForm";
    }

    @PostMapping("/history")
    public String orderHistoryByAuthor(
            @RequestParam("author") String author,
            Model model
    ) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("author", author);
        model.addAttribute("orders", orders);
        return "orderHistoryList";
    }
}
