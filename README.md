![GalacticFiles Banner](https://galacticfiles.com/images/galacticfiles2.png)

# jgalacticapi

[Download the latest jar file](https://github.com/galacticfls/jgalacticapi/releases)

[View the API javadoc](http://galacticfls.github.io/jgalacticapi/javadoc/index.html)

This software library allows people to access galacticfiles.com programmatically.  It enables the use of the following features.

* [Create account](#account-creation)
* [Login](#login)
* [Check account](#check-account)
* [File upload](#file-upload)
* [List files](#list-files)
* [Generate download links for any IP](#generate-link)
* [Delete files](#delete-files)
* [Generate payment addresses](#generate-payment-addresses)
* [List payment addresses](#list-payment-addresses)
* [List deposits](#list-deposits)
* [Withdraw to address](#withdraw)
* [List withdraws](#list-withdraws)
* [Update files](#update-files)
* [Grant access](#grant-access)

#Account creation
```java
GFSession s = new GFSession();
s.acceptTerms(true);  //Make sure you read galacticfiles.com/terms.html
s.createUser(username, userpass);
CheckData cd = s.check();
if (cd.isOk() {
  ..
```

#Login
```java
GFSession s = new GFSession();
s.login(username, userpass);
CheckData cd = s.check();
if (cd.isOk() {
  ..
```

#Check account
You should always run check() after login to make sure you have successfully logged in.  You account balance is also returned.
```java
GFSession s = new GFSession();
s.login(username, userpass);
CheckData cd = s.check();
if (cd.isOk() {
  System.out.println("Your account balance is: " + cd.getBalance());
```

#File upload
```java
File f = new File(....);
//Upload file f as putlic with link creation fee of 0.02BTC
//This will block until the upload is complete
GFSession s = new GFSession();
s.login(username, userpass);
Long id = s.uploadFile(f, true, "0.02"); 
//id is the file id to use in reference to the file
//or null if the upload failed.
```

#List files
```java
GFSession s = new GFSession();
s.login(username, userpass);
List<FileInfo> fl = s.listFiles();
for (FileInfo i : fl) {
  ...
```

#Generate link
If you are creating a download link for your own file you will only be charged the site fee based on the size of the file.  You will not be charged your own fee.  If you have insufficient funds the link will not be generated, getFee() will show how much you need in your account.  You may generate a link for any IP address in the ipv4 format (VVV.XXX.YYY.ZZZ).  If the ip is null, the link is generated for the publicly visible IP address of your connection.
```java
GFSession s = new GFSession();
s.login(username, userpass);
//password is null if there is no download password
//ip is the string representation of the IP that can download the file
//    if ip is null the IP this connection is from is used
DownloadLink dl = s.genDownloadLink(fid, password, ip);
if (dl.getUrl() == null) {
  //Link creation failed.
  // getFee() shows how much you need in case 
  // you have insufficient funds
  dl.getFee();
}
else {
  dl.getUrl(); // Url you can use to download the file
```

#Delete files
You may delete any file you own.  Already generated download links may work for a period of time until the file is removed from end points.
```java
GFSession s = new GFSession();
s.login(username, userpass);
s.deleteFile(fileid);
```

#Generate payment addresses
When you create an account a payment address is generated for you.  You may generate new ones if you like.  You must wait 5 minutess between each address.
```java
GFSession s = new GFSession();
s.login(username, userpass);
String addr = s.genAddress();
if (addr != null) {
  ...
}
else {
  //wait 5 minutes
```

#List payment addresses
You can list all of the payment addresses generated for you
```java
GFSession s = new GFSession();
s.login(username, userpass);
List<AddressInfo> addresses = s.listAddresses();
for (AddressInfo : addresses) {
  ...
```

#List deposits
```java
GFSession s = new GFSession();
s.login(username, userpass);
List<DepositInfo> dpl = s.listDeposits();
for (DepositInfo di : dpl) {
  //Deposits are confirmed and added to your
  //balance after 6 confirmations
  System.out.println("Amount:        " + di.getAmount());
  System.out.println("Confirmations: " + di.getConfirmations());
  System.out.println("Confirmed yet? " + di.isAccepted());
```

#Withdraw
You may send bitcoin from your account to another address.
```java
GFSession s = new GFSession();
s.login(username, userpass);
boolean ok = s.withdraw(address, "0.02");
```

#List withdraws
```java
GFSession s = new GFSession();
s.login(username, userpass);
List<WithdrawInfo> wl = s.listWithdraws();
for (WithdrawInfo wi : wl) {
  ...
```

#Update files
You can change files accessibility to either public, private or download with password.  You can also change the fee you charge others for download links.
```java
GFSession s = new GFSession();
s.login(username, userpass);
boolean ok0 = s.setPrivateFile(file0_id);
boolean ok1 = s.setPublicFile(file1_id);
boolean ok2 = s.setDownloadpass(file2_id, download_password);
boolean ok3 = s.setFileFee(file0_id, "0.1");
```

#Grant access
```java
GFSession s = new GFSession();
s.login(username, userpass);
boolean ok = s.addFileGrant(fileid, userid);
//Or remove access
boolean ok1 = s.deleteFileGrant(fileid, userid1);
```

