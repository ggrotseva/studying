package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.enums.UserRole;
import bg.softuni.pathfinder.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

   private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // demo url: .../admin/changeUserPermission/2?shouldReplaceRoles=false
    // in the Request Body we have this JSON:
    // {
    //   "roleName" : "ADMIN"
    // }

    @PatchMapping("/changeUserPermission/{id}")
    @ResponseBody
    public Set<UserRole> changeRoles(@PathVariable Long id,
                                     @RequestParam(defaultValue = "false") Boolean shouldReplaceRoles,
                                     @RequestBody String roleName) {

        //get the JSON value from "roleName"

        return this.userService.addRole(id, shouldReplaceRoles, roleName);
    }
}
