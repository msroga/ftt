package pl.ftt.service;

import pl.ftt.domain.User;
import pl.ftt.domain.UserDetails;

/**
 * Created by Marek on 2015-05-27.
 */
public interface ISecurityService
{
   User getLoggedInUser();

   UserDetails getUserDetails();
}
