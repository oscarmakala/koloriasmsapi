# koloriasmsapi
A Java library for communicating with the kolorio SMS REST API

### Installing
if you want to compile yourself, here's how:

```
$ git https://github.com/oscarmakala/kolorio-sms-client.git
$ cd kolorio-sms-client
$ mvn install          # Requires maven, download from http://maven.apache.org/download.html
```

### Quickstart

#### Send a Sms

```
String username = "xxxxx"; // Your username from http://kolorio.com/tz/
String password = "xxxxx"; // Your password from http://kolorio.com/tz/
String url = "xxxx";       // Endpoint url provided

SmsRestClient client =client = new SmsRestClient.Builder(username, password)
                            .setUrl(url)
                            .build();
             
Message message = new MessageCreator(
                "2557XXXXXXXX",
                "Shortcode/Msisdn",
                "Hello Tanzania"
        ).create(client);
System.out.println(message.toString());        
```
