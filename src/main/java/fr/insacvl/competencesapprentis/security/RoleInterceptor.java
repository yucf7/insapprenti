package fr.insacvl.competencesapprentis.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiresRole annotation = handlerMethod.getMethodAnnotation(RequiresRole.class);

            if (annotation != null) {
                Roles[] requiredRoles = annotation.value();
                try {
                    String token = request.getSession().getAttribute("token").toString();

                    for (Roles role : requiredRoles) {
                        if (JwtUtils.validateToken(token, role.toString())) {
                            return true;
                        }
                    }

                    // Redirect the user or handle unauthorized access
                    response.sendRedirect("/login");
                    return false;
                }
                catch (Exception e){
                    System.out.println("exception "+e.getMessage());
                    response.sendRedirect("/login");
                    return false;
                }
            }
        }

        return true;
    }

}