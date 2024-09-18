# Mail

This page details the philosphy, operations, and the recommended practices of making mailing work on WordPress.

## Prerequisites

Before starting, you'll need to have a basic understanding of the [SMTP protocol](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol) and the  roles of each of its nodes ([MUA](https://en.wikipedia.org/wiki/Mail_user_agent) and [MTA](https://en.wikipedia.org/wiki/Message_transfer_agent)).

## WordPress's Philosophy on Mailing

WordPress's role as a CMS is to act as a [MUA](https://en.wikipedia.org/wiki/Mail_user_agent), not a MTA, in the email route. Hence, `wp_mail()` is setup by default to rely on the presence of a local MTA.

WordPress can neither be a reliable or performant MTA (the same holds true for most other CMSs as well). Test benchmarks have shown that when WordPress is acting as a MTA itself (either via remote SMTP calls or HTTP API), page load time suffers by magnitudes due to network connection overhead compared to relaying to a local MTA.

## How WordPress's Mailing operates out-of-the-box

WordPress uses the `wp_mail()` function that utilizes the [PHPMailer](https://github.com/PHPMailer/PHPMailer) library in its default configuration. By default configuration, the PHPMailer library uses PHP's internal `mail()` function that requires the presence of a sendmail equivalent binary acting as [MTA](https://en.wikipedia.org/wiki/Message_transfer_agent) (configured via `sendmail_path` setting in `php.ini` which defaults to `/usr/sbin/sendmail` on most systems). Hence, in order for WordPress to work out-of-the-box, one needs to have the related mailing environment properly configured.

This is generally not a problem if you are using a managed hosting service, but if you are running your own server and do not have a SMTP server (MTA) set up and/or the related mailing environment properly configured, the outgoing mail is unlikely to send.

## Recommended Actions

Besides the technical aspects, it's worthwhile to outline the recommended solutions given the different scenarios so the admins do not end up choosing solutions that don't fit them.

If your WordPress site is on managed hosting, then there's typically nothing extra you need to do. Mails originated from your WordPress install shall work out-of-the-box. All mailing related questions shall be directed to your webhost.

If you're on unmanged hosting (i.e. running your own server), there are typically three ways you can setup your mails:

- Setup a local MTA or dedicated mail servers that sends mails directly: in this method, you as the admin is responsible for everything. Please keep in mind, even if you've configured all the related software correctly, you may still run into issues as mail deliverability extend into factors outside of your control such as IP reputation. Due to the amount of overhead and ongoing maintenance required, this method is typically reserved if you've the need to send very large volume of emails or you're a large webhost and need to be in control of the mail service.

- Setup a local MTA and have it relay mails to a third party mailing service. This is the recommended method for most self-hosted setups, is easier to implement than the previous one, and still retains compatibility with WordPress' philosophy. If you don't want to deal with the complexity of mail deliverability issues in running a MTA, this is way to go. In fact, some smaller web hosts utilize this method (they would hold a business account with the third party mailing services and relay all the mails to the mailing service). If you host and manage your own site and need to send to a sizable newsletter audience, this method is also ideal.

- Use a SMTP plugin and configure it to utilize a third party mailing service: doing this way, your WordPress install becomes a MTA itself. While the setup is much easier compared to other methods, your site may encounter significant performance degratdation due to network connection overhead.

## Setting up your own mail servers

If you are running your own *NIX server, you should have either `postfix` or `sendmail` equivalent on your machine and just need to set them up (you may search on the web for how-tos). If you wish to use a third party mailing service, the recommended practice is to setup your local MTA to relay mail to the remote SMTP server as this will prevent the page load time from suffering. If you do not want to manually setup a complete mail server such as `postfix` or `sendmail` on your *NIX box, there are two other ways:

* Use a relay MTA in place of a local full-featured MTA: for example, [msmtp-mta](https://wiki.debian.org/msmtp) is a simplified MTA that can be used to relay emails using a remote SMTP server (i.e. a remote MTA). The package provides a `/usr/sbin/sendmail` symlink to `msmtp` and has sendmail compatible interface that other software can use directly. On a Windows machine, try a sendmail emulator like [Fake sendmail for Windows with TLS v1.2 support](https://github.com/sendmail-tls1-2/main). 

* Use SMTP plugins: You can install [SMTP plugins](https://wordpress.org/plugins/search/smtp/) from the WP.org plugin directory. These type of plugins overrides `wp_mail()` default settings to use PHPMailer's internal SMTP method instead, which utlize remote third party mailing services to send mail on your behalf of your domain. Doing this way, your WordPress install becomes a MTA itself. Please keep in mind, the performance of the page load time may degrade significantly utilizing this method.

### Windows Host Server Specific

Check your “Relay” settings on the SMTP Virtual Server. Grant access to `127.0.0.1` . Then in your `php.ini` file, set the `SMTP` setting to the same IP address. Also set `smtp_port` to `25`.

### Ensuring Deliverability

#### Ensure Proper Return Address is Used

By default, the WordPress mailer fills in the `From:` field with *wordpress@example.com* and the `From:` name as *WordPress*.

This is fine if this is a valid e-mail address. For example, if your domain is *example.com* and your email is *wordpress@example.com*, your host shall pass the email on for delivery. It will probably send your mail as long as *example.com* is setup to send and receive mail, even if *wordpress* is not a valid mail box. But if you set your real email as the `From:` address and it’s something like *wordpress@example.net*, the mail may not send because the DNS records of *example.net* did not authorize its mails to be handled by your mail server.

#### When Treated as Spam

Your email message may have been routed to a spam folder or even worse, simply discarded as malicious. There are a couple measures you can use to convince recipient’s mail servers that your message is legitimate and should be delivered as addressed. If you have a personal site, setting up SPF is the bare minimum to ensure deliverability. If your site also deals with commercial transaction related content, setting up both SPF and DKIM would be ideal, if not required.

##### SPF (Sender Policy Framework)

This is the most common anti-spam measure used. If you are on a managed hosting service, there is a good chance your host has set this up for the mail server you are using. Have WordPress email you and check the message headers for evidence that the message passed the SPF check. You can get a message sent by following the Forgot Password link on the login page. To keep your old password, do not follow the link in the message.

If your system email failed the SPF check, you can set up the credentials if you have access to your DNS records and your mail server’s domain belongs to you. Check the return path of the email your system sent. If the mail server listed there has your domain name, you can set up SPF credentials. There are several how-tos on the Internet.

##### DKIM (Domain Key Identified Mail)

This system is also used. You can use both SPF and DKIM in the same message. Again, just as with SPF, you can check if your receiving mailserver verified your host’s domain key by examining the mail header. There is a fair chance no signature key was provided, indicating your host chose to not use this protocol. Also as with SPF, if you can edit your DNS records and the mail server belongs to your domain, you can set up DKIM credentials yourself. Some how-tos exist if you search the Internet.

To get WordPress to send the proper DKIM keys, hook the `'phpmailer_init'` action. You are passed the `$phpmailer` object. Set the necessary properties and return the object. See the class source code for more information. It’s on [wp-includes/PHPMailer/PHPMailer.php](https://github.com/WordPress/wordpress-develop/blob/trunk/src/wp-includes/PHPMailer/PHPMailer.php).
