package pl.ftt.service.impl;

import org.springframework.stereotype.Service;
import pl.ftt.service.ISecurityService;

/**
 * Created by Marek on 2015-05-27.
 */
@Service("securityService")
public class SecurityServiceImpl implements ISecurityService
{
//   @Resource
//   private IUserService userService;
//
//   @Override
//   public User getLoggedInUser()
//   {
//      UserDetails userDetails = getUserDetails();
//      return userDetails != null ? userService.getByLogin(userDetails.getUsername()) : null;
//   }
//
//   @Override
//   public UserDetails getUserDetails()
//   {
//      SecurityContext ctx = SecurityContextHolder.getContext();
//      Authentication authentication = ctx.getAuthentication();
//      if (authentication == null)
//      {
//         return null;
//      }
//      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//      return userDetails;
//   }
}
