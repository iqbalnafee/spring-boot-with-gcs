package com.example.bambergBeverageBox.aspects;


import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.enums.Menu;
import com.example.bambergBeverageBox.exceptions.RequiredParamMissingException;
import com.example.bambergBeverageBox.util.MvcUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Stream;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MvcTitleAndContentAspect {


    private final MvcUtil mvcUtil;

    @Before("@annotation( titleAndContent )")
    public void addTitleAndContent(JoinPoint joinPoint, TitleAndContent titleAndContent) throws RequiredParamMissingException {

        Model model = (Model) Stream.of(joinPoint.getArgs())
                .filter(arg -> arg instanceof Model && !(arg instanceof RedirectAttributes))
                .findAny()
                .orElseThrow(()->new RequiredParamMissingException("Parameters Missing"));

        String title = titleAndContent.title();
        String content = titleAndContent.content();
        Menu activeMenu = titleAndContent.activeMenu();

        mvcUtil.addTitleAndContent(model, title, content, activeMenu);
        mvcUtil.addMenuPath(model, activeMenu.findMenuPath());



        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Get the username
            // You can retrieve other details from the authenticated principal if needed
            // For example: UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            // Then access user details like userPrincipal.getId(), userPrincipal.getEmail(), etc.
            model.addAttribute("username", username);

            // Check for roles
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }*/
    }
}
