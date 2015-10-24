# jgalacticapi

This software library allows people to access galacticfiles.com programmatically.  It enables the use of the following features.

* [Create account](#account-creation)
* [Login](#login)
* [File upload](#file-upload)
* [List files](#list-files)
* [Generate download links for any IP](#generate-link)
* [Delete files](#delete-files)
* [Generate payment addresses](#generate-payment-address)
* List payment addresses
* List deposits
* Withdraw to address
* List withdraws
* Update file to public, private, or set download password
* Update file fee
* Grant users access to private files

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

