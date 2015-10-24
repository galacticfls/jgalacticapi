# jgalacticapi

This software library allows people to access galacticfiles.com programmatically.  It enables the use of the following features.

* [Create account](#account-creation)
* Login
* File upload
* List files
* Generate download links for any IP
* Delete files
* Generate payment addresses
* List payment addresses
* List deposits
* Withdraw to address
* List withdraws
* Update file to public, private, or set download password
* Update file fee
* Grant users access to private files

#Account creation
All access requires a new account creation or a login

```java
GFSession s = new GFSession();
s.acceptTerms(true);  //Make sure you read galacticfiles.com/terms.html
s.createUser(username, userpass);
CheckData cd = s.check();
if (cd.isOk() {
  ..
```

Or login
```java
GFSession s = new GFSession();
s.login(username, userpass);
CheckData cd = s.check();
if (cd.isOk() {
  ..
```


