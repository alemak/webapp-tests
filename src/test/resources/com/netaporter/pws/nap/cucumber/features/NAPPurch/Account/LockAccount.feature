@seaview  @purch
Feature: Lock account
  A user's account will be locked after 15 failed attempt

  @nap @Channelized
  Scenario Outline: A user tries to login with an incorrect password for specified number of times.
      Given I am a seaview registered default user
      When I try to sign in with incorrect password <loginAttempts> times
      Then My account is locked: <isLocked>

  Examples:
    | loginAttempts | isLocked  |
    |     15        |  true     |
    |     14        |  false    |

# NOTE: unlock feature does not exist in web app, change password by user or admin user does not unlock the account

#  Scenario Outline: When a locked account is reset, the user can login correctly again
#	Given I am on <channel>
#	And I am a registered admin user
#    And I register for an account and logout
#	And I try to sign in with incorrect password 15 times
#	When I reset the user account
#	And I sign in with the correct details
#	Then I am <isLoggedIn> signed in
#
#Examples:
#  | channel | isLoggedIn  |
#  | intl    |  true       |

#Scenario outline: locked account timeout reset?
	
