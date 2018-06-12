# GoogleSignIn
Google SignIn,get profile and show in second activity
Now, the code don`t work how I want. When I signIn with my google account, the logIn is succes.
I save the google token throw an object of my singleton class and jump to the second activity
I show on the left menu of the navigation drawer my id,my full name,my Id, but the picture don´t load.If I change the url,the picture load.If I click on the url that I got of my google login,the picture load ok
I want to SignOut, but the method signOut() dont´t work fine. My idea is,signOut and then,go back to the LoginScreen (first activity)
I think that the signOut don´t work, and when the activity go back to login screen, the app detect that the objet mGoogleApiClient is conected,and then, continue to the second activity

