package pl.myo.service;

import pl.myo.domain.User;
import pl.myo.domain.UserDetails;

/**
 * Created by Marek on 2015-05-27.
 */
public interface ISecurityService
{
   User getLoggedInUser();

   UserDetails getUserDetails();
}
